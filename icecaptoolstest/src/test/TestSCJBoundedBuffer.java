package test;

/**************************************************************************
 * File name  : MyApp.java
 * 
 * This code is available under the license:
 * Creative Commons, http://creativecommons.org/licenses/by-nc-nd/3.0/
 * It is free for non-commercial use. 
 * 
 * VIA University College, Horsens, Denmark, 2011
 * Hans Soendergaard, hso@viauc.dk
 * 
 * Description: 
 * 
 * Revision history:
 *   date   init  comment
 *
 *************************************************************************/
//import icecaptools.IcecapCompileMe;

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
import javax.safetycritical.Services;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.Memory;

public class TestSCJBoundedBuffer {
	static boolean failed;

	private static class MyAperiodicEvh extends AperiodicEventHandler {

		public MyAperiodicEvh(PriorityParameters priority, AperiodicParameters release,
				StorageParameters storageParameters) {
			super(priority, release, storageParameters);
		}

		public void handleAsyncEvent() {
			devices.Console.println("--> MyAPEvh: inv broken");
			failed = true;
			Mission.getMission().requestTermination();
		}
	}

	private static class MyAperiodicEvh1 extends AperiodicEventHandler {

		public MyAperiodicEvh1(PriorityParameters priority, AperiodicParameters release,
				StorageParameters storageParameters) {
			super(priority, release, storageParameters);
		}

		public void handleAsyncEvent() {
			failed = false;
			Mission.getMission().requestTermination();
		}
	}

	static int count = 0;

	private static class Producer extends PeriodicEventHandler {
		private BoundedBuffer buf;
		private int element = 0;

		private AperiodicEventHandler aevh;
		private AperiodicEventHandler aevh1;

		public Producer(PriorityParameters priority, PeriodicParameters periodic, StorageParameters storageParameters,
				BoundedBuffer buf, AperiodicEventHandler aevh, AperiodicEventHandler aevh1) {
			super(priority, periodic, storageParameters);
			this.buf = buf;
			this.aevh = aevh;
			this.aevh1 = aevh1;
		}

		public void handleAsyncEvent() {
			count++;

			int value = produce();
			buf.put(value);
			if (!buf.invariant())
				aevh.release();

			if (count == 50) {
				aevh1.release();
			}
		}

		private int produce() // takes some time
		{
			element++;
			for (int i = 0; i < 2 * 100; i++) {
				element = ~element; // bitwise complement
			}

			return element;
		}
	}

	private static class Consumer extends PeriodicEventHandler {
		private BoundedBuffer buf;
		private AperiodicEventHandler aevh;

		public Consumer(PriorityParameters priority, PeriodicParameters periodic, StorageParameters storageParameters,
				BoundedBuffer buf, AperiodicEventHandler aevh) {
			super(priority, periodic, storageParameters);
			this.buf = buf;
			this.aevh = aevh;
		}

		public void handleAsyncEvent() {
			int value = buf.take();
			if (!buf.invariant())
				aevh.release();
			consume(value);
		}

		private void consume(int x) // takes some time
		{
			for (int i = 0; i < 2 * 100; i++) {
				x = ~x; // bitwise complement
			}
		}
	}

	private static class Display extends PeriodicEventHandler {
		private BoundedBuffer buf;

		private AperiodicEventHandler aevh;

		public Display(PriorityParameters priority, PeriodicParameters periodic, StorageParameters storageParameters,
				BoundedBuffer buf, AperiodicEventHandler aevh) {
			super(priority, periodic, storageParameters);
			this.buf = buf;
			this.aevh = aevh;
		}

		public void handleAsyncEvent() {
			int avarage = buf.getAvarage(5);
			if (!buf.invariant())
				aevh.release();

			display(avarage);
		}

		private void display(int avarage) {
			devices.Console.println(avarage); // "avg: ");
		}
	}

	private static class BoundedBuffer {
		private int[] queue;
		private int count, size;
		private int front, rear;

		public BoundedBuffer(int size) {
			this.queue = new int[size];
			this.size = size;
			this.count = 0;
			this.front = 0;
			this.rear = 0;
		}

		public synchronized void put(int x) {
			if (isFull()) {
				front = (front + 1) % size;
			} else {
				count++;
			}
			queue[rear] = x;
			rear = (rear + 1) % size;
		}

