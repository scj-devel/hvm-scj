package test;

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.VMTest;

public class TestSCJSingleProcessException {
	private static int testCount;
	static {
		testCount = 0;
	}

	private static class MyPeriodicEvh extends PeriodicEventHandler {
		int n;
		AperiodicEventHandler aevh;
		int count = 0;

		protected MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodic,
				ScopeParameters storageParameters, int n, AperiodicEventHandler aevh) {
			super(priority, periodic, storageParameters, configParameters);
			this.n = n;
			this.aevh = aevh;
		}

		public void handleAsyncEvent() {
			count++;
			devices.Console.println("----------Running periodic handler: " + count);
			for (int i = 0; i < n; i++) {
				new Integer(count);
			}
			if (count % 3 == 2 && n == 2) {
				aevh.release();
				/*devices.Console.println("#############Throwing exception");
				while (true) {
					count = count / n;
					n = n - 1;
				}*/
			}
		}
	}

	private static class MyAperiodicEvh extends AperiodicEventHandler {
		int n;
		MissionSequencer missSeq;
		int count = 0;

		public MyAperiodicEvh(PriorityParameters priority, AperiodicParameters release,
				ScopeParameters storageParameters, int n, MissionSequencer missSeq) {
			super(priority, release, storageParameters, configParameters);
			this.n = n;
			this.missSeq = missSeq;
		}

		public void handleAsyncEvent() {
			count++;
			devices.Console.println("*****Running aperiodic handler: " + count);
			for (int i = 0; i < n; i++) {
				new Integer(count);
			}
			testCount++;
			if (count == 3)
				missSeq.signalTermination();
		}
	}

	private static class MyMission extends Mission {
		MissionSequencer missSeq;

		public MyMission(MissionSequencer missSeq) {
			this.missSeq = missSeq;
		}

		public void initialize() {
			AperiodicEventHandler aevh = new MyAperiodicEvh(new PriorityParameters(Priorities.PR98),
					new AperiodicParameters(),
					//new AperiodicParameters(new RelativeTime(50, 0, Clock
					//.getRealtimeClock()), null),
					storageParameters_Handlers, 1, missSeq);
			aevh.register();
			PeriodicEventHandler pevh1 = new MyPeriodicEvh(new PriorityParameters(Priorities.PR97),
					new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), // start
							new RelativeTime(200, 0, Clock.getRealtimeClock())), // period
					storageParameters_Handlers, 2, aevh); // used in
															// pevh.handleAsyncEvent
			pevh1.register();
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
		
		public long managedMemoryBackingStoreSize() {
			return 0;
		}
		
		public final boolean handleStartupError(int cause, long val) {
			return false;
		}
		
		public void cleanUp() {
		}

		private class MySequencer extends MissionSequencer {
			private MyMission mission;

			MySequencer() {
				super(new PriorityParameters(Priorities.PR95), 
						storageParameters_Sequencer, configParameters);
				mission = new MyMission(this);
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
//		storageParameters_Sequencer = new ScopeParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
//				Const.IMMORTAL_MEM, Const.PRIVATE_MEM, Const.MISSION_MEM);
//		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_BACKING_STORE,
//				0, Const.PRIVATE_MEM, 0);
		
		storageParameters_Sequencer = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO		
		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO
		
		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console.println("***** TestSCJPrioritySchedule2 begin *****");
		new LaunchLevel1(new MyApp());
		devices.Console.println("***** TestSCJPrioritySchedule2 end *****");
		if (testCount == 3) {
			VMTest.markResult(false);
		}
	}
}