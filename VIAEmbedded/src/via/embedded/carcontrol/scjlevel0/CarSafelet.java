package via.embedded.carcontrol.scjlevel0;

import javax.realtime.ConfigurationParameters;
import javax.safetycritical.CyclicExecutive;
import javax.safetycritical.LaunchLevel0;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;

import vm.Memory;

/**
 * Command prompt:
 * 
 *   cd /home/hso/git/hvm-scj/icecapvm/src
 * 
 * a) copy ...
  
        cp /home/hso/git/hvm-scj/icecaptoolstest/*.[ch] . 
   
   b) gcc compile ... 
      The JAVA_HEAP_SIZE and JAVA_STACK_SIZE may be smaller
     
      gcc -Wall -pedantic -O0 -g -DPC64 -DREF_OFFSET -DPRINTFSUPPORT -DSUPPORTGC -DJAVA_HEAP_SIZE=10240000 -L/usr/lib64 classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c rom_access.c print.c x86_64_interrupt.s -lpthread -lrt -lm

   c) ./a.out
  
 * @author hso
 *
 */
public class CarSafelet implements Safelet<CyclicExecutive>  {

	public static StorageParameters storageParameters_Sequencer;
	public static StorageParameters storageParameters_Handlers;
	public static ConfigurationParameters configParameters;		
	
	public MissionSequencer<CyclicExecutive> getSequencer() {
		MissionSequencer<CyclicExecutive> seq = new CarSequencer();      
	      return seq;
	}

	public long immortalMemorySize() {
		return Const.IMMORTAL_MEM;
	}

	public void initializeApplication() {
		
	}
	
	public static void main (String[] args) {
		System.out.println("\nCarSafelet Level 0 begin");
		
		Const.MEMORY_TRACKER_AREA_SIZE = 30000;
		Const.IMMORTAL_MEM = 30 * 1000;
		Const.OUTERMOST_SEQ_BACKING_STORE = 500;
		Const.OVERALL_BACKING_STORE = 200 * 1000;
		
		Memory.startMemoryAreaTracking();
		vm.Process.enableStackAnalysis();
		
		long immortal = 30000;
		long privateMem = 1000;
		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE, 150,
				immortal, 1000);

		storageParameters_Handlers = new StorageParameters(Const.PRIVATE_BACKING_STORE, privateMem, 0, 0);

		configParameters = new ConfigurationParameters(-1, -1, new long[] { Const.HANDLER_STACK_SIZE });
				
        new LaunchLevel0(new CarSafelet());
        System.out.println("CarSafelet Level 0 end \n");  
		vm.Process.reportStackUsage();        
        Memory.reportMemoryUsage();
	}
}
