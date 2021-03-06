/**************************************************************************
 * File name  : ManagedEventHandler.java
 * 
 * This file is part a SCJ Level 0 and Level 1 implementation, 
 * based on SCJ Draft, Version 0.94 25 June 2013.
 *
 * It is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as  
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This SCJ Level 0 and Level 1 implementation is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the  
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this SCJ Level 0 and Level 1 implementation.  
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2012 
 * @authors  Anders P. Ravn, Aalborg University, DK
 *           Stephan E. Korsholm and Hans S&oslash;ndergaard, 
 *             VIA University College, DK
 *************************************************************************/
package javax.safetycritical;

import javax.realtime.Affinity;
import javax.realtime.BoundAsyncEventHandler;
import javax.realtime.Clock;
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

/**
 * <code>ManagedEventHandler</code> is the base class for all SCJ handlers.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 * 
 * @scjComment - SCJ issue: In constructor, null arguments for priority and
 *             release parameters are left for resolution by infrastructure
 *             initialization <br>
 *             - SCJ issue: In constructor, if storage parameter is null, 
 *             a default value is given. <br>
            
 */
@SCJAllowed
public abstract class ManagedEventHandler extends BoundAsyncEventHandler implements ManagedSchedulable {

	PriorityParameters priority;
	ReleaseParameters release;

	ScopeParameters storage;
	ConfigurationParameters config;
	
	String name;
	
	Process process = null;
	Mission mission = null;
	
	ManagedMemory privateMemory;    // backing store of this handler ??
	
	ManagedMemory currentMemory;	// for multicore only
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
	ManagedEventHandler(PriorityParameters priority, ReleaseParameters release, 
		ScopeParameters storage, ConfigurationParameters config) {
		this(priority, release, storage, config, null);
	}

	ManagedEventHandler(PriorityParameters priority, ReleaseParameters release, 
			ScopeParameters storage, ConfigurationParameters config,
			String name) {
		if (priority == null)
			throw new IllegalArgumentException("priority is null");
//		if (release == null)  // release == null is allowed in OneShotEventHandler
//			throw new IllegalArgumentException("release is null");
		if (storage == null)
			throw new IllegalArgumentException("storage is null");
		this.priority = priority;
		this.release = release;
		this.storage = storage;
		this.config = config;
		this.name = name;
		this.mission = Mission.getMission();

		int backingStoreOfThisMemory;

		if (mission == null && this instanceof MissionSequencer) {
			backingStoreOfThisMemory = MemoryArea.getRemainingMemorySize();
			currentMemory = ImmortalMemory.instance();
		} else {
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

	//public abstract void handleAsyncEvent();

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

	Mission getMission() {
		return mission;
	}

	void setName(String name) {
		this.name = name;
	}
	
	PriorityParameters getPriorityParam() {
		return priority;
	}
	
	void setCurrentMemory(ManagedMemory current) {
		this.currentMemory = current;
	}

	ManagedMemory getCurrentMemory() {
		return currentMemory;
	}
	
	AffinitySet getAffinitySet() {
		return affinitySet;
	}
	
	// used for JML annotation only (not public)
	String getHandlerName() {
		return name;
	}
	
}



