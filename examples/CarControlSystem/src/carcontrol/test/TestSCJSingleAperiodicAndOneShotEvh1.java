package carcontrol.test;

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.HighResolutionTime;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.OneShotEventHandler;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.realtime.memory.ScopeParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.VMTest;

public class TestSCJSingleAperiodicAndOneShotEvh1 {
	
	private static int testCount;
	static {
		testCount = 0;
	}

	private static class MyAperiodicEvh extends AperiodicEventHandler {

		public MyAperiodicEvh(PriorityParameters priority,
				AperiodicParameters release,
				ScopeParameters storageParameters) {
			super(priority, release, storageParameters, configParameters);
		}

		public void handleAsyncEvent() {			
			System.out.println("***** Aperiodic event handler: handleAsyncEvent");
			Mission.getMission().requestTermination();
		}
	}
	
	private static class OneShotEvhStub extends OneShotEventHandler {
		
		AperiodicEventHandler apevh;
		
		public OneShotEvhStub(PriorityParameters priority, HighResolutionTime<?> releaseTime, AperiodicParameters release,
				ScopeParameters storage, AperiodicEventHandler apevh) {
			super(priority, releaseTime, release, storage, new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE }));
			this.apevh = apevh;			
		}

		public void handleAsyncEvent() {
			devices.Console.println("OneShotEvhStub.handleAsyncEvent: instead of interrupt \n");
			apevh.release();
		}
	}
	
	private static class MyMission extends Mission {
		
		public void initialize() {
			AperiodicEventHandler aevh = new MyAperiodicEvh(
					new PriorityParameters(Priorities.PR98),
					new AperiodicParameters(),
					storageParameters_Handlers);
			aevh.register();
			
			new OneShotEvhStub(
					new PriorityParameters(Priorities.MAX_PRIORITY), 
					new RelativeTime(1000, 0),  // release time = 1000 msecs
					new AperiodicParameters(new RelativeTime(500, 0), null), 
					storageParameters_Handlers,
					aevh).register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}
	}

	private static class MyApp implements Safelet {
		
		public MissionSequencer getSequencer() {
			return new MySequencer();
		}

		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}

		public void initializeApplication(String[] args) {
		}
		@Override
		public long managedMemoryBackingStoreSize() {
			System.out.println("HSO MyApp.managedMemoryBackingStoreSize called ");
			return Const.IMMORTAL_MEM_DEFAULT;
		}
			
		@Override
		public boolean handleStartupError(int cause, long val) {			
			System.out.println("HSO MyApp.handleStartupError called ");
			return false;
		}

		@Override
		public void cleanUp() {
			System.out.println("HSO MyApp.cleanUp called ");
		}

		private class MySequencer extends MissionSequencer {
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

	static ScopeParameters storageParameters_Sequencer;
	static ScopeParameters storageParameters_Handlers;
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
		storageParameters_Sequencer = new ScopeParameters(
				Const.OUTERMOST_SEQ_BACKING_STORE, Const.PRIVATE_MEM,
				Const.IMMORTAL_MEM, Const.MISSION_MEM);
		storageParameters_Handlers = new ScopeParameters(
				Const.PRIVATE_BACKING_STORE, Const.PRIVATE_MEM, 0,
				0);
		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console.println("***** TestSCJSingleAperiodicAndOneShotEvh1 begin *****");
		new LaunchLevel1(new MyApp());
		devices.Console.println("***** TestSCJSingleAperiodicAndOneShotEvh1 end *****");
		if (testCount == 3) {
			VMTest.markResult(false);
		}
	}
}