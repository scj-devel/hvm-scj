package test;

/**************************************************************************
 * File name  : TestSCJSharedResource1.java
 * 
 * This code is available under the license:
 * Creative Commons, http://creativecommons.org/licenses/by-nc-nd/3.0/
 * It is free for non-commercial use. 
 * 
 * VIA University College, Horsens, Denmark, 2014
 * Hans Soendergaard, hso@viauc.dk
 * 
 * Description: 
 *************************************************************************/

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
import javax.scj.util.Priorities;

import vm.VMTest;

public class TestSCJMPSharedResource1 {
    static boolean failed;

    private static class MyPeriodicEvh extends PeriodicEventHandler {
        int n;
        Mission mission;
        MissionSequencer missSeq;

        Resource res;

        int count = 0;

        protected MyPeriodicEvh(PriorityParameters priority, 
        		PeriodicParameters periodic, 
        		ScopeParameters storageParameters, 
                int n, 
                Resource res, Mission mission, MissionSequencer missSeq) {
            super(priority, periodic, storageParameters, configParameters);
            this.n = n;
            this.res = res;
            this.mission = mission;
            this.missSeq = missSeq;
        }

        public void handleAsyncEvent() {
            devices.Console.println("MyPEvh " + n + "; count " + count);
            res.inc();
            res.dec();

            int value = res.get();

            // Invariant: 0 <= value < numberOfPeriodicEventHandlers
            if (!invariant(value)){
            	 devices.Console.println("--> MyAPEvh: inv broken: " + res.get());
                 failed = true;
                 missSeq.signalTermination();
            }
                
            count++;
            if (n == 2 && count == 5) // 1000
            {
                devices.Console.println("MyPEvh " + n + "; count " + count + "; aevh2.register");
                failed = false;
                mission.requestTermination();
            }
        }

        private boolean invariant(int inv) {
            return (0 <= inv) && (inv < MyMission.NumberOfPeriodicEvhs);
        }
    }

    private static class Resource {
        private int value = 0;

        private final int n = 2;

        public synchronized void inc() {
            int local = value;
            // To consume some time:
            for (int i = 0; i < 2 * n; i++) {
                local = ~local; // bitwise complement
            }
            // increment
            value = local + 1;
        }

        public synchronized void dec() {
            int local = value;
            for (int i = 0; i < 2 * n; i++) {
                local = ~local; // bitwise complement
            }

            value = local - 1;
        }

        public synchronized int get() {
            return value;
        }
    }

    private static class MyMission extends Mission {
        static final int NumberOfPeriodicEvhs = 2;

        MissionSequencer missSeq;

        public MyMission(MissionSequencer missSeq) {
            this.missSeq = missSeq;
        }

        public void initialize() {
            Resource res = new Resource();

            MyPeriodicEvh handler1 =  new MyPeriodicEvh(new PriorityParameters(Priorities.PR96), 
            		new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), // start
                                           new RelativeTime(1000, 0, Clock.getRealtimeClock())), // period
                    storageParameters_Handlers,
                    2, res, this, missSeq);
           
            handler1.register();

            MyPeriodicEvh handler2 = new MyPeriodicEvh(new PriorityParameters(Priorities.PR96), 
            		new PeriodicParameters(new RelativeTime(200, 0, Clock.getRealtimeClock()), // start
                                           new RelativeTime(1000, 0, Clock.getRealtimeClock())), // period
                    storageParameters_Handlers,
                    3, res, this, missSeq);
            
            handler2.register();

            devices.Console.println("MyMission: Services.setCeiling");
            int ceiling = Priorities.PR96;
            Services.setCeiling(res, ceiling);
        }

        public long missionMemorySize() {
            return Const.MISSION_MEM;
        }
    }

    private static class MyApp implements Safelet {
        public MissionSequencer getSequencer() {
            devices.Console.println("MyApp.getSequencer");
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
            private MyMission mission;
            private int howManyTimes;

            MySequencer() {
                super(new PriorityParameters(Priorities.PR95), 
                		storageParameters_Sequencer, configParameters);                                                                                                         // size

                this.mission = new MyMission(this);
                this.howManyTimes = 0;
            }

            public MyMission getNextMission() {
                howManyTimes++;
                if (howManyTimes > 3 || mission.terminationPending()) {
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
	  storageParameters_Sequencer = 
        new ScopeParameters(
            Const.OUTERMOST_SEQ_BACKING_STORE,
            Const.IMMORTAL_MEM, 
            Const.PRIVATE_MEM, 
            Const.MISSION_MEM);
	  
	  storageParameters_Handlers = 
        new ScopeParameters(
            Const.PRIVATE_BACKING_STORE, 
            0, 
            Const.PRIVATE_MEM, 
            0);
	  
	  configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

        failed = true;
        
        devices.Console.println("\n********** test multicore shared resource main.begin ***********");
        // executes in heap memory
        new LaunchMulticore(new MyApp(), 1);
        devices.Console.println("********* test multicore shared resource main.end ***********");

        VMTest.markResult(failed);
    }

}