		public synchronized int take() {
			int value = 0;
			if (!isEmpty()) {
				value = queue[front];
				queue[front] = 0; // empty value
				front = (front + 1) % size;
				count--;
			}
			return value;
		}

		public synchronized boolean isEmpty() {
			return count == 0;
		}

		public synchronized boolean isFull() {
			return count == size;
		}

		// Returns avarage of newest values
		// Pre: howMany > 0
		public synchronized int getAvarage(int howMany) {
			int n = howMany;
			if (howMany > count)
				n = count;

			int sum = 0;

			int index = (rear - 1 + size) % size;
			for (int i = 0; i < n; i++) {
				sum += queue[index];
				index = (index - 1 + size) % size;
			}

			if (n > 0)
				return sum / n;
			else
				return 0;
		}

		/**
		 * The invariant is defined as follows:
		 * 
		 * 0 <= count <= size 0 <= rear < size 0 <= front < size rear == (front
		 * + count) % size
		 * 
		 * @return True, if invariant is satisfied, else false.
		 */
		public synchronized boolean invariant() {
			return 0 <= count && count <= size && 0 <= rear && rear < size && 0 <= front && front < size
					&& rear == (front + count) % size;
		}
	}

	private static class MyMission extends Mission {

		public void initialize() {
			BoundedBuffer buffer = new BoundedBuffer(10);

			AperiodicEventHandler aevh = new MyAperiodicEvh(new PriorityParameters(Priorities.PR98),
					new AperiodicParameters(new RelativeTime(100, 0, Clock.getRealtimeClock()), null),
					storageParameters_Handlers);
			aevh.register();

			AperiodicEventHandler aevh1 = new MyAperiodicEvh1(new PriorityParameters(Priorities.PR98),
					new AperiodicParameters(new RelativeTime(100, 0, Clock.getRealtimeClock()), null),
					storageParameters_Handlers);
			aevh1.register();

			new Producer(new PriorityParameters(Priorities.PR97), new PeriodicParameters(new RelativeTime(
					Clock.getRealtimeClock()), // start
					new RelativeTime(100, 0, Clock.getRealtimeClock())), // period
					storageParameters_Handlers, buffer, aevh, aevh1).register();

			new Consumer(new PriorityParameters(Priorities.PR96), new PeriodicParameters(new RelativeTime(
					Clock.getRealtimeClock()), // start
					new RelativeTime(200, 0, Clock.getRealtimeClock())), // period
					storageParameters_Handlers, buffer, aevh).register();

			new Display(new PriorityParameters(Priorities.PR95), new PeriodicParameters(new RelativeTime(
					Clock.getRealtimeClock()), // start
					new RelativeTime(2000, 0, Clock.getRealtimeClock())), // period
					storageParameters_Handlers, buffer, aevh).register();

			devices.Console.println("MyMission: Services.setCeiling");
			Services.setCeiling(buffer, Priorities.PR97);
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

	}

	private static class MyApp implements Safelet<MyMission> {

		public MissionSequencer<MyMission> getSequencer() {
			return new MySequencer();
		}

		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}

		public void initializeApplication() {
		}

		private class MySequencer extends MissionSequencer<MyMission> {
			private MyMission mission;

			MySequencer() {
				super(new PriorityParameters(Priorities.PR94), storageParameters_Sequencer);
				this.mission = new MyMission();
			}

			public MyMission getNextMission() {
				if (mission.terminationPending()) {
					Memory.dumpLiveMemories();
					return null;
				} else {
					return mission;
				}
			}
		}
	}

	public static StorageParameters storageParameters_Sequencer;
	public static StorageParameters storageParameters_Handlers;

	public static void main(String[] args) {
		Const.MEMORY_TRACKER_AREA_SIZE = 30000;
		Memory.startMemoryAreaTracking();
		vm.Process.enableStackAnalysis();

		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, Const.IMMORTAL_MEM, Const.MISSION_MEM);

		storageParameters_Handlers = new StorageParameters(Const.PRIVATE_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, 0, 0);

		failed = true;
		devices.Console.println("\n********** Bounded Buffer main.begin ***********");
		new LaunchLevel1(new MyApp());
		devices.Console.println("********* Bounded Buffer main.end *****************");
		vm.Process.reportStackUsage();
		Memory.reportMemoryUsage();
		if (!failed) {
			args = null;
		}
	}
}
