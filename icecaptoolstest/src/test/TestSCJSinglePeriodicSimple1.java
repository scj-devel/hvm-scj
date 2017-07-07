package test;

import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.VMTest;

public class TestSCJSinglePeriodicSimple1 {
	
	private static int testCount;
	
	static {
		testCount = 0;
	}

	private static class MyPeriodicEvh extends PeriodicEventHandler {

        public MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodicParameters, 
                ScopeParameters storageParameters) {
            super(priority, periodicParameters, storageParameters, configParameters);
        }

        public void handleAsyncEvent() {
        	try {
	        	System.out.println("***** Periodic event handler: handleAsyncEvent");
				testCount++;
				throw new Exception ("From handleAsyncEvent");
        	}
        	catch (Throwable e) {
        		System.out.println ("handleAsyncEvent.catch: " + e);
        	}
        	if (testCount > 3)
        		Mission.getMission().requestTermination();
        }        
    }

	private static class MyMission extends Mission {
		
		public void initialize() {
			PeriodicEventHandler pevh = new MyPeriodicEvh(
					new PriorityParameters(Priorities.PR98),
					new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()),
                            new RelativeTime(400, 0, Clock.getRealtimeClock())), 
					storageParameters_Handlers);
			pevh.register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}
	}

	private static class MyApp implements Safelet {
		
		public MissionSequencer getSequencer() {
			return new MySequencer();
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
			private MyMission mission;

			MySequencer() {
				super(new PriorityParameters(Priorities.PR95),
						storageParameters_Sequencer, configParameters);
				mission = new MyMission();
			}

			public MyMission getNextMission() {
				if (mission.terminationPending()) {
					return null;
				} else {
					return mission;
				}
			}
		}
	}

	static ScopeParameters storageParameters_Sequencer;
	static ScopeParameters storageParameters_Handlers;
	static ConfigurationParameters configParameters;

	public static void main(String[] args) {
//		storageParameters_Sequencer = new ScopeParameters(
//				Const.OUTERMOST_SEQ_BACKING_STORE, Const.IMMORTAL_MEM,
//				Const.PRIVATE_MEM, Const.MISSION_MEM);
//		storageParameters_Handlers = new ScopeParameters(
//				Const.PRIVATE_BACKING_STORE, 0, Const.PRIVATE_MEM,
//				0);
		
		storageParameters_Sequencer = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO		
		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO
		
		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console.println("***** TestSCJSinglePeriodicSimple1 begin *****");
		new LaunchLevel1(new MyApp());
		devices.Console.println("***** TestSCJSinglePeriodicSimple1 end *****");
		if (testCount > 0) {
			VMTest.markResult(false);
		}
	}
}