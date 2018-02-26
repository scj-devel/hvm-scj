package javax.safetycritical;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.realtime.MemoryArea;
import javax.safetycritical.ManagedMemory;
import javax.safetycritical.annotate.Level;
import javax.scj.util.Const;

import vm.Machine;
import vm.MachineFactory;
import vm.Memory;

public class LauncherAtSame70 implements Runnable {
	
	MachineFactory mFactory;  // use a MachineFactory for SAME70 in this launcher
	
	Class <? extends Safelet> app;
	
	ImmortalMemory immMem;
	
	Safelet safeletApp;

	public LauncherAtSame70(Level level, Class<? extends Safelet> app, MachineFactory mFactory) {
		
		Launcher.useOS = false;		
		Launcher.level = level.ordinal();
		if (Launcher.level > 2) throw new Error("LauncherAtSame70: Not correct level \n");
		
		this.mFactory = mFactory; // use a correct MachineFactory (for SAME70) in this launcher
		this.app = app;
		
		setHandlers();	
		
		immMem = createImmortalMemory(); 
		
		immMem.executeInArea(this);
		System.out.println("LauncherAtSame70.constructor end");
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
		
		System.out.println("LauncherAtSame70.createImmortalMemory: " + Const.IMMORTAL_MEM);
		return new ImmortalMemory(Const.IMMORTAL_MEM);
	}

	@Override
	public void run() {
		// create object in Immortal Memory
		try {	
			System.out.println("LauncherAtSame70.run 1");
			Constructor<? extends Safelet> constructor = app.getConstructor();
			safeletApp = /*(Safelet)*/ constructor.newInstance();	
			System.out.println("LauncherAtSame70.run 1.2: safeletApp: " + safeletApp);
			long immSizeMustHave = safeletApp.immortalMemorySize();
			long remainingSize = immMem.memoryRemaining();
			System.out.println("LauncherAtSame70.run 3.1. ImmSize: " + immSizeMustHave + "; ImmRemaining: " + remainingSize);
			
//			if (remainingSize < immSizeMustHave){ // the amount of remaining immortalMemory < immSizeMustHave
//				
//				safeletApp.handleStartupError(Safelet.INSUFFICIENT_IMMORTAL_MEMORY, immSizeMustHave - immMem.memoryRemaining());
//			}
			safeletApp.managedMemoryBackingStoreSize();
			
			System.out.println("LauncherAtSame70.run 5");
		    
		    // Level_0
		    if (Launcher.level == 0) {
			  safeletApp.initializeApplication(null);
			  MissionSequencer seq = safeletApp.getSequencer();
			  if (seq == null) throw new Error("*** LauncherAtSame70: run: Sequencer missing \n");
		
			  CyclicScheduler sch = CyclicScheduler.instance();
			  
			  System.out.println("LauncherAtSame70.run 6.3: "+ sch);
			  Machine.setCurrentScheduler(sch);	
			  sch.start(seq, mFactory);
		    } 
		    else	// Level_1 or Level_2
		    {
			  PriorityScheduler sch = PriorityScheduler.instance();			 
			  Machine.setCurrentScheduler(sch.prioritySchedulerImpl);			  
			  sch.insertReadyQueue(ScjProcess.createIdleProcess());
			  
			  System.out.println("LauncherAtSame70.run: safelet.getSequencer() ...");
			  
			  safeletApp.initializeApplication(null);
			  MissionSequencer seq = safeletApp.getSequencer();
			  System.out.println("LauncherAtSame70.run: safelet.getSequencer() end");
			  
			  // The sequencer inserts itself in PriorityScheduler
			  if (seq == null) throw new Error("LauncherAtSame70: run: Sequencer missing");
			  
			  PriorityScheduler.instance().start(mFactory);
		    }
		} 

		catch (InstantiationException e){
			System.out.println("LauncherAtSame70.run: InstantiationException: "+ e.getMessage());
			//throw e; //new Error("** LauncherTCK: Safelet: " + e.getMessage());
		}
		catch (IllegalAccessException e){
			System.out.println("LauncherAtSame70.run: IllegalAccessException: "+ e.getMessage());
			//throw new Error("** LauncherTCK: Safelet: " + e.getMessage());
	    }	
		catch (InvocationTargetException e){
			System.out.println("LauncherAtSame70.run: InvocationTargetException: "+ e.getMessage());
			new Error("** LauncherAtSame70.run: NoSuchMethodException: " + e.getMessage());
		}
		catch (NoSuchMethodException e){
			System.out.println("LauncherAtSame70.run: NoSuchMethodException: safelet cannot be created: "+ e.getMessage());
			new Error("** LauncherAtSame70: Safelet: " + e.getMessage());
		}
		catch (Throwable e){
			System.out.println("***LauncherAtSame70.run: Throwable: safelet cannot be created: "+ e.getMessage());
			throw new Error("** LauncherAtSame70.run: Throwable: " + e.getMessage());
		}
	}
	
	public Safelet getTestApplication() {
		return safeletApp;
	}
}
