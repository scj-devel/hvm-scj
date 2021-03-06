package test.ARM.ATSAMe70;

import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.CyclicExecutive;
import javax.safetycritical.CyclicSchedule;
import javax.safetycritical.Frame;
import javax.safetycritical.LaunchLevel0;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import devices.Console;
import devices.AVR.ATMega2560.ATMega2560SCJTargetConfiguration;
import devices.arm.ATSAMe70.ATSAMe70SCJTargetConfiguration;
import vm.Machine;
import vm.MachineFactory;
import vm.Memory;

@SuppressWarnings("rawtypes")
public class HelloSCJ extends ATSAMe70SCJTargetConfiguration  {

	private static class MyCyclicSchedule {
		static CyclicSchedule generate0(CyclicExecutive cyclicExec, PeriodicEventHandler[] handlers) {
			devices.Console.println("  *** MyCyclicSch: generate begin");
			Frame[] frames = new Frame[2];

			PeriodicEventHandler[] frame0 = new PeriodicEventHandler[2];
			PeriodicEventHandler[] frame1 = new PeriodicEventHandler[1];

			RelativeTime frameDuration = new RelativeTime(2000, 0, Clock.getRealtimeClock());

			frame0[0] = handlers[0];
			frame0[1] = handlers[1];

			frame1[0] = handlers[0];

			frames[0] = new Frame(frameDuration, frame0);
			frames[1] = new Frame(frameDuration, frame1);
			devices.Console.println("  *** MyCyclicSch: generated end");

			return new CyclicSchedule(frames);
		}
	}

	private static class MyPeriodicEvh1 extends PeriodicEventHandler {
		int n;

		protected MyPeriodicEvh1(PriorityParameters priority, PeriodicParameters periodic,
				ScopeParameters storageParameters, int n, MissionSequencer missSeq) {
			super(priority, periodic, storageParameters, configParameters);
			this.n = n;
		}

		public void handleAsyncEvent() {
			devices.Console.println("1");
			for (int i = 0; i < n; i++) {
				new Integer(i);
			}
		}
	}

	private static class MyPeriodicEvh2 extends PeriodicEventHandler {
		Mission mission;
		int count = 0;

		public MyPeriodicEvh2(PriorityParameters priority, PeriodicParameters periodicParameters,
				ScopeParameters storageParameters, Mission mission) {
			super(priority, periodicParameters, storageParameters, configParameters);
			this.mission = mission;
		}

		public void handleAsyncEvent() {
			devices.Console.println("2");
			count++;
			for (int i = 0; i < 2; i++) {
				new Integer(i);
			}

			if (count == 6) {
				if (!mimimalMemoryConfig) {
					Memory.dumpLiveMemories();
				}
				mission.requestTermination();
			}
		}
	}

	private static class MyMission extends CyclicExecutive {
		MissionSequencer missSeq;

		public MyMission(MissionSequencer missSeq) {
			this.missSeq = missSeq;
		}

		public void initialize() {
			new MyPeriodicEvh2(new PriorityParameters(Priorities.MIN_PRIORITY),
					new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()),
							new RelativeTime(50, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, this).register();

			new MyPeriodicEvh1(new PriorityParameters(Priorities.MIN_PRIORITY),
					new PeriodicParameters(new RelativeTime(), // start
							new RelativeTime(100, 0)), // period
					storageParameters_Handlers, 2, // used in handleAsyncEvent
					missSeq).register();

			devices.Console.println("*** MyMission0: initialize end");
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		public CyclicSchedule getSchedule(PeriodicEventHandler[] handlers) {
			devices.Console.println("  *** MyMission: getSchedule");
			return MyCyclicSchedule.generate0(this, handlers);
		}
	}

	private static class MyApp implements Safelet {

		public MissionSequencer getSequencer() {
			devices.Console.println("*");
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

		private static class MySequencer extends MissionSequencer {
			private Mission miss;

			MySequencer() {
				super(new PriorityParameters(Priorities.PR95), storageParameters_Sequencer, configParameters);

				miss = new MyMission(this);
			}

			public Mission getNextMission() {
				Mission mission = miss;
				miss = null;
				return mission;
			}
		}
	}

	public static ScopeParameters storageParameters_Sequencer;
	public static ScopeParameters storageParameters_Handlers;
	public static ConfigurationParameters configParameters;

/*
	private static class MyWriter extends DefaultWriter {

		@Override
		public short getMaxLineLength() {
			return 32;
		}
	}
*/
	private static final boolean mimimalMemoryConfig = false;

	public static void main(String[] args) {
		int handlerStackSize;
		int handlerMemorySize;

		init();
		
		devices.Console.println("Starting SCJ test");
		
		MachineFactory mFactory = Machine.getMachineFactory();

		if (mimimalMemoryConfig) {
			Const.OVERALL_BACKING_STORE = 4650; /* 34650 */
			Const.MEMORY_TRACKER_AREA_SIZE = 30000;
			Const.CYCLIC_SCHEDULER_STACK_SIZE = 340; /* 340 minimal on posix */
			Const.MISSION_MEM = 800;
			Const.IMMORTAL_MEM = 3810;
			handlerStackSize = 440; /* 440 minimal on posix */
			handlerMemorySize = 20;
		} else {
			Const.OVERALL_BACKING_STORE = 200000;
			Const.MEMORY_TRACKER_AREA_SIZE = 30000;
			Const.CYCLIC_SCHEDULER_STACK_SIZE = 2048; /* 340 minimal on posix */
			Const.MISSION_MEM = 40000;
			Const.IMMORTAL_MEM = 40000;
			handlerStackSize = 2048; /* 440 minimal on posix */
			handlerMemorySize = 20;
		}

		if (!mimimalMemoryConfig) {
			//Const.setDefaultErrorReporter();
			Memory.startMemoryAreaTracking();
			vm.Process.enableStackAnalysis();
		}
		storageParameters_Sequencer = new ScopeParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				Const.IMMORTAL_MEM, //new long[] { Const.HANDLER_STACK_SIZE },
				0, Const.MISSION_MEM);

		storageParameters_Handlers = new ScopeParameters(handlerMemorySize,
				0, //new long[] { Const.HANDLER_STACK_SIZE },
				handlerMemorySize, 0);
		configParameters = new ConfigurationParameters(-1, -1, new long[] { handlerStackSize });

		MyApp app = new MyApp();

		devices.Console.println("Setup complete - launching");
		new LaunchLevel0(app, mFactory);

		if (!mimimalMemoryConfig) {
			vm.Process.reportStackUsage();
			Memory.reportMemoryUsage();
		}
		
		devices.Console.println("done...");
		blink(20000);
	}

	@Override
	public int getJavaHeapSize() {
		return 256000;
	}
	
	@Override
	public String getOutputFolder() {
		return "C:\\home\\icecapout";
	}
}
