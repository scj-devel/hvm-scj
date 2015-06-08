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

public class TestSCJMP_AffinitySet_Level2_2 implements Safelet<Mission> {
	public static StorageParameters storageParameters_Sequencer;
	public static StorageParameters storageParameters_Handlers;
	public static StorageParameters storageParameters_InnerSequencer;
	public static Mission m;
	MissionSequencer<Mission> ms;
	public static AffinitySet[] sets;

	private static class MyPeriodicEvh extends PeriodicEventHandler {
		int count = 0;
		Mission m;

		public MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodicParameters,
				StorageParameters storage, Mission m) {
			super(priority, periodicParameters, storage);
			this.m = m;
		}

		public void handleAsyncEvent() {
			devices.Console.println("h");
			consume(100000);

			count++;
			if (count == 10) {
				m.requestTermination();
			}
		}
	}

	private static class TenThread extends Mission {
		public void initialize() {
			PeriodicEventHandler pevh = new MyPeriodicEvh(new PriorityParameters(50),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(40, 0, Clock.getRealtimeClock())),
					TestSCJMP_AffinitySet_Level2_2.storageParameters_Handlers, this);
			AffinitySet.setProcessorAffinity(AffinitySet.generate(0), pevh);
			pevh.register();

			PeriodicEventHandler pevh1 = new MyPeriodicEvh(new PriorityParameters(50),
					new PeriodicParameters(new RelativeTime(10, 0, Clock.getRealtimeClock()),
							new RelativeTime(40, 0, Clock.getRealtimeClock())),
					TestSCJMP_AffinitySet_Level2_2.storageParameters_Handlers, this);
			AffinitySet.setProcessorAffinity(AffinitySet.generate(1), pevh1);
			pevh1.register();

			PeriodicEventHandler pevh2 = new MyPeriodicEvh(new PriorityParameters(50),
					new PeriodicParameters(new RelativeTime(20, 0, Clock.getRealtimeClock()),
							new RelativeTime(40, 0, Clock.getRealtimeClock())),
					TestSCJMP_AffinitySet_Level2_2.storageParameters_Handlers, this);
			AffinitySet.setProcessorAffinity(AffinitySet.generate(2), pevh2);
			pevh2.register();

			PeriodicEventHandler pevh3 = new MyPeriodicEvh(new PriorityParameters(50),
					new PeriodicParameters(new RelativeTime(30, 0, Clock.getRealtimeClock()),
							new RelativeTime(40, 0, Clock.getRealtimeClock())),
					TestSCJMP_AffinitySet_Level2_2.storageParameters_Handlers, this);
			AffinitySet.setProcessorAffinity(AffinitySet.generate(3), pevh3);
			pevh3.register();

			PeriodicEventHandler pevh4 = new MyPeriodicEvh(new PriorityParameters(10),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_AffinitySet_Level2_2.storageParameters_Handlers, this){

						@Override
						public void handleAsyncEvent() {
							devices.Console.println("" + Services.getCurrentCPUID());
							consume(10000);
						}
				
			};
			pevh4.register();
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

		int count = 0;

		MySequencer() {
			super(new PriorityParameters(12), storageParameters_Sequencer, "outer-ms");
			m = new TenThread();
		}

		public Mission getNextMission() {
			sets = Services.getSchedulingAllocationDomains();
			if (count < 10) {
				devices.Console.println("new mission");
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
				Const.IMMORTAL_MEM - 30 * 1000, Const.MISSION_MEM /*- 150 * 1000*/);

		storageParameters_Handlers = new StorageParameters(0,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM - 10 * 1000, 0, 0);

		storageParameters_InnerSequencer = new StorageParameters(Const.PRIVATE_BACKING_STORE * 3
				+ Const.MISSION_MEM - 150 * 1000, new long[] { Const.HANDLER_STACK_SIZE },
				Const.PRIVATE_MEM, 0, Const.MISSION_MEM_DEFAULT - 150 * 1000);

		devices.Console.println("\n***** test multicore affinity set3 main.begin ************");
		new LaunchMulticore(new TestSCJMP_AffinitySet_Level2_2(), 2);
		devices.Console.println("***** test multicore affinity set3 main.end **************");
		args = null;
	}

	private static void consume(long time) {
		while (time > 0) {
			time--;

		}
	}

}
