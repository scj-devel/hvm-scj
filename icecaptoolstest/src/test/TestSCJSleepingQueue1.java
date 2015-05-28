/**************************************************************************
 * File name  : TestSCJSleepingQueue1.java
 * 
 * This code is available under the license:
 * Creative Commons, http://creativecommons.org/licenses/by-nc-nd/3.0/
 * It is free for non-commercial use. 
 * 
 * VIA University College, Horsens, Denmark, 2012
 * Hans Soendergaard, hso@viauc.dk
 * 
 * Description: 
 * 
 * Revision history:
 *   date   init  comment
 *
 *************************************************************************/

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

@SuppressWarnings("rawtypes")
public class TestSCJSleepingQueue1 {
    private static class MyAperiodicEvh extends AperiodicEventHandler {
        Mission mission;

        public MyAperiodicEvh(
        		PriorityParameters priority, 
        		AperiodicParameters release, 
        		StorageParameters storageParameters, 
                Mission m) {
            super(priority, release, storageParameters);
            this.mission = m;
        }

        public void handleAsyncEvent() {
            mission.requestTermination();
        }
    }

    private static class MyPeriodicEvh extends PeriodicEventHandler {
        AperiodicEventHandler aevh;
        int count = 0;

        public MyPeriodicEvh(
        		PriorityParameters priority, 
        		PeriodicParameters periodicParameters, 
        		StorageParameters storageParameters, 
                AperiodicEventHandler aevh) {
            super(priority, periodicParameters, storageParameters);
            this.aevh = aevh;
        }

        public void handleAsyncEvent() {
            count++;
            for (int i = 0; i < 2; i++) {
                new Integer(i);
            }

            if (count % 3 == 2)
                aevh.release();
        }
    }

    private static class MyPeriodicEvh1 extends PeriodicEventHandler {
        public MyPeriodicEvh1(
        		PriorityParameters priority, 
        		PeriodicParameters periodicParameters, 
        		StorageParameters storageParameters) 
        {
            super(priority, periodicParameters, storageParameters);
        }

        public void handleAsyncEvent() {
            for (int i = 0; i < 3; i++) {
                new Integer(i);
            }
        }
    }

    private static class MyMission0 extends Mission {
        public void initialize() {
            AperiodicEventHandler aevh = 
            	new MyAperiodicEvh(
            		new PriorityParameters(Priorities.PR97), 
            		new AperiodicParameters(new RelativeTime(50, 0, Clock.getRealtimeClock()), null), 
            		storageParameters_Handlers, 
            		this);
            aevh.register();

            new MyPeriodicEvh(
            	new PriorityParameters(Priorities.PR98), 
            	new PeriodicParameters(new RelativeTime(50, 0, Clock.getRealtimeClock()), // start
                                       new RelativeTime(80, 0, Clock.getRealtimeClock())), // period
                storageParameters_Handlers, aevh).register();
        }

        public long missionMemorySize() {
            return Const.MISSION_MEM;
        }
    }

    private static class MyMission1 extends Mission {
        public void initialize() {
            AperiodicEventHandler aevh = 
            	new MyAperiodicEvh(
            		new PriorityParameters(Priorities.PR96), 
            		new AperiodicParameters(new RelativeTime(100, 0, Clock.getRealtimeClock()), null), 
            		storageParameters_Handlers, 
            		this);
            aevh.register();

            new MyPeriodicEvh(
            		new PriorityParameters(Priorities.PR97), 
            		new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()), // start
                                           new RelativeTime(50, 0, Clock.getRealtimeClock())), // period
                    storageParameters_Handlers, aevh).register();

            new MyPeriodicEvh1(
            		new PriorityParameters(Priorities.PR98), 
            		new PeriodicParameters(new RelativeTime(70, 0, Clock.getRealtimeClock()), // start
                                           new RelativeTime(30, 0, Clock.getRealtimeClock())), // period
                    storageParameters_Handlers).register();
        }

        public long missionMemorySize() {
            return Const.MISSION_MEM;
        }
    }

    private static class MyApp implements Safelet {
        public static int count = 0;

        public MissionSequencer getSequencer() {
            return new MySequencer();
        }

        public long immortalMemorySize() {
            return Const.IMMORTAL_MEM;
        }
        
        public void initializeApplication() {
        }

        private class MySequencer extends MissionSequencer {
            private Mission[] missions;
            private int active = 0;

            private Mission miss;

            MySequencer() {
                super(new PriorityParameters(Priorities.PR95), 
                	  storageParameters_Sequencer); 
                missions = new Mission[2];
                missions[0] = new MyMission0();
                missions[1] = new MyMission1();

                miss = missions[0];
            }

            public Mission getNextMission() {
                MyApp.count++;
                if (MyApp.count % 1 == 0) 
                {
                    devices.Console.println("MS " + MyApp.count);
                }
                if (missions[active].terminationPending() && MyApp.count > 4) {
                    devices.Console.println("MySeq.getNextMission: null");
                    return null;
                } else {
                    active = (active + 1) % missions.length;
                    miss = missions[active];
                    return miss;
                }
            }
        }
    }

    public static StorageParameters storageParameters_Sequencer;
	public static StorageParameters storageParameters_Handlers;
  
	public static void main(String[] args) {
	  storageParameters_Sequencer = 
        new StorageParameters(
            Const.OUTERMOST_SEQ_BACKING_STORE,
            new long[] { Const.HANDLER_STACK_SIZE },
            Const.PRIVATE_MEM, 
            Const.IMMORTAL_MEM, 
            Const.MISSION_MEM);
	  
	  storageParameters_Handlers = 
        new StorageParameters(
            Const.PRIVATE_BACKING_STORE, 
            new long[] { Const.HANDLER_STACK_SIZE },
            Const.PRIVATE_MEM, 
            0, 
            0);
      
        devices.Console.println("\n***** Sleeping queue begin ************");
        // executes in heap memory
        new LaunchLevel1(new MyApp());
        devices.Console.println("***** Sleeping queue end **************");
        args = null;
    }
}
