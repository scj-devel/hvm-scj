package carcontrol.scjlevel1;

import javax.realtime.ConfigurationParameters;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.Safelet;
import javax.realtime.memory.ScopeParameters;
import javax.scj.util.Const;

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
		System.out.println("\nCarSafelet Level 1 begin");
		
		storageParameters_Sequencer = new ScopeParameters(Const.OUTERMOST_SEQ_BACKING_STORE, Const.PRIVATE_MEM,
				Const.IMMORTAL_MEM, Const.MISSION_MEM);

		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_BACKING_STORE, Const.PRIVATE_MEM, 0, 0);

		configParameters = new ConfigurationParameters(-1, -1, new long[] { Const.HANDLER_STACK_SIZE });
				
        new LaunchLevel1(new CarSafelet());
        System.out.println("CarSafelet Level 1 end \n");     
	}
}
