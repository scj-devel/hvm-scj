/**************************************************************************
 * File name  : TestSCJWaitAndNotify1.java
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
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.LaunchMulticore;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.Services;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

public class TestSCJMPWaitAndNotify {
	static int count = 0;

	private static class SharedResource {
		public synchronized void waitForGo() {
			count++;
			devices.Console.println("> before wait; count = " + count);
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			devices.Console.println("< after wait; count = " + count);
			if (count > 0)
				throw new Error("wait error; count = " + count);
		}

		public synchronized void go() {
			if (count > 0) {
				devices.Console.println("    before notify");
				count--;
				notify();
				devices.Console.println("    after notify");
			}
		}
	}

	private static class MyPEH1 extends PeriodicEventHandler {
		private int count = 0;
		Mission m;

		SharedResource shared;

		@IcecapCompileMe
		public MyPEH1(PriorityParameters priority, PeriodicParameters release, ScopeParameters storage,
				SharedResource shared, Mission m) {
			super(priority, release, storage, configParameters);
			this.m = m;
			this.shared = shared;
		}

		@Override
		@IcecapCompileMe
		public void handleAsyncEvent() {
			devices.Console.println("---------- PEH1.handleAsyncEvent");

			shared.waitForGo();

			count++;
			devices.Console.println("      PEH1: " + count);

			if (count == 3) {
				m.requestTermination();
				devices.Console.println("     Mission T");
			}
		}
	}

	private static class MyPEH2 extends PeriodicEventHandler {
		private int count = 0;

		SharedResource shared;

		public MyPEH2(PriorityParameters priority, PeriodicParameters release, ScopeParameters storage,
				SharedResource shared) {
			super(priority, release, storage, configParameters);
			this.shared = shared;
		}

		@Override
		public void handleAsyncEvent() {
			devices.Console.println("---------- PEH2.handleAsyncEvent");

			shared.go();

			devices.Console.println("     PEH2: " + count);
			count++;
		}
	}

	private static class MyMission extends Mission {

		@Override
		protected void initialize() {

			SharedResource shared = new SharedResource();

			MyPEH1 myPEH1 = new MyPEH1(new PriorityParameters(2), new PeriodicParameters(new RelativeTime(
					Clock.getRealtimeClock()), new RelativeTime(1, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, shared, this);
			
			myPEH1.register();

			MyPEH2 myPEH2 = new MyPEH2(new PriorityParameters(2), new PeriodicParameters(new RelativeTime(0, 0,
					Clock.getRealtimeClock()), new RelativeTime(1, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, shared);
			
			myPEH2.register();

			Services.setCeiling(shared, 2);
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
			if (count == 1000) {
				devices.Console.println("MySeq.count: " + count + "; null");
				return null;
			} else {
				count++;
				//devices.Console.println("MySequencer.getNextMission.count: " + count);
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
	}

	static ScopeParameters storageParameters_Sequencer;
	static ScopeParameters storageParameters_Handlers;
	static ConfigurationParameters configParameters;

	public static void main(String[] args) {
		storageParameters_Sequencer = new ScopeParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				Const.IMMORTAL_MEM, Const.PRIVATE_MEM, Const.MISSION_MEM);

		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_BACKING_STORE,
				0, Const.PRIVATE_MEM, 0);

		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console.println("\n***** test multicore wait and notify main.begin *****");
		new LaunchMulticore(new MyApp(), 2);
		devices.Console.println("***** test multicore wait and notify main.end *****");

		if (count == 0)
			VMTest.markResult(false);
	}
}
