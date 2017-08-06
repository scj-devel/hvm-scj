package javax.safetycritical;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.realtime.MemoryArea;
import javax.safetycritical.ManagedMemory;
import javax.safetycritical.annotate.Level;
import javax.scj.util.Const;

//import org.jmlspecs.utils.JmlAssertionError;



import vm.Machine;
import vm.MachineFactory;
import vm.Memory;
import vm.POSIX64BitMachineFactory;

public class LauncherTCK implements Runnable {
	
	MachineFactory mFactory;  // we use POSIX64BitMachineFactory() in this version
	
	Class <? extends Safelet> app;
	
	ImmortalMemory immMem;
	
	Safelet safeletApp;

	public LauncherTCK(Level level, Class<? extends Safelet> app) {
		
		Launcher.useOS = false;		
		Launcher.level = level.ordinal();
		if (Launcher.level > 2) throw new Error("LauncherTCK: Not correct level \n");
		
		this.mFactory = new POSIX64BitMachineFactory();
		this.app = app;
		
		setHandlers();	
		
		immMem = createImmortalMemory(); 
		
		immMem.executeInArea(this);
		System.out.println("LauncherTCK.constructor end");
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
		
		System.out.println("LauncherTCK.createImmortalMemory: " + Const.IMMORTAL_MEM);
		return new ImmortalMemory(Const.IMMORTAL_MEM);
	}

	@Override
	public void run() {
		// create object in Immortal Memory
		try {	
			System.out.println("LauncherTCK.run 1");
			Constructor<? extends Safelet> constructor = app.getConstructor(); 
			safeletApp = /*(Safelet)*/ constructor.newInstance();	
			System.out.println("LauncherTCK.run 1.2: safeletApp: " + safeletApp);
			long immSizeMustHave = safeletApp.immortalMemorySize();
			System.out.println("LauncherTCK.run 1.3");
			long remainingSize = immMem.memoryRemaining();
			System.out.println("LauncherTCK.run 3.1. ImmSize: " + immSizeMustHave + "; ImmRemaining: " + remainingSize);
			
			if (remainingSize < immSizeMustHave){ // the amount of remaining immortalMemory < immSizeMustHave
				
				safeletApp.handleStartupError(Safelet.INSUFFICIENT_IMMORTAL_MEMORY, immSizeMustHave - immMem.memoryRemaining());
			}	
			System.out.println("LauncherTCK.run 4");
			safeletApp.managedMemoryBackingStoreSize();
			
			System.out.println("LauncherTCK.run 5");
		    
		    // Level_0
		    if (Launcher.level == 0) {
		    	
			  safeletApp.initializeApplication();
			  MissionSequencer seq = safeletApp.getSequencer();
			  if (seq == null) throw new Error("*** LauncherTCK: run: Sequencer missing \n");
			
			  CyclicScheduler sch = CyclicScheduler.instance();
			  Machine.setCurrentScheduler(sch);	
			  sch.start(seq, mFactory);
		    } 
		    else	// Level_1 or Level_2
		    {
			  PriorityScheduler sch = PriorityScheduler.instance();			 
			  Machine.setCurrentScheduler(sch.prioritySchedulerImpl);			  
			  sch.insertReadyQueue(ScjProcess.createIdleProcess());
			  
			  System.out.println("LauncherTCK.run: safelet.getSequencer() ...");
			  
			  safeletApp.initializeApplication();
			  MissionSequencer seq = safeletApp.getSequencer();
			  System.out.println("LauncherTCK.run: safelet.getSequencer() end");
			  
			  // The sequencer inserts itself in PriorityScheduler
			  if (seq == null) throw new Error("LauncherTCK: run: Sequencer missing");
			  
			  PriorityScheduler.instance().start(mFactory);
		    }
		} 

		catch (InstantiationException e){
			System.out.println("LauncherTCK.run: InstantiationException: "+ e.getMessage());
			//throw e; //new Error("** LauncherTCK: Safelet: " + e.getMessage());
		}
		catch (IllegalAccessException e){
			System.out.println("LauncherTCK.run: IllegalAccessException: "+ e.getMessage());
			//throw new Error("** LauncherTCK: Safelet: " + e.getMessage());
	    }	
		catch (InvocationTargetException e){
			System.out.println("LauncherTCK.run: InvocationTargetException: "+ e.getMessage());
			new Error("** LauncherTCK.run: NoSuchMethodException: " + e.getMessage());
		}
		catch (NoSuchMethodException e){
			System.out.println("LauncherTCK.run: NoSuchMethodException: safelet cannot be created: "+ e.getMessage());
			new Error("** LauncherTCK: Safelet: " + e.getMessage());
		}
		catch (Throwable e){
			System.out.println("***LauncherTCK.run: Throwable: safelet cannot be created: "+ e.getMessage());
			throw new Error("** LauncherTCK.run: Throwable: " + e.getMessage());
		}
	}
	
	public Safelet getTestApplication() {
		return safeletApp;
	}
}
