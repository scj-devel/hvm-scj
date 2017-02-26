package javax.safetycritical;


import java.lang.reflect.Constructor;

import javax.realtime.MemoryArea;
import javax.safetycritical.ManagedMemory;
import javax.safetycritical.annotate.Level;
import javax.scj.util.Const;

import vm.Machine;
import vm.MachineFactory;
import vm.Memory;
import vm.POSIX64BitMachineFactory;


public class LauncherAP implements Runnable {
	
	protected static boolean useOS = false;
	protected static Level level;
	//protected static MachineFactory mFactory;
	protected static Class<? extends Safelet> app;
	
	protected MachineFactory mFactory;
	//protected Safelet app;

	public LauncherAP(Level level, Class<? extends Safelet> app, MachineFactory mFactory) 
	
	//public LauncherAP(Level level, Safelet app, MachineFactory mFactory) 
	
	{
		useOS = false;
		
		//mFactory = new POSIX64BitMachineFactory();		
		this.mFactory = mFactory;
		
		LauncherAP.level = level;
		LauncherAP.app = app;
		
		//this.app = app;
		
	    Mission.missionBehaviour = new SinglecoreMissionBehavior();
		ManagedEventHandler.handlerBehavior = new SinglecoreHandlerBehavior();
		Services.servicesBehavior = new SinglecoreServicesBehavior();
		ManagedMemory.memoryBehavior = new SinglecoreMemoryBehavior();
		
		ManagedMemory.allocateBackingStore(Const.OVERALL_BACKING_STORE);
		
		createImmortalMemory();		
		ImmortalMemory.instance().executeInArea(this);
	}
	
	private void createImmortalMemory() {
		ManagedMemory.allocateBackingStore(Const.OVERALL_BACKING_STORE);
		if (Memory.memoryAreaTrackingEnabled) {
			new PrivateMemory(Const.MEMORY_TRACKER_AREA_SIZE, Const.MEMORY_TRACKER_AREA_SIZE,
					MemoryArea.overAllBackingStore, "MemTrk");
		}
		new ImmortalMemory(Const.IMMORTAL_MEM);
	}

	@Override
	public void run() {
		// create object in Immortal Memory
		try {
			Constructor<? extends Safelet> constructor = app.getConstructor(new Class[0]);
			Safelet obj = (Safelet) constructor.newInstance(new Object[0]);
			
			obj.initializeApplication();
			
			// Level_0
			if (level == Level.LEVEL_0) {
				
			  //MissionSequencer seq = obj.getSequencer();
				
			  CyclicScheduler sch = CyclicScheduler.instance();
			  Machine.setCurrentScheduler(sch);
			  
			  MissionSequencer seq = obj.getSequencer();
			  
			  
			  sch.start(seq, mFactory);
			} else
			// Level_1 or Level_2
			{
//			  PriorityScheduler sch = PriorityScheduler.instance();
//			  Machine.setCurrentScheduler(sch.prioritySchedulerImpl);
//			  sch.insertReadyQueue(ScjProcess.createIdleProcess());
//// APR: Det følgende virker sært
//			  obj.getSequencer();
//// er sagen den at den erklærede initial MissionSequencer ikke bruges??
//// Nej den indsætter sig selv ved oprettelsen gennem xxHandlerBehaviour!!
//			  PriorityScheduler.instance().start(mFactory);
			}
		} catch (Throwable e){
			System.out.println("Initialization error: "+ e.getMessage());
		}
	}
}

