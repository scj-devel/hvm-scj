package test;

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
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
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;


public class TestSCJMPApevhAndOneShotAndThread implements Safelet<Mission> {
	public static StorageParameters storageParameters_Sequencer;
	public static StorageParameters storageParameters_Handlers;
	public static StorageParameters storageParameters_InnerSequencer;
	public static Mission m;
	MissionSequencer<Mission> ms;
	public static AffinitySet[] sets;
	
	private static class TenThread extends Mission {
		static int count = 0;
		static OneShotEventHandler one;
	

		public void initialize() {
			MyAperiodicEvh aevh = new MyAperiodicEvh(new PriorityParameters(10),
					new AperiodicParameters(), TestSCJMPApevhAndOneShotAndThread.storageParameters_Handlers);
			AffinitySet.setProcessorAffinity(sets[2], aevh);
			aevh.register();

			PeriodicEventHandler pevh = new MyPeriodicEvh(new PriorityParameters(50),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					TestSCJMPApevhAndOneShotAndThread.storageParameters_Handlers, this, aevh);
			AffinitySet.setProcessorAffinity(sets[1], pevh);
			pevh.register();

			one = new OneShotEventHandler(new PriorityParameters(10), new RelativeTime(0, 0),
					new AperiodicParameters(), TestSCJMPApevhAndOneShotAndThread.storageParameters_Handlers) {

				@Override
				public void handleAsyncEvent() {
					devices.Console.println("One shot running!");
				}
			};
			AffinitySet.setProcessorAffinity(sets[3], one);
			one.register();
			
			ManagedThread thread = new ManagedThread(new PriorityParameters(50), storageParameters_Handlers){
				@Override
				public void run() {
					devices.Console.println("Managed Thread Running...");
				}
				
			};
			AffinitySet.setProcessorAffinity(sets[0], thread);
			thread.register();

		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		private static class MyPeriodicEvh extends PeriodicEventHandler {
			AperiodicEventHandler han;

			public MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodicParameters,
					StorageParameters storage, Mission m, AperiodicEventHandler han) {
				super(priority, periodicParameters, storage);
				this.han = han;
			}

			public void handleAsyncEvent() {
				devices.Console.println("1");
				if (count == 1) {
					one.scheduleNextReleaseTime(new RelativeTime(20, 0));
				}
				han.release();
			}
		}

		private static class MyAperiodicEvh extends AperiodicEventHandler {

			public MyAperiodicEvh(PriorityParameters priority, AperiodicParameters release,
					StorageParameters storage) {
				super(priority, release, storage);
			}

			@Override
			public void handleAsyncEvent() {
				devices.Console.println("5");
				//			devices.Console.println("true id: " + this.getID() + " id: " + OSProcess.getThreadID());
				count++;
				if (count == 50) {
					TestSCJMPApevhAndOneShotAndThread.m.requestTermination();
					count = 0;
				}
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
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, Const.IMMORTAL_MEM - 30 * 1000,
				Const.MISSION_MEM /*- 150 * 1000*/);

		storageParameters_Handlers = new StorageParameters(0, new long[] { Const.HANDLER_STACK_SIZE },
				Const.PRIVATE_MEM - 10 * 1000, 0, 0);

		storageParameters_InnerSequencer = new StorageParameters(Const.PRIVATE_BACKING_STORE * 3 + Const.MISSION_MEM
				- 150 * 1000, new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, 0,
				Const.MISSION_MEM_DEFAULT - 150 * 1000);

		devices.Console.println("\n***** test multicore aperiodic handler and thread main.begin ************");
		new LaunchMulticore(new TestSCJMPApevhAndOneShotAndThread(), 1);
		devices.Console.println("***** test multicore aperiodic handler and thread main.end **************");
		args = null;
	}
}




