package test;

import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.LaunchMulticore;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.scj.util.Const;

import vm.VMTest;

public class TestSCJMP_NestedMissionSequencer_2 implements Safelet {
	static ScopeParameters storageParameters_Sequencer;
	static ScopeParameters storageParameters_Handlers;
	static ScopeParameters storageParameters_InnerSequencer;
	static ConfigurationParameters configParameters;

	public static int count = 0;
	MissionSequencer ms;
	
	private static class InnerMission3rd extends Mission {
		public void initialize() {
			PeriodicEventHandler pevh = new MyPeriodicEvh(new PriorityParameters(10),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_NestedMissionSequencer_2.storageParameters_Handlers, this);
			pevh.register();

			PeriodicEventHandler pevh1 = new MyPeriodicEvh1(new PriorityParameters(10),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_NestedMissionSequencer_2.storageParameters_Handlers);
			pevh1.register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		private static class MyPeriodicEvh extends PeriodicEventHandler {
			Mission m;
			int count = 0;

			public MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodicParameters,
					ScopeParameters storage, Mission m) {
				super(priority, periodicParameters, storage, configParameters);
				this.m = m;
			}

			public void handleAsyncEvent() {
				devices.Console.println("						1");
				count++;
				if (count == 3)
					m.requestTermination();
			}
		}

		private static class MyPeriodicEvh1 extends PeriodicEventHandler {

			public MyPeriodicEvh1(PriorityParameters priority, PeriodicParameters periodicParameters,
					ScopeParameters storage) {
				super(priority, periodicParameters, storage, configParameters);
			}

			public void handleAsyncEvent() {
				devices.Console.println("						2");
			}
		}
	}

	private static class InnerMission2nd extends Mission {
		public void initialize() {
			PeriodicEventHandler pevh = new MyPeriodicEvh(new PriorityParameters(10),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_NestedMissionSequencer_2.storageParameters_Handlers, this);
			pevh.register();

			PeriodicEventHandler pevh1 = new MyPeriodicEvh1(new PriorityParameters(10),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_NestedMissionSequencer_2.storageParameters_Handlers);
			pevh1.register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		private static class MyPeriodicEvh extends PeriodicEventHandler {
			Mission m;
			int count = 0;

			public MyPeriodicEvh(PriorityParameters priority,
					PeriodicParameters periodicParameters, ScopeParameters storage, Mission m) {
				super(priority, periodicParameters, storage, configParameters);
				this.m = m;
			}

			public void handleAsyncEvent() {
				devices.Console.println("				1");
				count++;
				if (count == 3)
					m.requestTermination();
			}
		}

		private static class MyPeriodicEvh1 extends PeriodicEventHandler {

			public MyPeriodicEvh1(PriorityParameters priority,
					PeriodicParameters periodicParameters, ScopeParameters storage) {
				super(priority, periodicParameters, storage, configParameters);
			}

			public void handleAsyncEvent() {
				devices.Console.println("				2");
			}
		}
	}

	private static class InnerMission1st extends Mission {
		public void initialize() {
			PeriodicEventHandler pevh = new MyPeriodicEvh(new PriorityParameters(10),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_NestedMissionSequencer_2.storageParameters_Handlers, this);
			pevh.register();

			PeriodicEventHandler pevh1 = new MyPeriodicEvh1(new PriorityParameters(10),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_NestedMissionSequencer_2.storageParameters_Handlers);
			pevh1.register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		private static class MyPeriodicEvh extends PeriodicEventHandler {
			Mission m;
			int count = 0;

			public MyPeriodicEvh(PriorityParameters priority,
					PeriodicParameters periodicParameters, ScopeParameters storage, Mission m) {
				super(priority, periodicParameters, storage, configParameters);
				this.m = m;
			}

			public void handleAsyncEvent() {
				devices.Console.println("		1: " + TestSCJMP_NestedMissionSequencer_2.count);
				count++;
				if (count == 3)
					m.requestTermination();
			}
		}

		private static class MyPeriodicEvh1 extends PeriodicEventHandler {

			public MyPeriodicEvh1(PriorityParameters priority,
					PeriodicParameters periodicParameters, ScopeParameters storage) {
				super(priority, periodicParameters, storage, configParameters);
			}

			public void handleAsyncEvent() {
				devices.Console.println("		2");
			}
		}
	}

	private static class InnerSeq3rd extends MissionSequencer {
		private int count = 0;

		public InnerSeq3rd(PriorityParameters priority, ScopeParameters storage) {
			super(priority, storage, configParameters, "InnerSeq 3rd");
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

	private static class InnerSeq2nd extends MissionSequencer {
		private int count = 0;

		public InnerSeq2nd(PriorityParameters priority, ScopeParameters storage) {
			super(priority, storage, configParameters, "InnerSeq 2nd");
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

	private static class InnerSeq1st extends MissionSequencer {
		private int count = 0;

		public InnerSeq1st(PriorityParameters priority, ScopeParameters storage) {
			super(priority, storage,  configParameters, "InnerSeq 1st");
		}

		@Override
		protected Mission getNextMission() {
			if (count < 1) {
				count++;
				return new InnerMission1st();
			} else {
				//				devices.Console.println("		ms1 over");
				return null;
			}
		}
	}

	private static class TopLevelMission extends Mission {

		public void initialize() {
			InnerSeq1st ms1 = new InnerSeq1st(new PriorityParameters(5),
					TestSCJMP_NestedMissionSequencer_2.storageParameters_InnerSequencer);

			ms1.register();

			InnerSeq2nd ms2 = new InnerSeq2nd(new PriorityParameters(5),
					TestSCJMP_NestedMissionSequencer_2.storageParameters_InnerSequencer);

			ms2.register();

			InnerSeq3rd ms3 = new InnerSeq3rd(new PriorityParameters(5),
					TestSCJMP_NestedMissionSequencer_2.storageParameters_InnerSequencer);

			ms3.register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}
	}

	public MissionSequencer getSequencer() {
		ms = new MySequencer();
		return ms;
	}

	public long immortalMemorySize() {
		return Const.IMMORTAL_MEM;
	}

	public void initializeApplication() {
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
		Mission m;

		MySequencer() {
			super(new PriorityParameters(1), storageParameters_Sequencer, configParameters, "outer-ms");
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
		storageParameters_Sequencer = new ScopeParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				Const.IMMORTAL_MEM - 30 * 1000,
				Const.PRIVATE_MEM, Const.MISSION_MEM - 150 * 1000);

		storageParameters_Handlers = new ScopeParameters(0,
				0, Const.PRIVATE_MEM - 10 * 1000, 0);

		storageParameters_InnerSequencer = new ScopeParameters(Const.PRIVATE_BACKING_STORE * 3
				+ Const.MISSION_MEM - 150 * 1000, 
				0, Const.PRIVATE_MEM, Const.MISSION_MEM_DEFAULT - 150 * 1000);
		
		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console.println("\n***** test multicore nested mission sequencer2 main.begin ************");
		new LaunchMulticore(new TestSCJMP_NestedMissionSequencer_2(), 2);
		devices.Console.println("***** test multicore nested mission sequencer2 main.end **************");
		VMTest.markResult(false);
	}
}
