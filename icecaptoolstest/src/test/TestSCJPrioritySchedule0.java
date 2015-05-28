package test;

import icecaptools.IcecapCompileMe;

import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

@SuppressWarnings("rawtypes")
public class TestSCJPrioritySchedule0 {

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
                StorageParameters storageParameters,
                int n, MissionSequencer missSeq) {
            super(priority, periodic, storageParameters);
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
        
        public void initializeApplication() {
        }

        private class MySequencer extends MissionSequencer {
            private Mission mission;

            MySequencer() {
                super(new PriorityParameters(Priorities.PR95), storageParameters_Sequencer); 
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
	  devices.Console.println("********* TestSCJPrioritySchedule0 begin *****");
    new LaunchLevel1(new MyApp());
    devices.Console.println("********* TestSCJPrioritySchedule0 end *****");
    if (testCount == 3) {
        args = null;
    }
  }

}
