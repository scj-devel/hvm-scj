package test;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.CyclicExecutive;
import javax.safetycritical.CyclicSchedule;
import javax.safetycritical.Frame;
import javax.safetycritical.LaunchLevel0;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

public class TestSCJLevel0SimpleCyclicExecutive1 extends CyclicExecutive 
	implements Safelet<CyclicExecutive> {

    private static MissionSequencer<CyclicExecutive> sequencer;

    private static class MyPEH extends PeriodicEventHandler {
        static final int priority = 13;

        int eventCounter;
        
        public MyPEH(String nm, RelativeTime start, RelativeTime period) {
            super(new PriorityParameters(priority), 
            	  new PeriodicParameters(start, period), 
            	  storageParameters_Handlers);
        }

        @Override
        public void handleAsyncEvent() {
            devices.Console.println("Running");
            ++eventCounter;
            if (eventCounter == 3) {
            	sequencer.signalTermination();
            }
        }
    }

    private static class VendorCyclicSchedule {

        static CyclicExecutive cache_key;
        static CyclicSchedule cache_schedule;

        static CyclicSchedule generate(PeriodicEventHandler[] peh, CyclicExecutive m) {
            if (m == cache_key) {
                return cache_schedule;
            } else {
                Frame frames[] = new Frame[2];
                PeriodicEventHandler frame1_handlers[] = new PeriodicEventHandler[3];
                PeriodicEventHandler frame2_handlers[] = new PeriodicEventHandler[2];
                RelativeTime frame1_duration = new RelativeTime(500, 0);
                RelativeTime frame2_duration = new RelativeTime(500, 0);

                frame1_handlers[0] = peh[0];
                frame1_handlers[1] = peh[2];
                frame1_handlers[2] = peh[1];

                frame2_handlers[0] = peh[0];
                frame2_handlers[1] = peh[2];

                frames[0] = new Frame(frame1_duration, frame1_handlers);
                frames[1] = new Frame(frame2_duration, frame2_handlers);

                cache_schedule = new CyclicSchedule(frames);
                cache_key = m;
                return cache_schedule;

            }
        }
    }
    
    public void initializeApplication() {
    }

    public long missionMemorySize() {
        return Const.MISSION_MEM;
    }

    public void initialize() {
        (new MyPEH("A", new RelativeTime(0, 0), new RelativeTime(500, 0))).register();
        (new MyPEH("B", new RelativeTime(0, 0), new RelativeTime(1000, 0))).register();
        (new MyPEH("C", new RelativeTime(0, 0), new RelativeTime(500, 0))).register();
    }

    public CyclicSchedule getSchedule(PeriodicEventHandler[] pehs) {
        return VendorCyclicSchedule.generate(pehs, this);
    }

    public MissionSequencer<CyclicExecutive> getSequencer() {
	    sequencer = new MissionSequencer<CyclicExecutive>(
	    				new PriorityParameters(Priorities.SEQUENCER_PRIORITY), 
	    				storageParameters_Sequencer) {

            @Override
            protected CyclicExecutive getNextMission() {
                return new TestSCJLevel0SimpleCyclicExecutive1();
            }
        };
		return sequencer;
	}

    @Override
    public long immortalMemorySize() {
        return Const.IMMORTAL_MEM_DEFAULT;
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
	  
        new LaunchLevel0(new TestSCJLevel0SimpleCyclicExecutive1());
        args = null;
    }
}
