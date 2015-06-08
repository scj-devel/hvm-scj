package test;

import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.AffinitySet;
import javax.safetycritical.LaunchMulticore;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.Services;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;

public class TestSCJMP_AffinitySet_Level2_1 implements Safelet<Mission> {
	public static StorageParameters storageParameters_Sequencer;
	public static StorageParameters storageParameters_Handlers;
	public static StorageParameters storageParameters_InnerSequencer;
	public static int count = 0;
	MissionSequencer<Mission> ms;

	private static class InnerMission1st extends Mission {
		public void initialize() {
			PeriodicEventHandler pevh = new MyPeriodicEvh(new PriorityParameters(10),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_AffinitySet_Level2_1.storageParameters_Handlers, this);
			AffinitySet.setProcessorAffinity(AffinitySet.generate(1), pevh);
			pevh.register();

			PeriodicEventHandler pevh1 = new MyPeriodicEvh1(new PriorityParameters(10),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_AffinitySet_Level2_1.storageParameters_Handlers);
			AffinitySet.setProcessorAffinity(AffinitySet.generate(1), pevh1);
			pevh1.register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		private class MyPeriodicEvh extends PeriodicEventHandler {
			Mission m;
			int count = 0;

			public MyPeriodicEvh(PriorityParameters priority,
					PeriodicParameters periodicParameters, StorageParameters storage, Mission m) {
				super(priority, periodicParameters, storage);
				this.m = m;
			}

			public void handleAsyncEvent() {
				if (AffinitySet.getAffinitySet(this).getProcessorSet()[0] == Services.getCurrentCPUID()) {
					devices.Console.println("0");
				} else {
					devices.Console.println("Pevh1_1!");
					throw new IllegalArgumentException();
				}
				count++;
				if (count == 3)
					m.requestTermination();
			}
		}

		private class MyPeriodicEvh1 extends PeriodicEventHandler {

			public MyPeriodicEvh1(PriorityParameters priority,
					PeriodicParameters periodicParameters, StorageParameters storage) {
				super(priority, periodicParameters, storage);
			}

			public void handleAsyncEvent() {
				if (AffinitySet.getAffinitySet(this).getProcessorSet()[0] == Services.getCurrentCPUID()) {
					devices.Console.println("0");
				} else {
					devices.Console.println("Pevh1_2!");
					throw new IllegalArgumentException();
				}
			}
		}
	}

	private static class InnerMission2nd extends Mission {
		public void initialize() {
			PeriodicEventHandler pevh = new MyPeriodicEvh(new PriorityParameters(10),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_AffinitySet_Level2_1.storageParameters_Handlers, this);
			AffinitySet.setProcessorAffinity(AffinitySet.generate(1), pevh);
			pevh.register();

			PeriodicEventHandler pevh1 = new MyPeriodicEvh1(new PriorityParameters(10),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_AffinitySet_Level2_1.storageParameters_Handlers);
			AffinitySet.setProcessorAffinity(AffinitySet.generate(1), pevh1);
			pevh1.register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		private static class MyPeriodicEvh extends PeriodicEventHandler {
			Mission m;
			int count = 0;

			public MyPeriodicEvh(PriorityParameters priority,
					PeriodicParameters periodicParameters, StorageParameters storage, Mission m) {
				super(priority, periodicParameters, storage);
				this.m = m;
			}

			public void handleAsyncEvent() {
				if (AffinitySet.getAffinitySet(this).getProcessorSet()[0] == Services.getCurrentCPUID()) {
					devices.Console.println("		0");
				} else {
					devices.Console.println("		Pevh2_1!");
					throw new IllegalArgumentException();
				}

				count++;
				if (count == 3)
					m.requestTermination();
			}
		}

		private static class MyPeriodicEvh1 extends PeriodicEventHandler {

			public MyPeriodicEvh1(PriorityParameters priority,
					PeriodicParameters periodicParameters, StorageParameters storage) {
				super(priority, periodicParameters, storage);
			}

			public void handleAsyncEvent() {
				if (AffinitySet.getAffinitySet(this).getProcessorSet()[0] == Services.getCurrentCPUID()) {
					devices.Console.println("		0");
				} else {
					devices.Console.println("		Pevh2_2!");
					throw new IllegalArgumentException();
				}
			}
		}
	}

