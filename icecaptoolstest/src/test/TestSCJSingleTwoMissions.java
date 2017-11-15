package test;

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.VMTest;

@SuppressWarnings("rawtypes")
public class TestSCJSingleTwoMissions {

    private static class MyPeriodicEvh extends PeriodicEventHandler {
        int n;
        AperiodicEventHandler aevh;
        int count = 0;

        public MyPeriodicEvh(PriorityParameters priority, 
        		             PeriodicParameters periodicParameters, 
        		             ScopeParameters storage,                                                     
        		             int n, AperiodicEventHandler aevh) {
            super(priority, periodicParameters, storage, configParameters);
            this.n = n;
            this.aevh = aevh;
        }

        public void handleAsyncEvent() {
            count++;
            for (int i = 0; i < n; i++) {
                new Integer(count);
            }

            if (count % 3 == 2)
                aevh.release();

            // devices.Console.println("   ** PeriodicEvH.handleAsyncEvent: Pevh no: "
            // + handlerNo + "; count: " + count);
        }
    }

    private static class MyAperiodicEvh extends AperiodicEventHandler {
        int n;
        int count = 0;
        Mission mission;

        public MyAperiodicEvh(PriorityParameters priority, 
        		 			  AperiodicParameters release, 
        		 			  ScopeParameters storage,                                                                                                       
        		 			  int n, Mission m) {
            super(priority, release, storage, configParameters);
            this.n = n;
            this.mission = m;
        }

        public void handleAsyncEvent() {
            count++;
            for (int i = 0; i < n; i++) {
                new Integer(count);
            }
            // devices.Console.println("   ** Apevh.handleAsyncEvent no: " + n +
            // "; count: " + count);
            mission.requestTermination();
        }
    }

    private static class MyMission1 extends Mission {
        public void initialize() {

            AperiodicEventHandler aevh = 
            	new MyAperiodicEvh(
            		new PriorityParameters(Priorities.PR97), 
            		new AperiodicParameters(new RelativeTime(300, 0, Clock.getRealtimeClock()), null), 
            		storageParameters_Handlers, 
            		3, this);
            aevh.register();

            PeriodicEventHandler pevh = 
            	new MyPeriodicEvh(
            		new PriorityParameters(Priorities.PR98), 
            		new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), 
            				               new RelativeTime(200, 0, Clock.getRealtimeClock())), 
            		storageParameters_Handlers, 
            		4, aevh);
            pevh.register();
        }

        public long missionMemorySize() {
            return Const.MISSION_MEM;
        }
    }

    private static class MyMission0 extends Mission {
        public void initialize() {

            AperiodicEventHandler aevh = 
            	new MyAperiodicEvh(
            		new PriorityParameters(Priorities.PR97), 
            		new AperiodicParameters(new RelativeTime(300, 0, Clock.getRealtimeClock()), null), 
            		storageParameters_Handlers, 1, this);
            aevh.register();

            PeriodicEventHandler pevh = 
            	new MyPeriodicEvh(
            		new PriorityParameters(Priorities.PR98), 
            		new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), 
            				               new RelativeTime(200, 0, Clock.getRealtimeClock())), 
            		storageParameters_Handlers, 2, aevh);
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
            private Mission[] mission;

            private int active = 0;

            private byte missionCount;

            MySequencer() {
                super(new PriorityParameters(Priorities.PR95), 
                		storageParameters_Sequencer, configParameters); 
                
                mission = new Mission[2];
                mission[0] = new MyMission0();
                mission[1] = new MyMission1();
                missionCount = 0;
            }

            public Mission getNextMission() {
                if (missionCount < 4) {
                    devices.Console.println("\n MyMS " + active);
                    Mission miss = mission[active];
                    active = (active + 1) % mission.length;
                    missionCount++;
                    return miss;
                } else {
                    return null;
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
 
      devices.Console.println("\n***** Two Missions main.begin ************");
      new LaunchLevel1(new MyApp());
      devices.Console.println("***** Two Missions main.end **************");
      
      VMTest.markResult(false);
    }
}
