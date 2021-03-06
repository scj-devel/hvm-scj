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

import vm.Address32Bit;
import vm.HardwareObject;
import vm.VMTest;

public class TestSCJSingleSimpleLowMemory {

    private static class Light extends HardwareObject {

        public Light(int address) {
            super(new Address32Bit(address));
        }

        public void on(int colour) // 0 = green; 1 = red
        {
            if (colour == 0) {
                devices.Console.println("g on"); // green on
            } else
                devices.Console.println("r on"); // red on
        }

        public void off(int colour) {
            if (colour == 0) {
                devices.Console.println("g off"); // green off
            } else
                devices.Console.println("r off"); // red off
        }
    }

    private static class MyAperiodicEvh extends AperiodicEventHandler {
        Light light;
        MissionSequencer missSeq;

        public MyAperiodicEvh(
        		PriorityParameters priority, 
        		AperiodicParameters release, 
        		ScopeParameters storageParameters,
                Light light, 
                MissionSequencer missSeq) {
            super(priority, release, storageParameters, configParameters);
            this.light = light;
            this.missSeq = missSeq;
        }

        public void handleAsyncEvent() {
            missSeq.signalTermination();
            devices.Console.println("Ap Term");
            light.on(1);
        }
    }

    private static class MyPeriodicEvh extends PeriodicEventHandler {
        Light light;
        AperiodicEventHandler aevh;

        int count = 0;

        protected MyPeriodicEvh(
        		PriorityParameters priority, 
        		PeriodicParameters periodic, 
        		ScopeParameters storageParameters,
                Light light, 
                AperiodicEventHandler aevh) {
            super(priority, periodic, storageParameters, configParameters);
            this.light = light;
            this.aevh = aevh;
        }

        public void handleAsyncEvent() {
            light.on(0);
            count++;

            devices.Console.println("MyPEvh");

            if (count % 4 == 3)
                aevh.release();

            light.off(0);
        }
    }

    private static class MyMission extends Mission {
        MissionSequencer missSeq;

        public MyMission(MissionSequencer missSeq) {
            this.missSeq = missSeq;
        }

        public void initialize() {
            int address = 123456;
            Light light = new Light(address);

            AperiodicEventHandler aevh = 
            	new MyAperiodicEvh(
            		new PriorityParameters(Priorities.PR98), 
            		new AperiodicParameters(new RelativeTime(500, 0), null), 
            		storageParameters_Handlers, 
            		light,missSeq);
            aevh.register();

            PeriodicEventHandler pevh1 = 
            	new MyPeriodicEvh(
            		new PriorityParameters(Priorities.PR97), 
            		new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), // start
                                           new RelativeTime(1000, 0, Clock.getRealtimeClock())), // period
                    storageParameters_Handlers,
                    light, 
                    aevh); // used in pevh.handleAsyncEvent
            pevh1.register();
        }

        public long missionMemorySize() {
            return Const.MISSION_MEM;
        }
    }

    private static class MyApp implements Safelet {
        public MissionSequencer getSequencer() {
            /* devices.Console.println("MyApp.getSeq"); */
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

        private static class MySequencer extends MissionSequencer {
            private MyMission mission;

            MySequencer() {
                super(new PriorityParameters(Priorities.PR95), 
                		storageParameters_Sequencer, configParameters); 

                mission = new MyMission(this);
            }

            public MyMission getNextMission() {
                if (mission.terminationPending()) {
                    devices.Console.println("MySeq.NextMiss: null");
                    return null;
                } else {
                    devices.Console.println("MySeq.NextMiss");
                    return mission;
                }
            }
        }
    }

    static ScopeParameters storageParameters_Sequencer;
	static ScopeParameters storageParameters_Handlers;
	static ConfigurationParameters configParameters;
	
    public static void main(String[] args) {
//        Const.OUTERMOST_SEQ_BACKING_STORE = 240 * 1000;
//        Const.IMMORTAL_MEM = 50 * 1000;
//        Const.MISSION_MEM = 20 * 1000;
//        Const.PRIVATE_MEM = 10 * 1000;
//        
//        Const.HANDLER_STACK_SIZE = Const.STACK_UNIT;

//	  storageParameters_Sequencer = 
//        new ScopeParameters(
//            Const.OUTERMOST_SEQ_BACKING_STORE,
//            Const.IMMORTAL_MEM, 
//            Const.PRIVATE_MEM, 
//            Const.MISSION_MEM);
//	  
//	  storageParameters_Handlers = 
//        new ScopeParameters(
//        		Const.PRIVATE_MEM, 
//            0, 
//            Const.PRIVATE_MEM, 
//            0);
      
      storageParameters_Sequencer = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO		
	  storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO
	  
	  configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

        devices.Console.println("\n***** TestSCJSimpleLowMemory begin *****");
        new LaunchLevel1(new MyApp());
        devices.Console.println("\n***** TestSCJSimpleLowMemory end *****");

        VMTest.markResult(false);
    }
}
