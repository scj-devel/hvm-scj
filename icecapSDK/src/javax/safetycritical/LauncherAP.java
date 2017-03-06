package javax.safetycritical;


import javax.realtime.MemoryArea;
import javax.safetycritical.annotate.Level;
import javax.scj.util.Const;

import vm.Machine;
import vm.MachineFactory;
import vm.Memory;
import vm.POSIX64BitMachineFactory;


public class LauncherAP implements Runnable {
	
	protected static boolean useOS = false;
	protected static Level level;
	protected static MachineFactory mFactory;
	protected static Safelet app;
	
	public LauncherAP(Level level, Safelet app)	
	{ 		
		useOS = false;		
		mFactory = Machine.getMachineFactory();
		
		LauncherAP.level = level;
		LauncherAP.app = app;
		
		setHandlers();
		
		System.out.println("\nLauncherAP.constructor");
		
		createImmortalMemory().executeInArea(this);	
		
		System.out.println("\nLauncherAP.constructor end");
	}
	
	private void setHandlers() {
		Mission.missionBehaviour = new SinglecoreMissionBehavior();
		ManagedEventHandler.handlerBehavior = new SinglecoreHandlerBehavior();
		Services.servicesBehavior = new SinglecoreServicesBehavior();
		ManagedMemory.memoryBehavior = new SinglecoreMemoryBehavior();	
	}
	
	private ImmortalMemory createImmortalMemory() {
		ManagedMemory.allocateBackingStore(Const.OVERALL_BACKING_STORE);
		if (Memory.memoryAreaTrackingEnabled) {
			new PrivateMemory(Const.MEMORY_TRACKER_AREA_SIZE, Const.MEMORY_TRACKER_AREA_SIZE,
					MemoryArea.overAllBackingStore, "MemTrk");
		}
		return new ImmortalMemory(Const.IMMORTAL_MEM);
	}

	@Override
	public void run() {
		// create object in Immortal Memory
		try {			
			app.initializeApplication();
			
			// Level_0
			if (level == Level.LEVEL_0) {
				
			  MissionSequencer seq = app.getSequencer();
				 
			  CyclicScheduler sch = CyclicScheduler.instance();
			  Machine.setCurrentScheduler(sch);
			  
			  System.out.println("\nLauncherAP.run 0_1: " + seq);
			  
			  sch.start(seq, mFactory);
			  
			  System.out.println("\nLauncherAP.run 0_2");
			} 
			else
			// Level_1 or Level_2
			{
			  PriorityScheduler sch = PriorityScheduler.instance();
			  System.out.println("\nLauncherAP.run 1_1");
			  Machine.setCurrentScheduler(sch.prioritySchedulerImpl);
			  System.out.println("\nLauncherAP.run 1_2");
			  sch.insertReadyQueue(ScjProcess.createIdleProcess());
			  System.out.println("\nLauncherAP.run 1_3");
			  
			  // APR: Det følgende virker sært
			  app.getSequencer();
			  System.out.println("\nLauncherAP.run 1_4");
			  
			  // er sagen den at den erklærede initial MissionSequencer ikke bruges??
			  // Nej den indsætter sig selv ved oprettelsen gennem xxHandlerBehaviour!!
			  PriorityScheduler.instance().start(mFactory);
			  System.out.println("\nLauncherAP.run 1_5");
			}
		} catch (Throwable e){
			System.out.println("*** LauncherAP.Initialization error: "+ e.getMessage());
			System.out.println("*** LauncherAP.Initialization error: ToDo: report should be updated");
			//if (app instanceof Test)
			
		}
	}
}

