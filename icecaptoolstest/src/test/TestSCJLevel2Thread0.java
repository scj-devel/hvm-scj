/**************************************************************************
 * File name  : TestSCJLevel2Thread0.java
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

import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.LaunchLevel2;
import javax.safetycritical.ManagedThread;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

public class TestSCJLevel2Thread0 {
	private static boolean testResult;

	static {
		testResult = false;
	}

	private static class MyThread extends ManagedThread {
		public MyThread(PriorityParameters priority, StorageParameters storage) {
			super(priority, storage);
		}

		public void run() {
			doWork();
		}

		void doWork() {
			devices.Console.println("MyThread: running.");
			devices.Console.println("MyThread: sleep 2 secs");
			try {
				ManagedThread.sleep(new RelativeTime(2000, 0));
			} catch (InterruptedException e) {
			}
			devices.Console.println("MyThread: continue after 2 secs");

			Mission.getMission().requestTermination();
		}
	}

	private static class MyMission extends Mission {

		public void initialize() {
			MyThread th = new MyThread(new PriorityParameters(Priorities.PR97), storageParameters_Handlers);
			th.register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}
	}

	private static class MySequencer extends MissionSequencer<MyMission> {

		private MyMission mission;

		MySequencer() {
			super(new PriorityParameters(Priorities.PR95), storageParameters_Sequencer);
			mission = new MyMission();
		}

		public MyMission getNextMission() {
			if (mission.terminationPending()) {
				devices.Console.println("\nMySeq.getNextMission:null");
				testResult = true;
				return null;
			} else {
				devices.Console.println("\nMySeq.getNextMission");
				return mission;
			}
		}
	}

	private static class MyApp implements Safelet<MyMission> {

		public MissionSequencer<MyMission> getSequencer() {
			devices.Console.println("MyApp.getSequencer");
			return new MySequencer();
		}

		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}

		public void initializeApplication() {
		}
	}

	public static StorageParameters storageParameters_Sequencer;
	public static StorageParameters storageParameters_Handlers;

	public static void main(String[] args) {
		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, Const.IMMORTAL_MEM, Const.MISSION_MEM);

		storageParameters_Handlers = new StorageParameters(Const.PRIVATE_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, 0, 0);

		devices.Console.println("\n**** Level2Thread0 Test main.begin ****");
		new LaunchLevel2(new MyApp());
		devices.Console.println("**** Level2Thread0 Test main.end ******");

		if (testResult == true)
			args = null;
	}

}
