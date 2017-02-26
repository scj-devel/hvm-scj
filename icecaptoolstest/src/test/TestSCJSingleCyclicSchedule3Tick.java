package test;

import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.CyclicExecutive;
import javax.safetycritical.CyclicSchedule;
import javax.safetycritical.Frame;
import javax.safetycritical.LaunchLevel0;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.VMTest;

public class TestSCJSingleCyclicSchedule3Tick 
{
    private static class MyCyclicSchedule 
    {
        static CyclicSchedule generate0(CyclicExecutive cyclicExec, PeriodicEventHandler[] handlers) {
            Frame[] frames = new Frame[1];

            PeriodicEventHandler[] frame0handlers = new PeriodicEventHandler[1];

            frame0handlers[0] = handlers[0];

            RelativeTime frameDuration = new RelativeTime(500, 0, Clock.getRealtimeClock());

            frames[0] = new Frame(frameDuration, frame0handlers);

            return new CyclicSchedule(frames);
        }

    }

    private static class MyPeriodicEvh extends PeriodicEventHandler 
    {
        Mission mission;
        int count = 0;

        public MyPeriodicEvh(PriorityParameters priority, 
        		PeriodicParameters periodicParameters, 
        		StorageParameters storageParameters,
                Mission mission) {
            super(priority, periodicParameters, storageParameters,configParameters);
            this.mission = mission;
        }

        public void handleAsyncEvent() {
            count++;
            devices.Console.println("tick!");
            if (count == 5) {
                mission.requestTermination();
            }
        }
    }

    private static class MyMission0 extends CyclicExecutive {
        public void initialize() {
            new MyPeriodicEvh(
            	new PriorityParameters(Priorities.MIN_PRIORITY), 
            	new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()), 
            			               new RelativeTime(500, 0, Clock.getRealtimeClock())), 
            	storageParameters_Handlers, 
            	this).register();
        }

        public long missionMemorySize() {
            return Const.MISSION_MEM;
        }

        public CyclicSchedule getSchedule(PeriodicEventHandler[] handlers) {
            return MyCyclicSchedule.generate0(this, handlers);
        }
    }

    private static class MyApp implements Safelet {

        public MissionSequencer getSequencer() {
            devices.Console.println("** MyApp.getSequencer");
            return new MySequencer();
        }

        public long immortalMemorySize() {
            return Const.IMMORTAL_MEM;
        }
        
        public void initializeApplication() {
        }

        private static class MySequencer extends MissionSequencer {
            private CyclicExecutive[] missions;
            private boolean started;

            MySequencer() {
                super(new PriorityParameters(Priorities.PR95), 
                		storageParameters_Sequencer, configParameters); 
                                                                                                                                                              // memory
                                                                                                                                                              // size
                missions = new CyclicExecutive[1];
                missions[0] = new MyMission0();
                started = false;
            }

            public CyclicExecutive getNextMission() {
                if (!started) {
                    started = true;
                    return missions[0];
                } else {
                    return null;
                }
            }
        }
    }

    public static StorageParameters storageParameters_Sequencer;
    public static StorageParameters storageParameters_Handlers;
    public static ConfigurationParameters configParameters;

    public static void main(String[] args) {
        Const.OUTERMOST_SEQ_BACKING_STORE = 32 * 1000 + 10 * 1000;
        Const.IMMORTAL_MEM = 23 * 1000 + 3 * 1000;
        Const.MISSION_MEM  =  3 * 1000 + 3 * 1000;
        Const.PRIVATE_MEM  =  1 * 1000 + 2 * 1000;
        Const.HANDLER_STACK_SIZE = Const.STACK_UNIT;
        
  	  storageParameters_Sequencer = 
          new StorageParameters(
              Const.OUTERMOST_SEQ_BACKING_STORE,
              Const.PRIVATE_MEM, 
              Const.IMMORTAL_MEM, 
              Const.MISSION_MEM);
  	  
  	  storageParameters_Handlers = 
          new StorageParameters(
          	Const.PRIVATE_MEM, 
              Const.PRIVATE_MEM, 
              0, 
              0);
  	  
  	  configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });
          
        devices.Console.println("\n****** TestSCJCyclicSchedule3Tick begin *********" );        
        new LaunchLevel0(new MyApp());
        devices.Console.println("****** TestSCJCyclicSchedule3Tick end *********");

        VMTest.markResult(false);
    }
}
