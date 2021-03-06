package test;

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.LaunchLevel2;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.Memory;
import vm.VMTest;

public class TestSCJSingleMemoryModel3 {

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
			//return 50 *1000;
			return Const.MISSION_MEM;
		}

		private class MyPEH extends PeriodicEventHandler {
			private int count = 0;
			private AperiodicEventHandler myAEH;

			public MyPEH(PriorityParameters priority, PeriodicParameters release, ScopeParameters storage,
					AperiodicEventHandler myAEH) {
				super(priority, release, storage, configParameters);
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

			public MyAEH(PriorityParameters priority, AperiodicParameters release, ScopeParameters storage, Mission m) {
				super(priority, release, storage, configParameters);
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
			return 50 * 1000;
		}

		private class MyPEH extends PeriodicEventHandler {
			private int count = 0;
			private AperiodicEventHandler myAEH;

			public MyPEH(PriorityParameters priority, PeriodicParameters release, ScopeParameters storage,
					AperiodicEventHandler myAEH) {
				super(priority, release, storage, configParameters);
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

			public MyAEH(PriorityParameters priority, AperiodicParameters release, ScopeParameters storage, Mission m) {
				super(priority, release, storage, configParameters);
				this.m = m;
			}

			@Override
			public void handleAsyncEvent() {
				devices.Console.println("						InnerMission 2 --- AEH: terminate mission 2");
				m.requestTermination();
			}
		}
	}

	private static class InnerSequencer2 extends MissionSequencer {
		private int count = 0;

		public InnerSequencer2(PriorityParameters priority, ScopeParameters storage) {
			super(priority, storage, configParameters, "InnerSeq2");
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
			return 50 * 1000;
		}

		private class MyPEH extends PeriodicEventHandler {
			private int count = 0;
			private AperiodicEventHandler myAEH;

			public MyPEH(PriorityParameters priority, PeriodicParameters release, ScopeParameters storage,
					AperiodicEventHandler myAEH) {
				super(priority, release, storage, configParameters);
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

			public MyAEH(PriorityParameters priority, AperiodicParameters release, ScopeParameters storage, Mission m) {
				super(priority, release, storage, configParameters);
				this.m = m;
			}

			@Override
			public void handleAsyncEvent() {
				devices.Console.println("InnerMission 1 --- AEH: terminate mission 1");
				m.requestTermination();
			}
		}
	}

	private static class InnerSequencer1 extends MissionSequencer {
		private int count = 0;

		public InnerSequencer1(PriorityParameters priority, ScopeParameters storage) {
			super(priority, storage, configParameters, "InnerSeq1");
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
			//return 100 * 1000;
			return Const.MISSION_MEM;
		}
	}

	private static class OuterMostSequencer extends MissionSequencer {
		private Mission[] missionArray;
		private int count = 0;

		public OuterMostSequencer(PriorityParameters priority, ScopeParameters storage) {
			super(priority, storage, configParameters, "OuterMostSeq");
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

	private static class MyApp implements Safelet {

		@Override
		public MissionSequencer getSequencer() {
			return new OuterMostSequencer(new PriorityParameters(Priorities.SEQUENCER_PRIORITY),
					storageParameters_OuterMostSequencer);
		}

		@Override
		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM; 
		}

		@Override
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
	}

	static ScopeParameters storageParameters_OuterMostSequencer;
	static ScopeParameters storageParameters_InnerSequencer;
	static ScopeParameters storageParameters_Handlers;
	static ConfigurationParameters configParameters;

	public static void main(String[] args) {
		
		Const.MEMORY_TRACKER_AREA_SIZE = 30000;
		Memory.startMemoryAreaTracking();
		//vm.Process.enableStackAnalysis();
		
//		storageParameters_OuterMostSequencer = 
//			//new ScopeParameters(Const.OUTERMOST_SEQ_BACKING_STORE, 0, Const.PRIVATE_MEM, Const.MISSION_MEM);
//			new ScopeParameters(Const.OUTERMOST_SEQ_BACKING_STORE, Const.IMMORTAL_MEM, Const.PRIVATE_MEM, Const.MISSION_MEM);
//
//		storageParameters_InnerSequencer = 
//			new ScopeParameters(100 * 1000, 0, 30 * 1000, 50 * 1000);
//
//		storageParameters_Handlers = 
//			//new ScopeParameters(Const.PRIVATE_MEM, 0, Const.PRIVATE_MEM, 0);
//			new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0);
		
		storageParameters_OuterMostSequencer = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 100 * 1000); // HSO	
		
		storageParameters_InnerSequencer = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 100 * 1000); // HSO	
		
		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO
		
		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console.println("\n***** MemoryModelTest3 main.begin ******************");
		new LaunchLevel2(new MyApp());
		devices.Console.println("***** MemoryModelTest3 main.end *******************");

		//vm.Process.reportStackUsage();
		Memory.reportMemoryUsage();
		
		VMTest.markResult(false);
	}

}
