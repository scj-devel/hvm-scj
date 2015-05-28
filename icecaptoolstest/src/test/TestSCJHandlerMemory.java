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

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.Memory;

public class TestSCJHandlerMemory {
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

	private static class MyPEH1 extends PeriodicEventHandler {
		private int count = 0;
		private AperiodicEventHandler myAEH;

		public MyPEH1(PriorityParameters priority, PeriodicParameters release, StorageParameters storage,
				AperiodicEventHandler myAEH) {
			super(priority, release, storage);
			this.myAEH = myAEH;
		}

		@Override
		public void handleAsyncEvent() {
			devices.Console.println("-- MyPEH1: " + count);
			count++;
			doWork();
			if (count == 3) {
				myAEH.release();
			}
		}

		void doWork() {
			int x = 0, y = 0;
			Memory memory = Memory.getCurrentMemoryArea();

			devices.Console.println("---- MyPEH1 allocating in: " + memory.getName());

			y = memory.consumedMemory();
			x = memory.consumedMemory();
			if (x != y) {
				devices.Console.println("---- MyPEH1 Fail 1");
				fail = true;
			}

			x = memory.consumedMemory();
			new SmallObject();
			y = memory.consumedMemory();

			if (y != x + sizeOfSmallObject) {
				devices.Console.println("---- MyPEH1 Fail 2, y = " + y + ", x = " + x);
				fail = true;
			}
		}
	}

	private static class MyPEH2 extends PeriodicEventHandler {
		private int count = 0;

		public MyPEH2(PriorityParameters priority, PeriodicParameters release, StorageParameters storage) {
			super(priority, release, storage);
		}

		@Override
		public void handleAsyncEvent() {
			devices.Console.println("------- MyPEH2: " + count);
			count++;
			doWork();
		}

		void doWork() {
			int x = 0, y = 0;
			Memory memory = Memory.getCurrentMemoryArea();

			devices.Console.println("-------- MyPEH2 allocating in: " + memory.getName());

			y = memory.consumedMemory();
			x = memory.consumedMemory();
			if (x != y) {
				devices.Console.println("-------- MyPEH2 Fail 1");
				fail = true;
			}

			x = memory.consumedMemory();
			new BiggerObject();
			y = memory.consumedMemory();

			if (y != x + sizeOfBiggerObject) {
				devices.Console.println("-------- MyPEH2 Fail 2, y = " + y + ", x = " + x);
				fail = true;
			}
		}
	}

	private static class MyAEH extends AperiodicEventHandler {
		private Mission m;

		public MyAEH(PriorityParameters priority, AperiodicParameters release, StorageParameters storage, Mission m) {
			super(priority, release, storage);
			this.m = m;
		}

		@Override
		public void handleAsyncEvent() {
			devices.Console.println("-- MyAEH: terminate mission");
			m.requestTermination();
		}
	}

	private static class MyMission extends Mission {
		public void initialize() {
			devices.Console.println("** MyMission.initialize");

			AperiodicEventHandler myAEH = new MyAEH(new PriorityParameters(10), new AperiodicParameters(
					new RelativeTime(50, 0, Clock.getRealtimeClock()), null), storageParameters_Handlers, this);
			myAEH.register();

			PeriodicEventHandler myPEH1 = new MyPEH1(new PriorityParameters(20), new PeriodicParameters(
					new RelativeTime(), new RelativeTime(1000, 0)), storageParameters_Handlers, myAEH);
			myPEH1.register();

			PeriodicEventHandler myPEH2 = new MyPEH2(new PriorityParameters(20), new PeriodicParameters(
					new RelativeTime(), new RelativeTime(1000, 0)), storageParameters_Handlers);
			myPEH2.register();

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

		devices.Console.println("\n********** TestSCJHandlerMemory main.begin ******************");

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

		devices.Console.println("\nsmall object is " + sizeOfSmallObject + " bytes");
		devices.Console.println("bigger object is " + sizeOfBiggerObject + " bytes \n");

		Const.setDefaultErrorReporter();
		vm.Memory.startMemoryAreaTracking();

		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, 2 * Const.PRIVATE_MEM, 2 * Const.IMMORTAL_MEM,
				Const.MISSION_MEM);

		storageParameters_Handlers = new StorageParameters(Const.PRIVATE_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, 2002, 0, 0);

		new LaunchLevel1(new MyApp());

		devices.Console.println("********* TestSCJHandlerMemory main.end ********************");

		if (!fail) {
			args = null;
		}
	}
}
