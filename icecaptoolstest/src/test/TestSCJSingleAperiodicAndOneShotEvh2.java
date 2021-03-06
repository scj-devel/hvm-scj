package test;

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.HighResolutionTime;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.OneShotEventHandler;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.VMTest;

/**
 * Test with three aperiodic event handlers registered in the mission.
 * OneShot handler releases aperiodic handler 1
 * Aperiodic handler 1 releases aperiodic handler 2
 * Aperiodic handler 2 requests for mission termination.
 * 
 * This test has also two running periodic handlers.
 *  
 * @author hso
 *
 */
public class TestSCJSingleAperiodicAndOneShotEvh2 {
	
	private static int testCount;
	static {
		testCount = 0;
	}

	private static class MyAperiodicEvh extends AperiodicEventHandler {

		int n;
		AperiodicEventHandler apevh;
		
		public MyAperiodicEvh(PriorityParameters priority,
				AperiodicParameters release,
				ScopeParameters storageParameters,
				int n,
				AperiodicEventHandler apevh) {
			super(priority, release, storageParameters, configParameters);
			this.n = n;
			this.apevh = apevh;
		}

		public void handleAsyncEvent() {			
			System.out.println("***** Aperiodic event handler " + n + " : handleAsyncEvent");
			testCount++;
			if (n == 1 || n == 2)
				apevh.release();
			
			if (n == 3)
			  Mission.getMission().requestTermination();
		}
	}
	
	private static class OneShotEvhStub extends OneShotEventHandler {
		
		AperiodicEventHandler apevh;
		
		public OneShotEvhStub(PriorityParameters priority, HighResolutionTime<?> releaseTime, AperiodicParameters release,
				ScopeParameters storage, AperiodicEventHandler apevh) {
			super(priority, releaseTime, release, storage, new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE }));
			this.apevh = apevh;			
		}

		public void handleAsyncEvent() {
			devices.Console.println("OneShotEvhStub.handleAsyncEvent \n");
			testCount++;
			apevh.release();
		}
	}
	
	private static class MyPeriodicEvh extends PeriodicEventHandler {
		int n;

		protected MyPeriodicEvh(PriorityParameters priority,
				PeriodicParameters periodic,
				ScopeParameters storageParameters, int n) {
			super(priority, periodic, storageParameters, configParameters);
			this.n = n;
		}

		public void handleAsyncEvent() {
			testCount++;
			devices.Console.println("----------Running periodic handler: "+n);
		}
	}


	private static class MyMission extends Mission {
		
		public void initialize() {
			AperiodicEventHandler aevh3 = new MyAperiodicEvh(
					new PriorityParameters(Priorities.PR98),
					new AperiodicParameters(),
					storageParameters_Handlers,
					3, null);
			aevh3.register();
			
			AperiodicEventHandler aevh2 = new MyAperiodicEvh(
					new PriorityParameters(Priorities.PR98),
					new AperiodicParameters(),
					storageParameters_Handlers,
					2,
					aevh3);
			aevh2.register();
			
			AperiodicEventHandler aevh1 = new MyAperiodicEvh(
					new PriorityParameters(Priorities.PR98),
					new AperiodicParameters(),
					storageParameters_Handlers,
					1,
					aevh2);
			aevh1.register();
			
			
			new OneShotEvhStub(
					new PriorityParameters(Priorities.MAX_PRIORITY), 
					new RelativeTime(500, 0),  // release time = 1000 msecs
					new AperiodicParameters(new RelativeTime(500, 0), null), 
					storageParameters_Handlers,
					aevh1).register();
			
			new MyPeriodicEvh(
					new PriorityParameters(Priorities.PR97),
					new PeriodicParameters(new RelativeTime(Clock
							.getRealtimeClock()), // start
							new RelativeTime(200, 0, Clock.getRealtimeClock())), // period
					storageParameters_Handlers, 
					4).register();
			
			new MyPeriodicEvh(
					new PriorityParameters(Priorities.PR96),
					new PeriodicParameters(new RelativeTime(Clock
							.getRealtimeClock()), // start
							new RelativeTime(300, 0, Clock.getRealtimeClock())), // period
					storageParameters_Handlers, 
					5).register();
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
			private MyMission mission;

			MySequencer() {
				super(new PriorityParameters(Priorities.PR95),
						storageParameters_Sequencer, configParameters);
				mission = new MyMission();
			}

			public MyMission getNextMission() {
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

	/**
	 * Compiling for the PC:
	 *
	 * gcc -Wall -pedantic -g -Os -DPC64 -DPRINTFSUPPORT -DJAVA_STACK_SIZE=768
	 * -DJAVA_HEAP_SIZE=15360 classes.c icecapvm.c methodinterpreter.c methods.c
	 * gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c
	 * rom_access.c x86_64_interrupt.s -lpthread -lrt
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		
		storageParameters_Sequencer = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO		
		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO		
		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console.println("***** TestSCJSingleAperiodicAndOneShotEvh2 begin *****");
		new LaunchLevel1(new MyApp());
		devices.Console.println("***** TestSCJSingleAperiodicAndOneShotEvh2 end *****");
		if (testCount == 11) {
			VMTest.markResult(false);
		}
	}
}