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

public class LauncherTCK implements Runnable {
	
	MachineFactory mFactory;  // we use POSIX64BitMachineFactory() in this version
	Class<? extends Safelet> app;

	public LauncherTCK(Level level, Class<? extends Safelet> app) {
		
		Launcher.useOS = false;		
		Launcher.level = level.ordinal();
		if (Launcher.level > 2) throw new Error("LauncherTCK: Not correct level \n");
		
		this.mFactory = new POSIX64BitMachineFactory();
		this.app = app;
		
		setHandlers();	
		
		createImmortalMemory().executeInArea(this);
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
		Safelet safelet;
		try {
			Constructor<? extends Safelet> constructor = app.getConstructor();			
			safelet = (Safelet) constructor.newInstance();

		} catch (Throwable e){
			System.out.println("LauncherTCK: safelet cannot be created: "+ e.getMessage());
			return;
		}
		
		try {
		  safelet.initializeApplication();
		  
		  // Level_0
		  if (Launcher.level == 0) {
			  MissionSequencer seq = safelet.getSequencer();
			  if (seq == null) throw new Exception("*** LauncherTCK: run: Sequencer missing");
			  
			  CyclicScheduler sch = CyclicScheduler.instance();
			  Machine.setCurrentScheduler(sch);	
			  sch.start(seq, mFactory);
		  } 
		  else	// Level_1 or Level_2
		  {
			  PriorityScheduler sch = PriorityScheduler.instance();			 
			  Machine.setCurrentScheduler(sch.prioritySchedulerImpl);			  
			  sch.insertReadyQueue(ScjProcess.createIdleProcess());
			  
			  MissionSequencer seq = safelet.getSequencer();
			  // The sequencer inserts itself in PriorityScheduler
			  if (seq == null) throw new Exception("LauncherTCK: run: Sequencer missing");
			  
			  PriorityScheduler.instance().start(mFactory);
		  }
	    } 
		catch (Throwable e) {
			System.out.println("LauncherTCK: UPS: Launcher initialization error: "+ e.getMessage());  
		}
	}
}
