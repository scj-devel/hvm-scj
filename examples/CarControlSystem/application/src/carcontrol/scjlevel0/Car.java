package carcontrol.scjlevel0;

import javax.realtime.ConfigurationParameters;
import javax.safetycritical.CyclicExecutive;
import javax.safetycritical.LaunchLevel0;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.Safelet;
import javax.realtime.memory.ScopeParameters;
import javax.scj.util.Const;
import javax.safetycritical.annotate.Level;

import carcontrol.configuration.Configuration;
import vm.Memory;

import carcontrol.infrastructure.Adapter;
import carcontrol.infrastructure.VMConfiguration;

/**
 * 1) Compile for HVM using CompilationManagerHSO
 *
 * 2) The usual steps in a Terminal command prompt:
 * 
 *    cd /home/hso/git/hvm-scj/icecapvm/src
 * 
 * a) copy ...
  
        cp /home/hso/git/hvm-scj/icecaptoolstest/*.[ch] . 
   
   b) gcc compile ... 
      The JAVA_HEAP_SIZE and JAVA_STACK_SIZE may be smaller
     
      //gcc -Wall -pedantic -O0 -g -DPC64 -DREF_OFFSET -DPRINTFSUPPORT -DSUPPORTGC -DJAVA_HEAP_SIZE=10240000 -L/usr/lib64 classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c rom_access.c print.c x86_64_interrupt.s -lpthread -lrt -lm

	  gcc -Wall -pedantic -Os -DPC64 -DREF_OFFSET -DPRINTFSUPPORT -DSUPPORTGC -DJAVA_HEAP_SIZE=1000000 -L/usr/lib64 classes.c icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c 	allocation_point.c rom_access.c print.c  x86_64_interrupt.s -lpthread -lrt -lm
   
   c) ./a.out
  
 * @author hso
 * 
 * New comment for test
 *
 */
public class Car implements Safelet  {
	
	CarConfiguration carConfig;
	
	// --- Safelet methods begin ------------------------------
	
	@Override
	public MissionSequencer getSequencer() {
		System.out.println("Car.getSequencer: " + CarConfiguration.SOnames.get(0));
		return new CarSequencer(CarConfiguration.SOnames.get(0));
	}

	@Override
	public long managedMemoryBackingStoreSize() {
		System.out.println("HSO Car.managedMemoryBackingStoreSize called ");
		return Const.IMMORTAL_MEM_DEFAULT;  // ToDo: returns the amount of additional backing store memory
	}
	
	@Override
	public long immortalMemorySize() {
		return Const.IMMORTAL_MEM;  
	}

	@Override
	public void initializeApplication(String[] args) {
		System.out.println("Car.initializeApplication");
		
		carConfig = new CarConfiguration();
		carConfig.initMissionMemSizes();
		carConfig.initSOConfigTable();
		
		carConfig.initCar();
	}	
		
	@Override
	public boolean handleStartupError(int cause, long val) {		
		System.out.println("HSO Car.handleStartupError called ");
		return false;
	}

	@Override
	public void cleanUp() {
		System.out.println("HSO Car.cleanUp called ");
	}

	
//	public static void main (String[] args) {
//		System.out.println("\nCar Level 0 begin");		
//		
//		if (VMConfiguration.MemoryTracking) {
//			Const.MEMORY_TRACKER_AREA_SIZE = 30000;
//			Memory.startMemoryAreaTracking();
//			vm.Process.enableStackAnalysis();
//					
//	        //new LaunchLevel0(new Car());  // old	        
//			Adapter.instance().launch(Level.LEVEL_0, Car.class);        
//	        
//	        vm.Process.reportStackUsage();        
//	        Memory.reportMemoryUsage();
//		} 
//		else {
//			Adapter.instance().launch(Level.LEVEL_0, Car.class);
//		}
//		
//		System.out.println("Car Level 0 end \n");  
//	}
}
