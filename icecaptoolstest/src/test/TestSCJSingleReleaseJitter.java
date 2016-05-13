package test;

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.VMTest;

public class TestSCJSingleReleaseJitter {
	private static int testCount;
	private static boolean failed;
	static {
		testCount = 0;
		failed = true;
	}

	private static class MyPeriodicEvh extends PeriodicEventHandler {
		AperiodicEventHandler aevh;

		protected MyPeriodicEvh(PriorityParameters priority,
				PeriodicParameters periodic,
				StorageParameters storageParameters, AperiodicEventHandler aevh) {
			super(priority, periodic, storageParameters, configParameters);
			this.aevh = aevh;
		}

		public void handleAsyncEvent() {
			devices.Console.println("#############Releasing aperiodic event handler");
			aevh.release();
			testCount++;
		}
	}

	private static class MyAperiodicEvh extends AperiodicEventHandler {
		MissionSequencer<MyMission> missSeq;

		public MyAperiodicEvh(PriorityParameters priority,
				AperiodicParameters release,
				StorageParameters storageParameters, MissionSequencer<MyMission> missSeq) {
			super(priority, release, storageParameters, configParameters);
			this.missSeq = missSeq;
		}

		public void handleAsyncEvent() {
			if (testCount == 1)
			{
				devices.Console.println("This should not happen");
			}
			else
			{
				devices.Console.println("Higher priority handler got scheduled immediately (as supposed to)");
				failed = false;
			}
			missSeq.signalTermination();
		}
	}

	private static class MyMission extends Mission {
		MissionSequencer<MyMission> missSeq;

		public MyMission(MissionSequencer<MyMission> missSeq) {
			this.missSeq = missSeq;
		}

		public void initialize() {
			AperiodicEventHandler aevh = new MyAperiodicEvh(
					new PriorityParameters(Priorities.PR98),
					new AperiodicParameters(),
					storageParameters_Handlers, missSeq);
			aevh.register();
			PeriodicEventHandler pevh1 = new MyPeriodicEvh(
					new PriorityParameters(Priorities.PR97),
					new PeriodicParameters(new RelativeTime(Clock
							.getRealtimeClock()), // start
							new RelativeTime(200, 0, Clock.getRealtimeClock())), // period
					storageParameters_Handlers, aevh); // used in
															// pevh.handleAsyncEvent
			pevh1.register();
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

		devices.Console.println("***** TestSCJPrioritySchedule2 begin *****");
		new LaunchLevel1(new MyApp());
		devices.Console
				.println("***** TestSCJPrioritySchedule2 end *****");
		VMTest.markResult(failed);
	}
}