package carcontrol.scjlevel2;

import javax.realtime.ConfigurationParameters;
import javax.safetycritical.LaunchLevel2;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.Safelet;
import javax.realtime.memory.ScopeParameters;
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
public class CarSafelet implements Safelet  {

	public static ScopeParameters storageParameters_Sequencer;
	public static ScopeParameters storageParameters_Handlers;
	public static ConfigurationParameters configParameters;		
	
	public MissionSequencer getSequencer() {
		MissionSequencer seq = new CarSequencer();      
	      return seq;
	}

	public long immortalMemorySize() {
		return Const.IMMORTAL_MEM;
	}

	public void initializeApplication(String[] args) {
		
	}

	@Override
	public long managedMemoryBackingStoreSize() {
		System.out.println("HSO CarSafelet.managedMemoryBackingStoreSize called ");
		return Const.IMMORTAL_MEM_DEFAULT;
	}
		
	@Override
	public boolean handleStartupError(int cause, long val) {
		
		System.out.println("HSO CarSafelet.handleStartupError called ");
		return false;
	}

	@Override
	public void cleanUp() {
		System.out.println("HSO CarSafelet.cleanUp called ");
	}
	
	public static void main (String[] args) {
		System.out.println("\nCarSafelet Level 2 begin");
		Const.MEMORY_TRACKER_AREA_SIZE = 30000;

		Memory.startMemoryAreaTracking();
		storageParameters_Sequencer = new ScopeParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				Const.PRIVATE_MEM,
				Const.IMMORTAL_MEM - 30 * 1000, Const.MISSION_MEM - 130 * 1000);

		storageParameters_Handlers = new ScopeParameters(0,
				Const.PRIVATE_MEM - 10 * 1000, 0, 0);

		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });
				
        new LaunchLevel2(new CarSafelet());
        
        System.out.println("CarSafelet Level 2 end \n");    
        Memory.reportMemoryUsage();
	}
}
