package test;

import javax.realtime.Clock;
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

@SuppressWarnings("rawtypes") 
public class TestSCJCyclicSchedule3 {

    private static class MyCyclicSchedule {
        static CyclicSchedule generate0(CyclicExecutive cyclicExec, PeriodicEventHandler[] handlers) {
            // devices.Console.println("  *** MyCyclicSch: generate begin");
            Frame[] frames = new Frame[2];

            PeriodicEventHandler[] frame0 = new PeriodicEventHandler[2];
            PeriodicEventHandler[] frame1 = new PeriodicEventHandler[1];

            RelativeTime frameDuration = new RelativeTime(5, 0, Clock.getRealtimeClock());

            frame0[0] = handlers[0];
            frame0[1] = handlers[1];

            frame1[0] = handlers[0];

            frames[0] = new Frame(frameDuration, frame0);
            frames[1] = new Frame(frameDuration, frame1);
            // devices.Console.println("  *** MyCyclicSch: generated end");

            return new CyclicSchedule(frames);
        }
    }

    private static class MyPeriodicEvh1 extends PeriodicEventHandler {
        int n;

        protected MyPeriodicEvh1(PriorityParameters priority, PeriodicParameters periodic, 
                StorageParameters storageParameters,
                int n, MissionSequencer missSeq) {
            super(priority, periodic, storageParameters);
            this.n = n;
        }

        public void handleAsyncEvent() {
            for (int i = 0; i < n; i++) {
                new Integer(i);
            }
        }
    }

    private static class MyPeriodicEvh extends PeriodicEventHandler {
        Mission mission;
        int count = 0;

        public MyPeriodicEvh(PriorityParameters priority, PeriodicParameters periodicParameters, 
                StorageParameters storageParameters,                                                    
                Mission mission) {
            super(priority, periodicParameters, storageParameters);
            this.mission = mission;
        }

        public void handleAsyncEvent() {
            count++;
            for (int i = 0; i < 2; i++) {
                new Integer(i);
            }

            if (count == 2)
                mission.requestTermination();
        }
    }

    private static class MyMission0 extends CyclicExecutive {
        MissionSequencer missSeq;

        public MyMission0(MissionSequencer missSeq) {
            this.missSeq = missSeq;
        }

        public void initialize() {
            new MyPeriodicEvh(
            	new PriorityParameters(Priorities.MIN_PRIORITY),
                new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()),
                                       new RelativeTime(5, 0, Clock.getRealtimeClock())), 
                storageParameters_Handlers, this).register();

            new MyPeriodicEvh1(
            	new PriorityParameters(Priorities.MIN_PRIORITY), 
                new PeriodicParameters(new RelativeTime(), // start
                                       new RelativeTime(10, 0)), // period
                storageParameters_Handlers,
                2, // used in handleAsyncEvent
                missSeq).register();

            // devices.Console.println("*** MyMission0: initialize end");
        }

        public long missionMemorySize() {
            return Const.MISSION_MEM;
        }

        public CyclicSchedule getSchedule(PeriodicEventHandler[] handlers) {
            // devices.Console.println("  *** MyMission: getSchedule");
            return MyCyclicSchedule.generate0(this, handlers);
        }
    }

    private static class MyMission1 extends CyclicExecutive {
        MissionSequencer missSeq;

        public MyMission1(MissionSequencer missSeq) {
            this.missSeq = missSeq;
        }

        public void initialize() {
            new MyPeriodicEvh(new PriorityParameters(Priorities.MIN_PRIORITY), 
                    new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), 
                                           new RelativeTime(5, 0, Clock.getRealtimeClock())), 
                    storageParameters_Handlers, this).register();

            new MyPeriodicEvh1(new PriorityParameters(Priorities.MIN_PRIORITY), 
                    new PeriodicParameters(new RelativeTime(), // start
                                           new RelativeTime(10, 0)), // period
                    storageParameters_Handlers,
                    2, // used in handleAsyncEvent
                    missSeq).register();

            // devices.Console.println("*** MyMission1: initialize end");
        }

        public long missionMemorySize() {
            return Const.MISSION_MEM;
        }

        public CyclicSchedule getSchedule(PeriodicEventHandler[] handlers) {
            // devices.Console.println("  *** MyMission: getSchedule");
            return MyCyclicSchedule.generate0(this, handlers);
        }
    }

    private static class MyApp implements Safelet {
        public static int count = 0;

        public MissionSequencer getSequencer() {
            devices.Console.println("** MyApp.getSequencer");
            return new MySequencer();
        }
        
        public long immortalMemorySize()
        {
          return Const.IMMORTAL_MEM;
        }
        
        public void initializeApplication() {
        }

        private static class MySequencer extends MissionSequencer {
            private Mission[] missions;
            private int active = 0;

            private Mission miss;

            MySequencer() {
                super(new PriorityParameters(Priorities.PR95), 
                		storageParameters_Sequencer); 
                                                                                                                                                     // memory
                                                                                                                                                     // size
                missions = new Mission[2];
                missions[0] = new MyMission0(this);
                missions[1] = new MyMission1(this);

                miss = missions[0];
            }

            public Mission getNextMission() {
                MyApp.count++;
                if (MyApp.count % 10 == 0) // or MyApp.count % 10, etc.
                {
                    devices.Console.println("MS " + MyApp.count);
                }
                if (missions[active].terminationPending() && MyApp.count > 150) {
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
		Const.setDefaultErrorReporter();
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

        devices.Console.println("\n****** Cyc 3 main.begin *********");
        new LaunchLevel0(new MyApp());
        devices.Console.println("****** Cyc 3 main.end *********");
        
        args = null;
    }
}
