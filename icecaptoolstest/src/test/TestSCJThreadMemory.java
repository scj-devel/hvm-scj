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

import vm.Memory;

public class TestSCJThreadMemory {
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

		public Thread1(PriorityParameters priority, StorageParameters storage) {
			super(priority, storage);
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
				if (y != x + sizeOfSmallObject) {
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

		public Thread2(PriorityParameters priority, StorageParameters storage) {
			super(priority, storage);
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
				if (y != x + sizeOfBiggerObject) {
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

	private static class MyMission extends Mission {
		public void initialize() {
			devices.Console.println("** MyMission.initialize");

			new Thread1(new PriorityParameters(Priorities.PR96), storageParameters_Handlers).register();

			new Thread2(new PriorityParameters(Priorities.PR95), storageParameters_Handlers).register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

	}

	private static class MySequencer extends MissionSequencer<MyMission> {

		private MyMission mission;

		private int count = -1;

		MySequencer() {
			super(new PriorityParameters(Priorities.SEQUENCER_PRIORITY), storageParameters_Sequencer);

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

		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, Const.IMMORTAL_MEM, Const.MISSION_MEM);

		storageParameters_Handlers = new StorageParameters(Const.PRIVATE_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, 2002, 0, 0);

		devices.Console.println("\n********** TestSCJThreadMemory main.begin ******************");
		new LaunchLevel2(new MyApp());
		devices.Console.println("********* TestSCJThreadMemory main.end ********************");
		if (!fail) {
			args = null;
		}
	}
}
