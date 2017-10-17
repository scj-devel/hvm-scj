package test;

import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.AffinitySet;
import javax.safetycritical.LaunchMulticore;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.Services;
import javax.scj.util.Const;

import vm.VMTest;

public class TestSCJMP_AffinitySet_Level2_2 implements Safelet {
	static ScopeParameters storageParameters_Sequencer;
	static ScopeParameters storageParameters_Handlers;
	static ScopeParameters storageParameters_InnerSequencer;
	static ConfigurationParameters configParameters;

	public static Mission m;
	MissionSequencer ms;
	public static AffinitySet[] sets;

	private static class MyPeriodicEvh extends PeriodicEventHandler {
		int count = 0;
		Mission m;

		public MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodicParameters,
				ScopeParameters storage, Mission m) {
			super(priority, periodicParameters, storage, configParameters);
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

	public MissionSequencer getSequencer() {
		ms = new MySequencer();
		return ms;
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

	private class MySequencer extends MissionSequencer {

		int count = 0;

		MySequencer() {
			super(new PriorityParameters(12), storageParameters_Sequencer, configParameters, "outer-ms");
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
		storageParameters_Sequencer = new ScopeParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				Const.IMMORTAL_MEM - 30 * 1000,
				Const.PRIVATE_MEM, Const.MISSION_MEM /*- 150 * 1000*/);

		storageParameters_Handlers = new ScopeParameters(0,
				0, Const.PRIVATE_MEM - 10 * 1000, 0);

		storageParameters_InnerSequencer = new ScopeParameters(Const.PRIVATE_BACKING_STORE * 3
				+ Const.MISSION_MEM - 150 * 1000, 
				0, Const.PRIVATE_MEM, Const.MISSION_MEM_DEFAULT - 150 * 1000);

		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console.println("\n***** test multicore affinity set3 main.begin ************");
		new LaunchMulticore(new TestSCJMP_AffinitySet_Level2_2(), 2);
		devices.Console.println("***** test multicore affinity set3 main.end **************");
		VMTest.markResult(false);
	}

	private static void consume(long time) {
		while (time > 0) {
			time--;

		}
	}

}
