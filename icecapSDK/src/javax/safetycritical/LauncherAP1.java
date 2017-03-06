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

public class LauncherAP1 implements Runnable {
	
	protected static boolean useOS = false;
	protected static Level level;
	protected static MachineFactory mFactory;
	protected static Class<? extends Safelet> app;

	public LauncherAP1(Level level, Class<? extends Safelet> app) {
		useOS = false;
		mFactory = new POSIX64BitMachineFactory();
		LauncherAP1.app = app;
		LauncherAP1.level = level;
		
		setHandlers();	
		
		System.out.println("\nLauncherAP1.constructor ...");
		
		createImmortalMemory().executeInArea(this);
		
		System.out.println("\nLauncherAP1.constructor end");
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
		Safelet obj;
		try {
			Constructor<? extends Safelet> constructor = app.getConstructor();
			System.out.println ("nLauncherAP1.run:constructor: " + constructor.toString());
			
			obj = (Safelet) constructor.newInstance();
			System.out.println ("LauncherAP1.run: newInstance: " + obj);

		} catch (Throwable e){
			System.out.println("Safelet cannot be created: "+ e.getMessage());
			return;
		}
		
		try {
		  System.out.println("\nLauncherAP1.run 1");
		  obj.initializeApplication();
		  
		  System.out.println("\nLauncherAP1.run 2");
		  //MissionSequencer seq = obj.getSequencer();
		  
		  System.out.println("\nLauncherAP1.run 3");
		  //if (seq == null) throw new Exception("*** run: Sequencer missing");
		  
		  // Level_0
		  if (level == Level.LEVEL_0) {
			  MissionSequencer seq = obj.getSequencer();
			  if (seq == null) throw new Exception("*** run: Sequencer missing");
			  
			  CyclicScheduler sch = CyclicScheduler.instance();
			  System.out.println("\nLauncherAP1.run 04");
			  Machine.setCurrentScheduler(sch);	
			  System.out.println("\nLauncherAP1.run 05");
			  sch.start(seq, mFactory);
			  System.out.println("\nLauncherAP1.run 06");
		  } 
		  else	// Level_1 or Level_2
		  {
			  PriorityScheduler sch = PriorityScheduler.instance();
			  System.out.println("\nLauncherAP1.run 17");
			  Machine.setCurrentScheduler(sch.prioritySchedulerImpl);
			  System.out.println("\nLauncherAP1.run 18");
			  sch.insertReadyQueue(ScjProcess.createIdleProcess());
			  System.out.println("\nLauncherAP1.run 19");
			  
			  // APR: Det følgende virker sært
			  MissionSequencer seq = obj.getSequencer();
			  if (seq == null) throw new Exception("*** run: Sequencer missing");
			  
			  System.out.println("\nLauncherAP1.run 110");
			  
			  // er sagen den at den erklærede initial MissionSequencer ikke bruges??
			  // Nej den indsætter sig selv ved oprettelsen gennem xxHandlerBehaviour!!
			  PriorityScheduler.instance().start(mFactory);
			  System.out.println("\nLauncherAP1.run 111");
		  }
	    } 
		catch (Throwable e) {
			System.out.println("*** UPS: Launcher initialization error: "+ e.getMessage());  
		}
	}
}
