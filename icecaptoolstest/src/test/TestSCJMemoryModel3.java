package test;

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.LaunchLevel2;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

public class TestSCJMemoryModel3 {

	private static class TopMission2 extends Mission {
		protected void initialize() {
			AperiodicEventHandler myAEH = new MyAEH(new PriorityParameters(10), new AperiodicParameters(
					new RelativeTime(50, 0, Clock.getRealtimeClock()), null), storageParameters_Handlers, this);
			myAEH.register();

			PeriodicEventHandler myPEH = new MyPEH(new PriorityParameters(20), new PeriodicParameters(new RelativeTime(
					Clock.getRealtimeClock()), new RelativeTime(1000, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, myAEH);
			myPEH.register();
		}

		public long missionMemorySize() {
			return 50000;
		}

		private class MyPEH extends PeriodicEventHandler {
			private int count = 0;
			private AperiodicEventHandler myAEH;

			public MyPEH(PriorityParameters priority, PeriodicParameters release, StorageParameters storage,
					AperiodicEventHandler myAEH) {
				super(priority, release, storage);
				this.myAEH = myAEH;
			}

			@Override
			public void handleAsyncEvent() {
				devices.Console.println("Top Mission 2 --- PEH: " + count);
				count++;

				if (count == 3) {
					myAEH.release();
				}
			}
		}

		private class MyAEH extends AperiodicEventHandler {
			private Mission m;

			public MyAEH(PriorityParameters priority, AperiodicParameters release, StorageParameters storage, Mission m) {
				super(priority, release, storage);
				this.m = m;
			}

			@Override
			public void handleAsyncEvent() {
				devices.Console.println("Top Mission 2 --- AEH: terminate top mission 2");
				m.requestTermination();
			}
		}
	}

	private static class InnerMission2 extends Mission {
		protected void initialize() {
			AperiodicEventHandler myAEH = new MyAEH(new PriorityParameters(10), new AperiodicParameters(
					new RelativeTime(50, 0, Clock.getRealtimeClock()), null), storageParameters_Handlers, this);
			myAEH.register();

			PeriodicEventHandler myPEH = new MyPEH(new PriorityParameters(20), new PeriodicParameters(new RelativeTime(
					Clock.getRealtimeClock()), new RelativeTime(1000, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, myAEH);
			myPEH.register();
		}

		@Override
		public long missionMemorySize() {
			return 50000;
		}

		private class MyPEH extends PeriodicEventHandler {
			private int count = 0;
			private AperiodicEventHandler myAEH;

			public MyPEH(PriorityParameters priority, PeriodicParameters release, StorageParameters storage,
					AperiodicEventHandler myAEH) {
				super(priority, release, storage);
				this.myAEH = myAEH;
			}

			@Override
			public void handleAsyncEvent() {
				devices.Console.println("						InnerMission 2 --- PEH: " + count);
				count++;
				if (count == 5) {
					myAEH.release();
				}
			}
		}

		private class MyAEH extends AperiodicEventHandler {
			private Mission m;

			public MyAEH(PriorityParameters priority, AperiodicParameters release, StorageParameters storage, Mission m) {
				super(priority, release, storage);
				this.m = m;
			}

			@Override
			public void handleAsyncEvent() {
				devices.Console.println("						InnerMission 2 --- AEH: terminate mission 2");
				m.requestTermination();
			}
		}
	}

	private static class InnerSequencer2 extends MissionSequencer<Mission> {
		private int count = 0;

		public InnerSequencer2(PriorityParameters priority, StorageParameters storage) {
			super(priority, storage, "InnerSeq2");
		}

		@Override
		protected Mission getNextMission() {

			if (count == 0) {
				count++;
				devices.Console.println("2ed sequencer: has next misison");

				return new InnerMission2();
			} else {
				devices.Console.println("2ed sequencer: has no more misison");
				return null;
			}
		}

	}

	private static class InnerMission1 extends Mission {
		protected void initialize() {
			AperiodicEventHandler myAEH = new MyAEH(new PriorityParameters(15), new AperiodicParameters(
					new RelativeTime(50, 0, Clock.getRealtimeClock()), null), storageParameters_Handlers, this);
			myAEH.register();

			PeriodicEventHandler myPEH = new MyPEH(new PriorityParameters(20), new PeriodicParameters(new RelativeTime(
					Clock.getRealtimeClock()), new RelativeTime(1000, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, myAEH);
			myPEH.register();
		}

		@Override
		public long missionMemorySize() {
			return 100 * 1000;
		}

		private class MyPEH extends PeriodicEventHandler {
			private int count = 0;
			private AperiodicEventHandler myAEH;

			public MyPEH(PriorityParameters priority, PeriodicParameters release, StorageParameters storage,
					AperiodicEventHandler myAEH) {
				super(priority, release, storage);
				this.myAEH = myAEH;
			}

			@Override
			public void handleAsyncEvent() {
				devices.Console.println("InnerMission 1 --- PEH: " + count);
				count++;
				if (count == 3) {
					myAEH.release();
				}
			}
		}

		private class MyAEH extends AperiodicEventHandler {
			private Mission m;

			public MyAEH(PriorityParameters priority, AperiodicParameters release, StorageParameters storage, Mission m) {
				super(priority, release, storage);
				this.m = m;
			}

			@Override
			public void handleAsyncEvent() {
				devices.Console.println("InnerMission 1 --- AEH: terminate mission 1");
				m.requestTermination();
			}
		}
	}

	private static class InnerSequencer1 extends MissionSequencer<Mission> {
		private int count = 0;

		public InnerSequencer1(PriorityParameters priority, StorageParameters storage) {
			super(priority, storage, "InnerSeq1");
		}

		@Override
		protected Mission getNextMission() {
			devices.Console.println("		inner sequencer 1 is running. ");

			if (count < 2) {
				count++;
				devices.Console.println(" ");

				return new InnerMission1();
			} else {
				devices.Console.println("  inner sequencer 1: has no more misison: null");
				return null;
			}
		}
	}

	private static class TopMission1 extends Mission {

		protected void initialize() {
			devices.Console.println("TopMission1.initialize");
			InnerSequencer1 firstSeq = new InnerSequencer1(new PriorityParameters(Priorities.SEQUENCER_PRIORITY + 1),
					storageParameters_InnerSequencer);
			firstSeq.register();

			InnerSequencer2 secondSeq = new InnerSequencer2(new PriorityParameters(Priorities.SEQUENCER_PRIORITY + 1),
					storageParameters_InnerSequencer);
			secondSeq.register();
		}

		public long missionMemorySize() {
			return 100 * 1000;
		}
	}

	private static class OuterMostSequencer extends MissionSequencer<Mission> {
		private Mission[] missionArray;
		private int count = 0;

		public OuterMostSequencer(PriorityParameters priority, StorageParameters storage) {
			super(priority, storage, "OuterMostSeq");
			// initialize missions here
			missionArray = new Mission[2];
			missionArray[0] = new TopMission1();
			missionArray[1] = new TopMission2();
		}

		@Override
		protected Mission getNextMission() {
			if (count == 0) {
				devices.Console.println("\nMySeq.getNextMission: " + missionArray[0]);
				count++;
				return missionArray[0];
			}
			if (count == 1) {
				devices.Console.println("\nMySeq.getNextMission: " + missionArray[1]);
				count++;
				return missionArray[1];
			}

			devices.Console.println("\nMySeq.getNextMission: null");
			return null;

		}
	}

	private static class MyApp implements Safelet<Mission> {

		@Override
		public MissionSequencer<Mission> getSequencer() {
			return new OuterMostSequencer(new PriorityParameters(Priorities.SEQUENCER_PRIORITY),
					storageParameters_OuterMostSequencer);
		}

		@Override
		public long immortalMemorySize() {
			return 50 * 1000;
		}

		@Override
		public void initializeApplication() {
		}

	}

	static StorageParameters storageParameters_OuterMostSequencer;
	static StorageParameters storageParameters_InnerSequencer;
	static StorageParameters storageParameters_Handlers;

	public static void main(String[] args) {
		storageParameters_OuterMostSequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, 0, Const.MISSION_MEM);

		storageParameters_InnerSequencer = new StorageParameters(150 * 1000, new long[] { Const.HANDLER_STACK_SIZE },
				30 * 1000, 0, 50 * 1000);

		storageParameters_Handlers = new StorageParameters(Const.PRIVATE_MEM, // PRIVATE_BACKING_STORE, 
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, 0, 0);

		devices.Console.println("\n***** MemoryModelTest3 main.begin ******************");
		new LaunchLevel2(new MyApp());
		devices.Console.println("***** MemoryModelTest3 main.end *******************");

		args = null;
	}

}
