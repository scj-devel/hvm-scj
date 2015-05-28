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

import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.LaunchLevel2;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.Services;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

public class TestSCJWaitAndNotify1 {
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
		public MyPEH1(PriorityParameters priority, PeriodicParameters release, StorageParameters storage,
				SharedResource shared, Mission m) {
			super(priority, release, storage);
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

		public MyPEH2(PriorityParameters priority, PeriodicParameters release, StorageParameters storage,
				SharedResource shared) {
			super(priority, release, storage);
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

			MyPEH1 myPEH1 = new MyPEH1(new PriorityParameters(12), new PeriodicParameters(new RelativeTime(
					Clock.getRealtimeClock()), new RelativeTime(50, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, shared, this);
			myPEH1.register();

			PeriodicEventHandler myPEH2 = new MyPEH2(new PriorityParameters(12),
					new PeriodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()), new RelativeTime(50, 0,
							Clock.getRealtimeClock())), storageParameters_Handlers, shared);
			myPEH2.register();

			Services.setCeiling(shared, 12);
		}

		@Override
		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

	}

	private static class MySequencer extends MissionSequencer<Mission> {
		private Mission mission;
		private int count = 0;

		public MySequencer(PriorityParameters priority, StorageParameters storage) {
			super(priority, storage);
			mission = new MyMission();
		}

		@Override
		protected Mission getNextMission() {
			if (count == 10) {
				devices.Console.println("MySeq.count: " + count + "; null");
				return null;
			} else {
				count++;
				//devices.Console.println("MySequencer.getNextMission.count: " + count);
				return mission;
			}
		}
	}

	private static class MyApp implements Safelet<Mission> {

		@Override
		public MissionSequencer<Mission> getSequencer() {
			return new MySequencer(new PriorityParameters(Priorities.SEQUENCER_PRIORITY), storageParameters_Sequencer);
		}

		@Override
		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}

		@Override
		public void initializeApplication() {
		}
	}

	static StorageParameters storageParameters_Sequencer;
	static StorageParameters storageParameters_Handlers;

	public static void main(String[] args) {
		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, Const.IMMORTAL_MEM, Const.MISSION_MEM);

		storageParameters_Handlers = new StorageParameters(Const.PRIVATE_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, 0, 0);

		devices.Console.println("\n***** TestSCJWaitAndNotify1 main.begin *****");
		new LaunchLevel2(new MyApp());
		devices.Console.println("***** TestSCJWaitAndNotify1 main.end *****");

		if (count == 0)
			args = null;
	}
}
