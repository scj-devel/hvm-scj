package test;

import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.CyclicExecutive;
import javax.safetycritical.CyclicSchedule;
import javax.safetycritical.Frame;
import javax.safetycritical.LaunchLevel0;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.Memory;

public class TestSCJCyclicSchedule3LowMemory {
	private static class MyCyclicSchedule {
		static CyclicSchedule generate0(CyclicExecutive cyclicExec, PeriodicEventHandler[] handlers) {
			Frame[] frames = new Frame[2];

			PeriodicEventHandler[] frame0 = new PeriodicEventHandler[2];
			PeriodicEventHandler[] frame1 = new PeriodicEventHandler[1];

			RelativeTime frameDuration = new RelativeTime(500, 0, Clock.getRealtimeClock());

			frame0[0] = handlers[0];
			frame0[1] = handlers[1];

			frame1[0] = handlers[0];

			frames[0] = new Frame(frameDuration, frame0);
			frames[1] = new Frame(frameDuration, frame1);

			return new CyclicSchedule(frames);
		}

		static CyclicSchedule generate1(CyclicExecutive cyclicExec, PeriodicEventHandler[] handlers) {
			Frame[] frames = new Frame[2];

			PeriodicEventHandler[] frame0 = new PeriodicEventHandler[1];
			PeriodicEventHandler[] frame1 = new PeriodicEventHandler[2];

			RelativeTime frameDuration = new RelativeTime(500, 0, Clock.getRealtimeClock());

			frame0[0] = handlers[0];

			frame1[0] = handlers[0];
			frame1[1] = handlers[1];

			frames[0] = new Frame(frameDuration, frame0);
			frames[1] = new Frame(frameDuration, frame1);

			return new CyclicSchedule(frames);
		}
	}

	private static class MyPeriodicEvh1 extends PeriodicEventHandler {
		protected MyPeriodicEvh1(PriorityParameters priority, PeriodicParameters periodic,
				StorageParameters storageParameters) {
			super(priority, periodic, storageParameters);
		}

		public void handleAsyncEvent() {
			devices.Console.println("MyPev1");
			new Integer(89);
		}
	}

	private static class MyPeriodicEvh extends PeriodicEventHandler {
		Mission mission;
		int count = 0;

		public MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodicParameters,
				StorageParameters storageParameters, Mission mission) {
			super(priority, periodicParameters, storageParameters);
			this.mission = mission;
		}

		public void handleAsyncEvent() {
			count++;
			devices.Console.println("MyPev");
			if (count == 2)
				mission.requestTermination();
		}
	}

	private static class MyMission0 extends CyclicExecutive {
		public void initialize() {
			new MyPeriodicEvh(new PriorityParameters(Priorities.MIN_PRIORITY), new PeriodicParameters(new RelativeTime(
					Clock.getRealtimeClock()), new RelativeTime(500, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, this).register();

			new MyPeriodicEvh1(new PriorityParameters(Priorities.MIN_PRIORITY), new PeriodicParameters(
					new RelativeTime(), // start  
					new RelativeTime(1000, 0)), // period 
					storageParameters_Handlers).register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		public CyclicSchedule getSchedule(PeriodicEventHandler[] handlers) {
			return MyCyclicSchedule.generate0(this, handlers);
		}
	}

	private static class MyMission1 extends CyclicExecutive {
		public void initialize() {
			new MyPeriodicEvh(
				new PriorityParameters(Priorities.MIN_PRIORITY), 
				new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), 
						               new RelativeTime(500, 0, Clock.getRealtimeClock())),			
					storageParameters_Handlers, this).register();

			new MyPeriodicEvh1(new PriorityParameters(Priorities.MIN_PRIORITY), new PeriodicParameters(
					new RelativeTime(), // start  
					new RelativeTime(1000, 0)), // period 
					storageParameters_Handlers).register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		public CyclicSchedule getSchedule(PeriodicEventHandler[] handlers) {
			return MyCyclicSchedule.generate1(this, handlers);
		}
	}

	private static class MyApp implements Safelet<Mission> {
		public static int count = 0;

		public MissionSequencer<Mission> getSequencer() {
			devices.Console.println("** MyApp.getSequencer");
			return new MySequencer();
		}

		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}

		public void initializeApplication() {
		}

		private static class MySequencer extends MissionSequencer<Mission> {
			private Mission[] missions;
			private int active = 0;

			private Mission miss;

			MySequencer() {
				super(new PriorityParameters(Priorities.PR95), storageParameters_Sequencer);
				missions = new Mission[2];
				missions[0] = new MyMission0();
				missions[1] = new MyMission1();

				miss = missions[0];
			}

			public Mission getNextMission() {
				MyApp.count++;
				if (missions[active].terminationPending() && MyApp.count > 5) {
					devices.Console.println("MySeq.getNextMission: null");
					Memory.dumpLiveMemories();
					return null;
				} else {
					active = (active + 1) % missions.length;
					miss = missions[active];
					return miss;
				}
			}
		}
	}

	public static StorageParameters storageParameters_Sequencer;
	public static StorageParameters storageParameters_Handlers;

	public static void main(String[] args) {
//		Const.MEMORY_TRACKER_AREA_SIZE = 30000;
//		Memory.startMemoryAreaTracking();
//		vm.Process.enableStackAnalysis();
//		Const.OVERALL_BACKING_STORE = 51000;
//		Const.IMMORTAL_MEM = 18 * 1000;
//		Const.MISSION_MEM = 1000;
//		Const.HANDLER_STACK_SIZE = 1024;
		
		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, 1000, Const.IMMORTAL_MEM, Const.MISSION_MEM);

		storageParameters_Handlers = new StorageParameters(500, new long[] { Const.HANDLER_STACK_SIZE },
				500, 0, 0);

		devices.Console.println("\n****** TestSCJCyclicSchedule3LowMemory begin *********");
		new LaunchLevel0(new MyApp());
		devices.Console.println("****** TestSCJCyclicSchedule3LowMemory end *********");

		vm.Process.reportStackUsage();
		Memory.reportMemoryUsage();
		args = null;
	}
}