	private static class InnerMission3rd extends Mission {
		public void initialize() {
			PeriodicEventHandler pevh = new MyPeriodicEvh(new PriorityParameters(10),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_AffinitySet_Level2_1.storageParameters_Handlers, this);
			pevh.register();

			PeriodicEventHandler pevh1 = new MyPeriodicEvh1(new PriorityParameters(10),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_AffinitySet_Level2_1.storageParameters_Handlers);
			pevh1.register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		private static class MyPeriodicEvh extends PeriodicEventHandler {
			Mission m;
			int count = 0;

			public MyPeriodicEvh(PriorityParameters priority,
					PeriodicParameters periodicParameters, StorageParameters storage, Mission m) {
				super(priority, periodicParameters, storage);
				this.m = m;
			}

			public void handleAsyncEvent() {
				if (AffinitySet.getAffinitySet(this).getProcessorSet().length == Services.getAvailableCPUCount()) {
					devices.Console.println("				0");
				} else {
					devices.Console.println("				Pevh3_1!");
					throw new IllegalArgumentException();
				}

				count++;
				if (count == 3)
					m.requestTermination();
			}
		}

		private static class MyPeriodicEvh1 extends PeriodicEventHandler {

			public MyPeriodicEvh1(PriorityParameters priority,
					PeriodicParameters periodicParameters, StorageParameters storage) {
				super(priority, periodicParameters, storage);
			}

			public void handleAsyncEvent() {
				if (AffinitySet.getAffinitySet(this).getProcessorSet().length == Services.getAvailableCPUCount()) {
					devices.Console.println("				0");
				} else {
					devices.Console.println("				Pevh3_2!");
					throw new IllegalArgumentException();
				}
			}
		}
	}

	private static class InnerSeq3rd extends MissionSequencer<Mission> {
		private int count = 0;

		public InnerSeq3rd(PriorityParameters priority, StorageParameters storage) {
			super(priority, storage, "InnerSeq 3rd");
		}

		@Override
		protected Mission getNextMission() {
			if (count < 1) {
				count++;
				return new InnerMission3rd();
			} else {
				//				devices.Console.println("						ms3 over");
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
				//				devices.Console.println("				ms2 over");
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
				return null;
			}
		}
	}

	private static class TopLevelMission extends Mission {

		public void initialize() {
			InnerSeq1st ms1 = new InnerSeq1st(new PriorityParameters(5),
					TestSCJMP_AffinitySet_Level2_1.storageParameters_InnerSequencer);

			ms1.register();

			InnerSeq2nd ms2 = new InnerSeq2nd(new PriorityParameters(5),
					TestSCJMP_AffinitySet_Level2_1.storageParameters_InnerSequencer);

			ms2.register();

			InnerSeq3rd ms3 = new InnerSeq3rd(new PriorityParameters(5),
					TestSCJMP_AffinitySet_Level2_1.storageParameters_InnerSequencer);

			ms3.register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
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

		MySequencer() {
			super(new PriorityParameters(1), storageParameters_Sequencer, "outer-ms");
			m = new TopLevelMission();
		}

		public Mission getNextMission() {
			if (count < 10) {
				count++;
				devices.Console.println("new mission");
				return m;
			} else {
				return null;
			}

		}
	}

	public static void main(String[] args) {
		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM,
				Const.IMMORTAL_MEM - 30 * 1000, Const.MISSION_MEM - 150 * 1000);

		storageParameters_Handlers = new StorageParameters(0,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM - 10 * 1000, 0, 0);

		storageParameters_InnerSequencer = new StorageParameters(Const.PRIVATE_BACKING_STORE * 3
				+ Const.MISSION_MEM - 150 * 1000, new long[] { Const.HANDLER_STACK_SIZE },
				Const.PRIVATE_MEM, 0, Const.MISSION_MEM_DEFAULT - 150 * 1000);

		devices.Console.println("\n***** test multicore affinity set2 main.begin ************");
		new LaunchMulticore(new TestSCJMP_AffinitySet_Level2_1(), 2);
		devices.Console.println("***** test multicore affinity set2 main.end **************");
		args = null;
	}
}
