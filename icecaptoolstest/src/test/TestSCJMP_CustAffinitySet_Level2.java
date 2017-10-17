package test;

import javax.realtime.AbsoluteTime;
import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.AffinitySet;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.LaunchMulticore;
import javax.safetycritical.ManagedThread;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.OneShotEventHandler;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.Services;
import javax.scj.util.Configuration;
import javax.scj.util.Const;

import vm.VMTest;

public class TestSCJMP_CustAffinitySet_Level2 implements Safelet {
	static ScopeParameters storageParameters_Sequencer;
	static ScopeParameters storageParameters_Handlers;
	static ScopeParameters storageParameters_InnerSequencer;
	static ConfigurationParameters configParameters;

	public static Mission m;
	MissionSequencer ms;
	public static AffinitySet[] sets;
	static int count = 0;
	static OneShotEventHandler one;

	private static class MyAperiodicEvh extends AperiodicEventHandler {

		public MyAperiodicEvh(PriorityParameters priority, AperiodicParameters release,
				ScopeParameters storage) {
			super(priority, release, storage, configParameters);
		}

		@Override
		public void handleAsyncEvent() {
			//			devices.Console.println("aph");
			if (Services.getAvailableCPUCount() == AffinitySet.getAffinitySet(this).getProcessorSet().length) {
				devices.Console.println("0");
			} else {
				devices.Console.println("Aperiodic!");
				throw new IllegalArgumentException();
			}
		}
	}

	private static class MyPeriodicEvh extends PeriodicEventHandler {
		AperiodicEventHandler han;
		int count = 0;
		Mission m;

		public MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodicParameters,
				ScopeParameters storage, Mission m, AperiodicEventHandler han) {
			super(priority, periodicParameters, storage, configParameters);
			this.han = han;
			this.m = m;
		}

		public void handleAsyncEvent() {
			//			devices.Console.println("ph");
			if (Services.getAvailableCPUCount() == AffinitySet.getAffinitySet(this).getProcessorSet().length) {
				devices.Console.println("0");
			} else {
				devices.Console.println("Periodic!");
				throw new IllegalArgumentException();
			}

			han.release();
			one.scheduleNextReleaseTime(new AbsoluteTime());

			count++;
			if (count == 10) {
				m.requestTermination();
			}
		}
	}

	private static class TenThread extends Mission {
		public void initialize() {
			AffinitySet.printAffinitySets();
			MyAperiodicEvh aevh = new MyAperiodicEvh(new PriorityParameters(30),
					new AperiodicParameters(),
					TestSCJMP_CustAffinitySet_Level2.storageParameters_Handlers);
			AffinitySet.setProcessorAffinity(sets[1], aevh);
			aevh.register();

			PeriodicEventHandler pevh = new MyPeriodicEvh(new PriorityParameters(50),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMP_CustAffinitySet_Level2.storageParameters_Handlers, this, aevh);
			AffinitySet.setProcessorAffinity(sets[2], pevh);
			pevh.register();

			one = new OneShotEventHandler(new PriorityParameters(10), new RelativeTime(0, 0),
					new AperiodicParameters(),
					storageParameters_Handlers, configParameters) {

				@Override
				public void handleAsyncEvent() {
					//					devices.Console.println("one");
					if (Services.getAvailableCPUCount() == AffinitySet.getAffinitySet(this).getProcessorSet().length) {
						devices.Console.println("0");
					} else {
						devices.Console.println("One Shot!");
						throw new IllegalArgumentException();
					}
				}
			};
			AffinitySet.setProcessorAffinity(sets[3], one);
			one.register();

			ManagedThread thread = new ManagedThread(new PriorityParameters(50),
					storageParameters_Handlers, configParameters) {
				@Override
				public void run() {
					if (Services.getAvailableCPUCount() == AffinitySet.getAffinitySet(this).getProcessorSet().length) {
						devices.Console.println("t0");
					} else {
						devices.Console.println("Thread!");
						throw new IllegalArgumentException();
					}
				}

			};
			AffinitySet.setProcessorAffinity(sets[0], thread);
			thread.register();

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
		Configuration.processors = new int[][] { { 0, 1, 2, 3 }, { 0, 1, 2 }, { 0, 1 }, { 0 } };
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
				Const.PRIVATE_MEM, Const.MISSION_MEM);

		storageParameters_Handlers = new ScopeParameters(0,
				0, Const.PRIVATE_MEM - 10 * 1000, 0);

		storageParameters_InnerSequencer = new ScopeParameters(Const.PRIVATE_BACKING_STORE * 3
				+ Const.MISSION_MEM - 150 * 1000, 
				0, Const.PRIVATE_MEM, Const.MISSION_MEM_DEFAULT - 150 * 1000);

		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console.println("\n***** test multicore affinity set5 main.begin ************");
		new LaunchMulticore(new TestSCJMP_CustAffinitySet_Level2(), 2);
		devices.Console.println("***** test multicore affinity set5 main.end **************");
		VMTest.markResult(false);
	}
}
