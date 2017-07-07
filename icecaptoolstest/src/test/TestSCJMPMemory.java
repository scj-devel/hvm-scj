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
import javax.safetycritical.Services;
import javax.scj.util.Const;

import vm.VMTest;

public class TestSCJMPMemory {
	public static MissionSequencer ms;

	private static class MyPeriodicEvh extends PeriodicEventHandler {
		int i = 0;
		Mission m;

		public MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodicParameters,
				ScopeParameters storage, Mission m, String name) {
			super(priority, periodicParameters, storage, configParameters);
			this.m = m;
		}

		public void handleAsyncEvent() {
			if (Services.getNameOfCurrentMemoryArea().equals("PvtMem")) {
				devices.Console.println("1");
			} else {
				devices.Console.println("5 " + Services.getNameOfCurrentMemoryArea());
			}
			
			i++;
			if (i == 3) {
				m.requestTermination();
			}
		}
	}

	private static class MyPeriodicEvh1 extends PeriodicEventHandler {

		public MyPeriodicEvh1(PriorityParameters priority, PeriodicParameters periodicParameters,
				ScopeParameters storage, String name) {
			super(priority, periodicParameters, storage, configParameters);
		}

		public void handleAsyncEvent() {
			if (Services.getNameOfCurrentMemoryArea().equals("PvtMem")) {
				devices.Console.println("0");
			} else {
				devices.Console.println("	6 " + Services.getNameOfCurrentMemoryArea());
			}
		}
	}

	private static class MyMission0 extends Mission {
		public void initialize() {
			if (Services.getNameOfCurrentMemoryArea().equals("outer-ms")) {
				devices.Console.println("0");
			} else {
				devices.Console.println("	6 " + Services.getNameOfCurrentMemoryArea());
			}
			
			PeriodicEventHandler pevh = new MyPeriodicEvh(new PriorityParameters(20),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, this, "handler 1: ");
			pevh.register();

			PeriodicEventHandler pevh1 = new MyPeriodicEvh1(new PriorityParameters(20),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()),
							new RelativeTime(1, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, "handler 2: ");
			pevh1.register();

		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}
	}

	private static class MyApp implements Safelet {
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
			MyMission0 m;
			int count = 0;

			MySequencer() {
				super(new PriorityParameters(12), storageParameters_Sequencer, configParameters, "outer-ms");
				m = new MyMission0();
			}

			public MyMission0 getNextMission() {
				if (Services.getNameOfCurrentMemoryArea().equals("PvtMem")) {
					devices.Console.println("0");
				} else {
					devices.Console.println("	11 " + Services.getNameOfCurrentMemoryArea());
				}
				
				if (count < 10) {
					count++;
					//
					return m;
				} else {
					return null;
				}
			}
		}
	}

	static ScopeParameters storageParameters_Sequencer;
	static ScopeParameters storageParameters_Handlers;
	static ScopeParameters storageParameters_InnerSequencer;
	static ConfigurationParameters configParameters;

	public static void main(String[] args) {
		storageParameters_Sequencer = new ScopeParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				Const.IMMORTAL_MEM, Const.PRIVATE_MEM,
				Const.MISSION_MEM);

		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_BACKING_STORE,
				0, Const.PRIVATE_MEM, 0);

		storageParameters_InnerSequencer = new ScopeParameters(Const.PRIVATE_BACKING_STORE * 3
				+ Const.MISSION_MEM, 0, Const.PRIVATE_MEM,
				Const.MISSION_MEM_DEFAULT);

		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console.println("\n***** test multicore memory management main.begin ************");
		new LaunchMulticore(new MyApp(), 1);
		devices.Console.println("***** test multicore memory management main.end **************");
		VMTest.markResult(false);
	}

}
