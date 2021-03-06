/**************************************************************************
 * File name  : TestSCJThreadMemory.java
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

import vm.Memory;
import vm.VMTest;

public class TestSCJSingleThreadMemory {
	private static int sizeOfSmallObject;
	private static int sizeOfBiggerObject;

	static boolean fail;

	private static class SmallObject {
		@SuppressWarnings("unused")
		int x;
		@SuppressWarnings("unused")
		short y;
	}

	private static class BiggerObject {
		@SuppressWarnings("unused")
		int x;
		@SuppressWarnings("unused")
		int y;
		@SuppressWarnings("unused")
		short z;
	}

	private static class Thread1 extends ManagedThread {
		private int count = 0;

		private RelativeTime delayTime;

		public Thread1(PriorityParameters priority, ScopeParameters storage) {
			super(priority, storage, configParameters);
			this.delayTime = new RelativeTime(100, 0);
		}

		public void run() {
			devices.Console.println("Thread1");
			doWork();
		}

		void doWork() {
			int x = 0, y = 0;
			Memory memory = Memory.getCurrentMemoryArea();

			devices.Console.println("Thread 1 allocating in: " + memory.getName());

			y = memory.consumedMemory();

			while (count < 5) {
				x = memory.consumedMemory();
				if (x != y) {
					devices.Console.println("T1 Fail 1");
					fail = true;
				}

				new SmallObject();

				y = memory.consumedMemory();
				if (y < x + sizeOfSmallObject) {
					devices.Console.println("T1 Fail 2, y = " + y + ", x = " + x);
					fail = true;
				}
				count++;
				delay();
			}
		}

		private void delay() {
			try {
				ManagedThread.sleep(delayTime);
			} catch (InterruptedException e) {
			}
		}
	}

	private static class Thread2 extends ManagedThread {

		private RelativeTime delayTime;

		private int count;

		public Thread2(PriorityParameters priority, ScopeParameters storage) {
			super(priority, storage, configParameters);
			this.delayTime = new RelativeTime(100, 0);
		}

		public void run() {
			devices.Console.println("Thread2");
			doWork();
		}

		void doWork() {
			int x = 0, y = 0;
			Memory memory = Memory.getCurrentMemoryArea();

			devices.Console.println("Thread 2 allocating in: " + memory.getName());

			y = memory.consumedMemory();

			while (count < 5) {
				x = memory.consumedMemory();
				if (x != y) {
					devices.Console.println("T2 Fail 1");
					fail = true;
				}

				new BiggerObject();

				y = memory.consumedMemory();
				if (y < x + sizeOfBiggerObject) {
					devices.Console.println("T2 Fail 2, y = " + y + ", x = " + x);
					fail = true;
				}
				count++;
				delay();
			}
		}

		private void delay() {
			try {
				ManagedThread.sleep(delayTime);
			} catch (InterruptedException e) {
			}
		}
	}

	private static class MyMission extends Mission  {
		public void initialize() {
			devices.Console.println("** MyMission.initialize");

			new Thread1(new PriorityParameters(Priorities.PR96), storageParameters_Handlers).register();

			new Thread2(new PriorityParameters(Priorities.PR95), storageParameters_Handlers).register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

	}

	private static class MySequencer extends MissionSequencer {

		private MyMission mission;

		private int count = -1;

		MySequencer() {
			super(new PriorityParameters(Priorities.SEQUENCER_PRIORITY), 
				storageParameters_Sequencer, configParameters);

			mission = new MyMission();
		}

		public MyMission getNextMission() {
			count++;
			if (count >= 1) {
				devices.Console.println("\nMySeq.getNextMission:null");
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

	static ScopeParameters storageParameters_Sequencer;
	static ScopeParameters storageParameters_Handlers;
	static ConfigurationParameters configParameters;


	public static void main(String[] args) {
		Memory memory = Memory.getHeapArea();
		new SmallObject();
		int x = memory.consumedMemory();
		new SmallObject();
		int y = memory.consumedMemory();
		sizeOfSmallObject = y - x;

		x = memory.consumedMemory();
		new BiggerObject();
		y = memory.consumedMemory();
		sizeOfBiggerObject = y - x;

		devices.Console.println("small object is " + sizeOfSmallObject + " bytes");
		devices.Console.println("bigger object is " + sizeOfBiggerObject + " bytes");

		Const.setDefaultErrorReporter();
		vm.Memory.startMemoryAreaTracking();

//		storageParameters_Sequencer = new ScopeParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
//				Const.IMMORTAL_MEM, Const.PRIVATE_MEM, Const.MISSION_MEM);
//
//		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_BACKING_STORE,
//				0, 2002, 0);
		
		storageParameters_Sequencer = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO		
		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO
		
		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console.println("\n********** TestSCJThreadMemory main.begin ******************");
		new LaunchLevel2(new MyApp());
		devices.Console.println("********* TestSCJThreadMemory main.end ********************");
		VMTest.markResult(fail);
	}
}
