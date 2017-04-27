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

public class TestSCJSingleCyclicSchedule4 {

    public static class MyCyclicSchedule {
    	
        static CyclicSchedule generate(PeriodicEventHandler[] handlers) {
            Frame[] frames = new Frame[2];
            PeriodicEventHandler[] frame0 = new PeriodicEventHandler[2];
            PeriodicEventHandler[] frame1 = new PeriodicEventHandler[1];

            RelativeTime frameDuration = new RelativeTime(200, 0, Clock.getRealtimeClock());

            frame0[0] = handlers[0];
            frame0[1] = handlers[1];

            frame1[0] = handlers[0];

            frames[0] = new Frame(frameDuration, frame0);
            frames[1] = new Frame(frameDuration, frame1);

            return new CyclicSchedule(frames);
        }
    }

    public static class MyPeriodicEvh extends PeriodicEventHandler {
        int n;
        int count = 0;

        protected MyPeriodicEvh(PriorityParameters priority, 
                PeriodicParameters periodic, 
                StorageParameters storageParameters,                
                int n) {
            super(priority, periodic, storageParameters, configParameters);
            this.n = n;
        }

        public void handleAsyncEvent() {
            count++;
            devices.Console.println("  *** MyPEvh:" + n + "; " + count);

            if (count % 7 == 5 && n == 4)
            	Mission.getMission().requestTermination();
        }
    }

    public static class MyMission extends CyclicExecutive {

        public void initialize() {
            PeriodicEventHandler pevh1 = new MyPeriodicEvh(new PriorityParameters(Priorities.MIN_PRIORITY), new PeriodicParameters(new RelativeTime(), // start
                    new RelativeTime(200, 0)), // period
                    storageParameters_Handlers,
                    2); // used in handleAsyncEvent
                   
            pevh1.register();

            PeriodicEventHandler pevh2 = new MyPeriodicEvh(new PriorityParameters(Priorities.MIN_PRIORITY), new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), // start
                    new RelativeTime(400, 0, Clock.getRealtimeClock())), // period
                    storageParameters_Handlers,
                    4); // used in handleAsyncEvent
            pevh2.register();
        }

        public long missionMemorySize() {
            return Const.MISSION_MEM;
        }

        public CyclicSchedule getSchedule(PeriodicEventHandler[] handlers) {
            devices.Console.println("  *** MyMission: getSchedule");
            return MyCyclicSchedule.generate(handlers);
        }
    }

    public static class MySequencer extends MissionSequencer {
        private MyMission mission;

        public MySequencer() {
            super(new PriorityParameters(Priorities.PR95), // lowest priority
            	  storageParameters_Sequencer, configParameters);                                                                            // size

            mission = new MyMission();
        }

        public MyMission getNextMission() {
            if (mission.terminationPending()) {
                devices.Console.println("\n** MySequencer.getNextMission: null");
                return null;
            } else {
                devices.Console.println("\n** MySequencer.getNextMission");
                return mission;
            }
        }
    }

    public static class MyApp implements Safelet {
        public MissionSequencer getSequencer() {
            devices.Console.println("** MyApp.getSequencer");
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
    }

    public static StorageParameters storageParameters_Sequencer;
	public static StorageParameters storageParameters_Handlers;
	public static ConfigurationParameters configParameters;
	
	public static void main(String[] args) {
	  storageParameters_Sequencer = 
        new StorageParameters(
            Const.OUTERMOST_SEQ_BACKING_STORE,
            Const.PRIVATE_MEM, 
            Const.IMMORTAL_MEM, 
            Const.MISSION_MEM);
	  
	  storageParameters_Handlers = 
        new StorageParameters(
            Const.PRIVATE_BACKING_STORE,
            Const.PRIVATE_MEM, 
            0, 
            0);
	  
	  configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

        devices.Console.println("\n****** CyclicSchedule4 main.begin *********");
        new LaunchLevel0(new MyApp());
        devices.Console.println("****** CyclicSchedule4 main.end *********");
        VMTest.markResult(false);
    }

}
