package test;

import javax.realtime.AperiodicParameters;
import javax.realtime.ConfigurationParameters;
import javax.realtime.HighResolutionTime;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;

import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.ManagedInterruptServiceRoutine;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.OneShotEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;

import javax.scj.util.Const;
import javax.scj.util.Priorities;
import vm.VMTest;

public class TestSCJSingleInterrupt1 {
	private static int testCount;
	static {
		testCount = 0;
	}

	private static class MyAperiodicEvh extends AperiodicEventHandler {
		
		public MyAperiodicEvh(PriorityParameters priority,
				AperiodicParameters release,
				StorageParameters storageParameters) {
			super(priority, release, storageParameters, configParameters);
		}

		public void handleAsyncEvent() {			
			System.out.println("***** Aperiodic event handler: handleAsyncEvent");
			Mission.getMission().requestTermination();
		}
	}
	
	private static class ManagedISR extends ManagedInterruptServiceRoutine {
		
		AperiodicEventHandler apevh; 
		
		public ManagedISR(long sizes, AperiodicEventHandler apevh) {
			super(sizes);
			this.apevh = apevh;
		}
		
		public void handle() {
			
			System.out.println("ManagedISR.handle calls apevh.release()");
			apevh.release();

		}
	}
	
	private static class OneShotEvhStub extends OneShotEventHandler {
		
		ManagedISR isr;
		
		public OneShotEvhStub(PriorityParameters priority, HighResolutionTime<?> releaseTime, AperiodicParameters release,
				StorageParameters storage, ManagedISR isr) {
			super(priority, releaseTime, release, storage, new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE }));
			this.isr = isr;			
		}

		public void handleAsyncEvent() {
			devices.Console.println("OneShotEvhStub.handleAsyncEvent: instead of interrupt \n");
			isr.handle();
		}
	}


	private static class MyMission extends Mission {

		public void initialize() {
			AperiodicEventHandler aevh = new MyAperiodicEvh(
					new PriorityParameters(Priorities.PR98),
					new AperiodicParameters(),
					storageParameters_Handlers);
			aevh.register();
			
			ManagedISR isr = new ManagedISR(Const.PRIVATE_MEM_DEFAULT, aevh);
//			try {
//				isr_Parking.register(1);
//			}
//			catch (RegistrationException e) {
//				System.err.println("In MissionParking.initialize: RegistrationException: " + e.getMessage());
//			}
			
			new OneShotEvhStub(
					new PriorityParameters(Priorities.MAX_PRIORITY), 
					new RelativeTime(2*1000, 0),  // release time = 2 msecs
					new AperiodicParameters(new RelativeTime(500, 0), null), 
					storageParameters_Handlers,
					isr).register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}
	}

	private static class MyApp implements Safelet<MyMission> {
		public MissionSequencer<MyMission> getSequencer() {
			return new MySequencer();
		}

		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}

		public void initializeApplication() {
		}

		private class MySequencer extends MissionSequencer<MyMission> {
			private MyMission mission;

			MySequencer() {
				super(new PriorityParameters(Priorities.PR95),
						storageParameters_Sequencer, configParameters);
				mission = new MyMission();
			}

			public MyMission getNextMission() {
				if (mission.terminationPending()) {
					return null;
				} else {
					return mission;
				}
			}
		}
	}

	static StorageParameters storageParameters_Sequencer;
	static StorageParameters storageParameters_Handlers;
	static ConfigurationParameters configParameters;

	/**
	 * Compiling for the PC:
	 *
	 * gcc -Wall -pedantic -g -Os -DPC64 -DPRINTFSUPPORT -DJAVA_STACK_SIZE=768
	 * -DJAVA_HEAP_SIZE=15360 classes.c icecapvm.c methodinterpreter.c methods.c
	 * gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c
	 * rom_access.c x86_64_interrupt.s -lpthread -lrt
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		storageParameters_Sequencer = new StorageParameters(
				Const.OUTERMOST_SEQ_BACKING_STORE, Const.PRIVATE_MEM,
				Const.IMMORTAL_MEM, Const.MISSION_MEM);
		storageParameters_Handlers = new StorageParameters(
				Const.PRIVATE_BACKING_STORE, Const.PRIVATE_MEM, 0,
				0);
		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console.println("***** TestSCJSingleInterrupt1 begin *****");
		new LaunchLevel1(new MyApp());
		devices.Console.println("***** TestSCJSingleInterrupt1 end *****");
		if (testCount == 3) {
			VMTest.markResult(false);
		}
	}
}