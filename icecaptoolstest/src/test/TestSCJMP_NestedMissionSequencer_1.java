package test;

import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.LaunchMulticore;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;

public class TestSCJMP_NestedMissionSequencer_1 implements Safelet<Mission> {
	public static StorageParameters storageParameters_Sequencer;
	public static StorageParameters storageParameters_Handlers;
	MissionSequencer<Mission> ms;

	
	
	
	private static class InnerMission3rd extends Mission {

		public void initialize() {
			PeriodicEventHandler pevh = new MyPeriodicEvh(new PriorityParameters(15), new PeriodicParameters(
					new RelativeTime(0, 0, Clock.getRealtimeClock()), new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_NestedMissionSequencer_1.storageParameters_Handlers, this);
			pevh.register();

			PeriodicEventHandler pevh1 = new MyPeriodicEvh1(new PriorityParameters(15), new PeriodicParameters(
					new RelativeTime(0, 0, Clock.getRealtimeClock()), new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_NestedMissionSequencer_1.storageParameters_Handlers);
			pevh1.register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		private static class MyPeriodicEvh extends PeriodicEventHandler {
			Mission m;
			int count = 0;

			public MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodicParameters,
					StorageParameters storage, Mission m) {
				super(priority, periodicParameters, storage);
				this.m = m;
			}

			public void handleAsyncEvent() {
				devices.Console.println("						1");
				count++;
				if (count == 5)
					m.requestTermination();
			}
		}

		private static class MyPeriodicEvh1 extends PeriodicEventHandler {

			public MyPeriodicEvh1(PriorityParameters priority, PeriodicParameters periodicParameters,
					StorageParameters storage) {
				super(priority, periodicParameters, storage);
			}

			public void handleAsyncEvent() {
//				devices.Console.println("						"+this.MissionMemoryConsumed());
				devices.Console.println("						2");
			}
		}
	}

	
	private static class InnerMission2nd extends Mission {
		
		public static StorageParameters storageParameters_InnerSequencer3 = new StorageParameters(
				100 * 1000, new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, 0,
				Const.MISSION_MEM_DEFAULT - 150 * 1000);

		public void initialize() {
			InnerSeq3rd seq3 = new InnerSeq3rd(new PriorityParameters(5), storageParameters_InnerSequencer3);
			seq3.register();
			
			PeriodicEventHandler pevh = new MyPeriodicEvh(new PriorityParameters(15), new PeriodicParameters(
					new RelativeTime(0, 0, Clock.getRealtimeClock()), new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_NestedMissionSequencer_1.storageParameters_Handlers, this);
			pevh.register();

			PeriodicEventHandler pevh1 = new MyPeriodicEvh1(new PriorityParameters(15), new PeriodicParameters(
					new RelativeTime(0, 0, Clock.getRealtimeClock()), new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_NestedMissionSequencer_1.storageParameters_Handlers);
			pevh1.register();
			
			
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		private static class MyPeriodicEvh extends PeriodicEventHandler {
			Mission m;
			int count = 0;

			public MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodicParameters,
					StorageParameters storage, Mission m) {
				super(priority, periodicParameters, storage);
				this.m = m;
			}

			public void handleAsyncEvent() {
				devices.Console.println("				1");
				count++;
				if (count == 10)
					m.requestTermination();
			}
		}

		private static class MyPeriodicEvh1 extends PeriodicEventHandler {

			public MyPeriodicEvh1(PriorityParameters priority, PeriodicParameters periodicParameters,
					StorageParameters storage) {
				super(priority, periodicParameters, storage);
			}

			public void handleAsyncEvent() {
//				devices.Console.println("				"+this.MissionMemoryConsumed());
				devices.Console.println("				2");
			}
		}
	}

	
	private static class InnerMission1st extends Mission {
		public static StorageParameters storageParameters_InnerSequencer2 = new StorageParameters(
				200 * 1000, new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, 0,
				Const.MISSION_MEM_DEFAULT - 130 * 1000);
		
		public void initialize() {
			InnerSeq2nd seq2 = new InnerSeq2nd(new PriorityParameters(5), storageParameters_InnerSequencer2);
			seq2.register();
			
			PeriodicEventHandler pevh = new MyPeriodicEvh(new PriorityParameters(15), new PeriodicParameters(
					new RelativeTime(0, 0, Clock.getRealtimeClock()), new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_NestedMissionSequencer_1.storageParameters_Handlers, this);
			pevh.register();

			PeriodicEventHandler pevh1 = new MyPeriodicEvh1(new PriorityParameters(15), new PeriodicParameters(
					new RelativeTime(0, 0, Clock.getRealtimeClock()), new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_NestedMissionSequencer_1.storageParameters_Handlers);
			pevh1.register();
			
			
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		private static class MyPeriodicEvh extends PeriodicEventHandler {
			Mission m;
			int count = 0;

			public MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodicParameters,
					StorageParameters storage, Mission m) {
				super(priority, periodicParameters, storage);
				this.m = m;
			}

			public void handleAsyncEvent() {
				devices.Console.println("		1");
				count++;
				if (count == 15)
					m.requestTermination();
			}
		}

		private static class MyPeriodicEvh1 extends PeriodicEventHandler {

			public MyPeriodicEvh1(PriorityParameters priority, PeriodicParameters periodicParameters,
					StorageParameters storage) {
				super(priority, periodicParameters, storage);
			}

			public void handleAsyncEvent() {
				devices.Console.println("		2");
			}
		}
	}

	
	private static class InnerSeq3rd extends MissionSequencer<Mission> {
		private int count = 0;

		public InnerSeq3rd(PriorityParameters priority, StorageParameters storage) {
			super(priority, storage, "InnerSeq 3nd");
		}

		@Override
		protected Mission getNextMission() {
			if (count < 1) {
				count++;
				return new InnerMission3rd();
			} else {
				devices.Console.println("						ms3 over");
				return null;
			}
		}
	}
	
	private static class InnerSeq2nd extends MissionSequencer<Mission> {
		private int count = 0;

		public InnerSeq2nd(PriorityParameters priority, StorageParameters storage) {
			super(priority, storage, "InnerSeq 2nd");
		}

		@Override
		protected Mission getNextMission() {
			if (count < 1) {
				count++;
				return new InnerMission2nd();
			} else {
				devices.Console.println("				ms2 over");
				return null;
			}
		}
	}
	
	private static class InnerSeq1st extends MissionSequencer<Mission> {
		private int count = 0;

		public InnerSeq1st(PriorityParameters priority, StorageParameters storage) {
			super(priority, storage, "InnerSeq 1st");
		}

		@Override
		protected Mission getNextMission() {
			if (count < 1) {
				count++;
				return new InnerMission1st();
			} else {
				devices.Console.println("		ms1 over");
				return null;
			}
		}
	}

	
	private static class TopLevelMission extends Mission {

		public static StorageParameters storageParameters_InnerSequencer1 = new StorageParameters(
				350 * 1000, new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, 0,
				Const.MISSION_MEM_DEFAULT - 150 * 1000);

		public void initialize() {
			InnerSeq1st ms1 = new InnerSeq1st(new PriorityParameters(5),
					storageParameters_InnerSequencer1);
			ms1.register();

			MyPeriodicEvh handler1 = new MyPeriodicEvh(new PriorityParameters(15),
					new PeriodicParameters(new RelativeTime(0, 0), new RelativeTime(1, 0)),
					TestSCJMP_NestedMissionSequencer_1.storageParameters_Handlers, this);
			handler1.register();

			MyPeriodicEvh1 handler2 = new MyPeriodicEvh1(new PriorityParameters(15),
					new PeriodicParameters(new RelativeTime(0, 0), new RelativeTime(1, 0)),
					TestSCJMP_NestedMissionSequencer_1.storageParameters_Handlers);
			handler2.register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		private static class MyPeriodicEvh extends PeriodicEventHandler {
			Mission m;
			int count = 0;

			public MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodicParameters,
					StorageParameters storage, Mission m) {
				super(priority, periodicParameters, storage);
				this.m = m;
			}

			public void handleAsyncEvent() {
				devices.Console.println("1");
				count++;
				if (count == 20)
					m.requestTermination();
			}
		}

		private static class MyPeriodicEvh1 extends PeriodicEventHandler {

			public MyPeriodicEvh1(PriorityParameters priority, PeriodicParameters periodicParameters,
					StorageParameters storage) {
				super(priority, periodicParameters, storage);
			}

			public void handleAsyncEvent() {
//				devices.Console.println(""+this.MissionMemoryConsumed());
				devices.Console.println("2");
			}
		}
	}
	
	public MissionSequencer<Mission> getSequencer() {
		ms = new MySequencer();
		return ms;
	}

	public long immortalMemorySize() {
		return Const.IMMORTAL_MEM;
	}

	public void initializeApplication() {
	}

	private class MySequencer extends MissionSequencer<Mission> {
		Mission m;
		int count = 0;

		MySequencer() {
			super(new PriorityParameters(5), storageParameters_Sequencer, "outer-ms");
			m = new TopLevelMission();
		}

		public Mission getNextMission() {
			if (count < 10) {
				count++;
				return m;
			} else {
				return null;
			}
		}
	}

	public static void main(String[] args) {
		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM,
				Const.IMMORTAL_MEM - 30 * 1000, Const.MISSION_MEM - 130 * 1000);

		storageParameters_Handlers = new StorageParameters(0,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM - 10 * 1000, 0, 0);

		

		devices.Console.println("\n***** test multicore nested mission sequencer1 main.begin ************");
		new LaunchMulticore(new TestSCJMP_NestedMissionSequencer_1(), 2);
		devices.Console.println("***** test multicore nested mission sequencer1 main.end **************");
		args = null;
	}
}
