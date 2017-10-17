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

import javax.realtime.ConfigurationParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.LaunchLevel2;
import javax.safetycritical.ManagedThread;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.Safelet;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.VMTest;

public class TestSCJSingleLevel2Thread0 {
	private static boolean testResult;

	static {
		testResult = false;
	}

	private static class MyThread extends ManagedThread {
		public MyThread(PriorityParameters priority, ScopeParameters storage) {
			super(priority, storage, configParameters);
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

	private static class MySequencer extends MissionSequencer {

		private MyMission mission;

		MySequencer() {
			super(new PriorityParameters(Priorities.PR95), 
					storageParameters_Sequencer, configParameters);
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

	private static class MyApp implements Safelet {

		public MissionSequencer getSequencer() {
			devices.Console.println("MyApp.getSequencer");
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
	}

	public static ScopeParameters storageParameters_Sequencer;
	public static ScopeParameters storageParameters_Handlers;
	public static ConfigurationParameters configParameters;

	public static void main(String[] args) {
//		storageParameters_Sequencer = new ScopeParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
//				Const.IMMORTAL_MEM, Const.PRIVATE_MEM, Const.MISSION_MEM);
//
//		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_BACKING_STORE,
//				0, Const.PRIVATE_MEM, 0);
		
		storageParameters_Sequencer = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO		
		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO
		
		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console.println("\n**** Level2Thread0 Test main.begin ****");
		new LaunchLevel2(new MyApp());
		devices.Console.println("**** Level2Thread0 Test main.end ******");

		if (testResult == true)
			VMTest.markResult(false);
	}

}
