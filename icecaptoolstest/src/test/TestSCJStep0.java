package test;

import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.Memory;

public class TestSCJStep0 {

	private static class Counter extends PeriodicEventHandler {
		private int count = 0;
		private Mission owner;

		public Counter(PriorityParameters priority,
				PeriodicParameters periodicParameters,
				StorageParameters storage, Mission owner) {
			super(priority, periodicParameters, storage);
			this.count = 0;
			this.owner = owner;
		}

		public void handleAsyncEvent() {
			count++;

			if (count == 10) {
				owner.requestTermination();
				while (true) {
					;
				}
			}
		}
	}

	private static class Mission1 extends Mission {
		public void initialize() {
			Clock clk = Clock.getRealtimeClock();

			PeriodicEventHandler pevh = new Counter(new PriorityParameters(
					Priorities.PR98), new PeriodicParameters(new RelativeTime(
					clk), new RelativeTime(200, 0, clk)), null, this);
			pevh.register();
		}

		@Override
		public long missionMemorySize() {
			return 0;
		}
	}

	private static class TopLevelSequencer extends MissionSequencer<Mission> {
		@SuppressWarnings("unused")
		private Mission1 mission;

		TopLevelSequencer() {
			super(new PriorityParameters(Priorities.PR94), new StorageParameters(0, null, 0, 0, 0));
			this.mission = new Mission1();
		}

		public Mission getNextMission() {
			return null;
		}
	}

	private static class Application implements Safelet<Mission> {
		public MissionSequencer<Mission> getSequencer() {
			return new TopLevelSequencer();
		}

		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}

		public void initializeApplication() {
		}
	}

	public static void main(String[] args) {
		Memory.startMemoryAreaTracking();
		devices.Console
				.println("\n********** Starting application ***********");
		new LaunchLevel1(new Application());
		devices.Console.println("********* Application done *****************");
		Memory.reportMemoryUsage();
		args = null;
	}

}
