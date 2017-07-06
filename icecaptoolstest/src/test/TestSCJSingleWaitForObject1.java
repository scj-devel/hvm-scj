/**************************************************************************
 * File name  : TestSCJSingleWaitForObject1.java
 * 
 * This code is available under the license:
 * Creative Commons, http://creativecommons.org/licenses/by-nc-nd/3.0/
 * It is free for non-commercial use. 
 * 
 * VIA University College, Horsens, Denmark, 2014
 * Hans Soendergaard, hso@viauc.dk
 * 
 * Description: 
 * 
 * Revision history:
 *   date   init  comment
 *
 *************************************************************************/

package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.HighResolutionTime;
import javax.realtime.RelativeTime;
import javax.safetycritical.LaunchLevel2;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.ScopeParameters;
import javax.safetycritical.Services;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

public class TestSCJSingleWaitForObject1 {

	private static class SharedResource {		
		
		private final int MAX = 2;
		private int count = 1;
		
		public synchronized void inc() {
			
			devices.Console.println("> before wait; count = " + count); // + "; this: " + this);
			
			//while (count >= MAX) 
			//try {
				//HighResolutionTime.waitForObject(this, new RelativeTime (1000, 0));
			
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			
			devices.Console.println("< after wait; count = " + count + "\n");
			
			count++;
//			if (count > MAX)
//				count = 0;
		}

		public synchronized void dec()  {
			
			devices.Console.println("    before notify; count = " + count);
			count--;
			notify();
			devices.Console.println("    after notify; count = " + count);
		}
	}

	private static class MyPEH1 extends PeriodicEventHandler {
		private int count = 0;
		Mission m;

		TestSCJSingleWaitForObject1.SharedResource shared;

		@IcecapCompileMe
		public MyPEH1(PriorityParameters priority, PeriodicParameters release, ScopeParameters storage,
				TestSCJSingleWaitForObject1.SharedResource shared, Mission m) {
			super(priority, release, storage, configParameters);
			this.m = m;
			this.shared = shared;
		}

		@Override
		@IcecapCompileMe
		public void handleAsyncEvent() {
			
			shared.inc();

			count++;

			if (count == 3) {
				m.requestTermination();
				devices.Console.println(" ==>  Mission requestTermination");
			}
		}
	}

	private static class MyPEH2 extends PeriodicEventHandler {
		//private int count = 0;

		TestSCJSingleWaitForObject1.SharedResource shared;

		public MyPEH2(PriorityParameters priority, PeriodicParameters release, ScopeParameters storage,
				TestSCJSingleWaitForObject1.SharedResource shared) {
			super(priority, release, storage, configParameters);
			this.shared = shared;
		}

		@Override
		public void handleAsyncEvent() {
			//devices.Console.println("---------- PEH2.handleAsyncEvent");

			shared.dec();

			//devices.Console.println("     PEH2: " + count);
			//count++;
		}
	}

	private static class MyMission extends Mission {

		@Override
		protected void initialize() {

			TestSCJSingleWaitForObject1.SharedResource shared = new TestSCJSingleWaitForObject1.SharedResource();

			MyPEH1 myPEH1 = new MyPEH1(
					new PriorityParameters(12), 
					new PeriodicParameters(
							new RelativeTime(0, 0, Clock.getRealtimeClock()), 
							new RelativeTime(1000, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, shared, this);
			myPEH1.register();

			PeriodicEventHandler myPEH2 = new MyPEH2(
					new PriorityParameters(12),
					new PeriodicParameters(
							new RelativeTime(0, 0, Clock.getRealtimeClock()), 
							new RelativeTime(2000, 0, Clock.getRealtimeClock())), 
					storageParameters_Handlers, shared);
			myPEH2.register();

			Services.setCeiling(shared, 12);
		}

		@Override
		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

	}

	private static class MySequencer extends MissionSequencer {
		private MyMission mission;
		private int count = 0;

		public MySequencer(PriorityParameters priority, ScopeParameters storage) {
			super(priority, storage, configParameters);
			mission = new MyMission();
		}

		@Override
		protected MyMission getNextMission() {
			if (count == 1) {
				devices.Console.println("MySequencer.getNextMission: null");
				return null;
			} else {
				count++;
				devices.Console.println("MySequencer.getNextMission: " + mission);
				return mission;
			}
		}
	}

	private static class MyApp implements Safelet {

		@Override
		public MissionSequencer getSequencer() {
			return new MySequencer(new PriorityParameters(Priorities.SEQUENCER_PRIORITY), storageParameters_Sequencer);
		}

		@Override
		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}

		@Override
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

	static ScopeParameters storageParameters_Sequencer;
	static ScopeParameters storageParameters_Handlers;
	static ConfigurationParameters configParameters;

	public static void main(String[] args) {
//		storageParameters_Sequencer = new ScopeParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
//				Const.IMMORTAL_MEM, Const.PRIVATE_MEM, Const.MISSION_MEM);
//
//		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_BACKING_STORE,
//				0, Const.PRIVATE_MEM, 0);
		
		storageParameters_Sequencer = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO		
		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO
		
		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console.println("\n***** TestSCJWaitForObject1 main.begin *****");
		new LaunchLevel2(new MyApp());
		devices.Console.println("***** TestSCJWaitForObject1 main.end *****");

		VMTest.markResult(false);
	}
}
