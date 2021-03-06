package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

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

@SuppressWarnings("rawtypes")
public class TestSCJSinglePrioritySchedule0 {

    private static int testCount;

    static {
        testCount = 0;
    }

    private static class MyPeriodicEvh extends PeriodicEventHandler {
        int n;
        int count = 0;

        MissionSequencer missSeq;

        protected MyPeriodicEvh(PriorityParameters priority, 
                PeriodicParameters periodic, 
                ScopeParameters storageParameters,
                int n, MissionSequencer missSeq) {
            super(priority, periodic, storageParameters, configParameters);
            this.n = n;
            this.missSeq = missSeq;
        }

        @IcecapCompileMe
        public void handleAsyncEvent() {
            for (int i = 0; i < n; i++)
                new Integer(count++);

            if (n == 1) {
                testCount++;
                if (count % 5 == 3)
                    missSeq.signalTermination();
            }
        }
    }

    private static class MyMission extends Mission {
        MissionSequencer missSeq;

        public MyMission(MissionSequencer missSeq) {
            this.missSeq = missSeq;
        }

        public void initialize() {
            PeriodicEventHandler pevh1 = 
              new MyPeriodicEvh(new PriorityParameters(Priorities.PR98),                       
                    new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), // start
                    new RelativeTime(500, 0, Clock.getRealtimeClock())), // period
                    storageParameters_Handlers,
                    1, // a number
                    missSeq);
            pevh1.register();

            PeriodicEventHandler pevh2 = 
              new MyPeriodicEvh(new PriorityParameters(Priorities.PR97), 
                    new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), // start
                    new RelativeTime(600, 0, Clock.getRealtimeClock())), // period
                    storageParameters_Handlers,
                    2, // a number
                    null);
            pevh2.register();

            PeriodicEventHandler pevh3 = 
              new MyPeriodicEvh(new PriorityParameters(Priorities.PR96), 
                    new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), // start
                    new RelativeTime(700, 0, Clock.getRealtimeClock())), // period
                    storageParameters_Handlers,
                    3, // a number
                    null);
            pevh3.register();

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
            private Mission mission;

            MySequencer() {
                super(new PriorityParameters(Priorities.PR95), 
                		storageParameters_Sequencer, configParameters); 
                mission = new MyMission(this);
            }

            public Mission getNextMission() {
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
//	  storageParameters_Sequencer = 
//        new ScopeParameters(
//            Const.OUTERMOST_SEQ_BACKING_STORE,
//            Const.IMMORTAL_MEM, 
//            Const.PRIVATE_MEM, 
//            Const.MISSION_MEM);
//	  
//	  storageParameters_Handlers = 
//        new ScopeParameters(
//            Const.PRIVATE_BACKING_STORE, 
//            0, 
//            Const.PRIVATE_MEM, 
//            0);
	  
	  storageParameters_Sequencer = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO		
	  storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO
		
	  configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

	devices.Console.println("********* TestSCJPrioritySchedule0 begin *****");
    new LaunchLevel1(new MyApp());
    devices.Console.println("********* TestSCJPrioritySchedule0 end *****");
    if (testCount == 3) {
    	VMTest.markResult(false);
    }
  }

}
