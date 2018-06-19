package javax.safetycritical;

import javax.realtime.BoundAsyncLongEventHandler;
import javax.realtime.ConfigurationParameters;
import javax.realtime.MemoryArea;
import javax.realtime.PriorityParameters;
import javax.realtime.ReleaseParameters;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.Phase;
import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.annotate.SCJPhase;

import vm.Memory;

public abstract class ManagedLongEventHandler extends BoundAsyncLongEventHandler implements ManagedSchedulable {

	PriorityParameters priority;
	ReleaseParameters release;
	
	ScopeParameters storage;
	ConfigurationParameters config;

	String name;
	
	Process process = null;
	Mission mission = null;

	ManagedMemory privateMemory;  // backing store of this handler ??
		
	ManagedMemory currentMemory;	// for multicore only; - not implemented
	AffinitySet affinitySet = null;
	
	// used in JML spec. methods
	boolean isRegistered;
	boolean isInMissionScope;
	
	static HandlerBehavior handlerBehavior = null;
	
	/**
	 * Constructs an event handler.
	 * 
	 * @param priority specifies the priority parameters.
	 * @param release  specifies the release parameters.
	 * @param storage  specifies the non-null maximum storage demands for this event handler.
	 * 
	 * @throws <code>IllegalArgumentException</code> if priority or release or storage parameters are null.
	 */
	ManagedLongEventHandler(PriorityParameters priority, ReleaseParameters release, 
		ScopeParameters storage, ConfigurationParameters config) {
		this(priority, release, storage, config, null);
	}

	ManagedLongEventHandler(PriorityParameters priority, ReleaseParameters release, 
			ScopeParameters storage, ConfigurationParameters config,
			String name) {
		if (priority == null)
			throw new IllegalArgumentException("priority is null");
		if (storage == null)
			throw new IllegalArgumentException("storage is null");
		this.priority = priority;
		this.release = release;
		this.storage = storage;
		this.config = config;
		this.name = name;
		this.mission = Mission.getMission();

		int backingStoreOfThisMemory;

//		if (mission == null && this instanceof MissionSequencer) {
//			backingStoreOfThisMemory = MemoryArea.getRemainingMemorySize();
//			currentMemory = ImmortalMemory.instance();
//		} 
//		else 
		{
			//backingStoreOfThisMemory = (int) this.storage.totalBackingStore;
			backingStoreOfThisMemory = (int) this.storage.getMaxInitialArea() + (int) this.storage.getMaxBackingStore(); // HSO
   			if(mission !=null){
				this.currentMemory = mission.currMissSeq.missionMemory;
				this.affinitySet = mission.currMissSeq.affinitySet;
			}
				
		}

		MemoryArea backingStoreProvider = (mission == null) ? 
				MemoryArea.overAllBackingStore : mission.currMissSeq.missionMemory;

		String privateMemoryName = Memory.getNextMemoryName("PvtMem");		
		
		privateMemory = new PrivateMemory((int) this.storage.getMaxInitialArea(),
		           backingStoreOfThisMemory,
	               backingStoreProvider, 
	               privateMemoryName);	
		
		this.isRegistered = false;
		this.isInMissionScope = false;
	}

	@SCJAllowed(Level.SUPPORT)
	@SCJPhase(Phase.CLEANUP)
	public void cleanUp() {
		//System.out.println("ManagedEventHandler.cleanUp: " + this);
		privateMemory.removeArea();
		isRegistered = false;
	}
	
	/**
	 * Registers this event handler with the current mission.
	 */
	@SCJAllowed
	@SCJPhase(Phase.INITIALIZATION)
	public void register() {
		mission.addMSObject(this);
		
		isRegistered = true;
		isInMissionScope = true;
	}

	@SCJAllowed(Level.SUPPORT)
	public void signalTermination() {
		// Default behavior: no action
		//System.out.println("ManagedEventHandler.signalTermination is called");
	}
	
	public String getName() {
		return name;
	}

	
	
//	void setCurrentMemory(ManagedMemory current) {
//		this.privateMemory = current;
//	}
//
//	ManagedMemory getCurrentMemory() {
//		return privateMemory;
//	}
	
	public PriorityParameters getPriorityParam() {
		return priority;
	}
}
