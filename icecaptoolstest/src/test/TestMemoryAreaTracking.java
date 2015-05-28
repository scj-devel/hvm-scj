package test;

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
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

import vm.Address32Bit;
import vm.HardwareObject;
import vm.Memory;

public class TestMemoryAreaTracking {
	private static class Light extends HardwareObject {

		public Light(int address) {
			super(new Address32Bit(address));
		}

		public void on(int colour) // 0 = green; 1 = red
		{
			if (colour == 0) {
				devices.Console.println("g on"); // green on
			} else
				devices.Console.println("r on"); // red on
		}

		public void off(int colour) {
			if (colour == 0) {
				devices.Console.println("g off"); // green off
			} else
				devices.Console.println("r off"); // red off
		}
	}

	private static class MyAperiodicEvh extends AperiodicEventHandler {
		Light light;
		MissionSequencer<MyMission> missSeq;

		public MyAperiodicEvh(PriorityParameters priority, AperiodicParameters release,
				StorageParameters storageParameters, Light light, MissionSequencer<MyMission> missSeq) {
			super(priority, release, storageParameters);
			this.light = light;
			this.missSeq = missSeq;
		}

		public void handleAsyncEvent() {
			missSeq.signalTermination();
			devices.Console.println("Ap Term");
			light.on(1);
		}
	}

	private static class MyPeriodicEvh extends PeriodicEventHandler {
		Light light;
		AperiodicEventHandler aevh;

		int count = 0;

		protected MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodic,
				StorageParameters storageParameters, Light light, AperiodicEventHandler aevh) {
			super(priority, periodic, storageParameters);
			this.light = light;
			this.aevh = aevh;
		}

		public void handleAsyncEvent() {
			light.on(0);
			count++;

			devices.Console.println("MyPEvh");

			if (count % 4 == 3)
				aevh.release();

			light.off(0);
		}
	}

	private static class MyMission extends Mission {
		MissionSequencer<MyMission> missSeq;

		public MyMission(MissionSequencer<MyMission> missSeq) {
			this.missSeq = missSeq;
		}

		public void initialize() {
			int address = 123456;
			Light light = new Light(address);

			AperiodicEventHandler aevh = new MyAperiodicEvh(new PriorityParameters(Priorities.PR98),
					new AperiodicParameters(new RelativeTime(500, 0), null), storageParameters_Handlers, light, missSeq);
			aevh.register();

			PeriodicEventHandler pevh1 = new MyPeriodicEvh(new PriorityParameters(Priorities.PR97),
					new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), // start
							new RelativeTime(2000, 0, Clock.getRealtimeClock())), // period
					storageParameters_Handlers, light, aevh); // used in pevh.handleAsyncEvent
			pevh1.register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}
	}

	private static class MyApp implements Safelet<MyMission> {
		public MissionSequencer<MyMission> getSequencer() {
			devices.Console.println("MyApp.getSeq");
			return new MySequencer();
		}

		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}

		public void initializeApplication() {
		}

		private static class MySequencer extends MissionSequencer<MyMission> {
			private MyMission mission;

			MySequencer() {
				super(new PriorityParameters(Priorities.PR95), storageParameters_Sequencer);

				mission = new MyMission(this);
			}

			public MyMission getNextMission() {
				if (mission.terminationPending()) {
					devices.Console.println("MySeq.NextMiss: null");
					return null;
				} else {
					devices.Console.println("MySeq.NextMiss");
					return mission;
				}
			}
		}
	}

	public static StorageParameters storageParameters_Sequencer;
	public static StorageParameters storageParameters_Handlers;

	public static void main(String[] args) {
		Const.OUTERMOST_SEQ_BACKING_STORE = 140 * 1000;
		Const.IMMORTAL_MEM = 20 * 1000;
		Const.MISSION_MEM = 23 * 1000;
		Const.PRIVATE_MEM = 2 * 1000;

		Const.MEMORY_TRACKER_AREA_SIZE = 30000;

		Memory.startMemoryAreaTracking();

		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, Const.IMMORTAL_MEM, Const.MISSION_MEM);

		storageParameters_Handlers = new StorageParameters(Const.PRIVATE_MEM, new long[] { Const.HANDLER_STACK_SIZE },
				Const.PRIVATE_MEM, 0, 0);

		devices.Console.println("\n***** TestSCJSimpleLowMemory begin *****");
		new LaunchLevel1(new MyApp());
		devices.Console.println("\n***** TestSCJSimpleLowMemory end *****");

		Memory.reportMemoryUsage();
		args = null;
	}

}
