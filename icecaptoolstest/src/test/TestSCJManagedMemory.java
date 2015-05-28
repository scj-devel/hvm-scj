package test;

import javax.realtime.MemoryArea;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.InnerPrivateMemory;
import javax.safetycritical.LaunchLevel2;
import javax.safetycritical.ManagedMemory;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionMemory;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.PrivateMemory;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.safetycritical.TestPortal;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.Memory;

public class TestSCJManagedMemory {
	private static int sizeOfSmallObject;
	private static int sizeOfBiggerObject;

	static int fails = 0;

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

	static class Work {
		static void doWork(String name) {
			int x = 0, y = 0;
			Memory memory = Memory.getCurrentMemoryArea();

			devices.Console.println("---- " + name + " allocating in: " + memory.getName());

			y = memory.consumedMemory();
			x = memory.consumedMemory();
			//devices.Console.println("---- " + name + ", y = " + y + ", x = " + x);
			if (x != y) {
				devices.Console.println("!!!! " + name + " Fail 1");
				fails++;
			}

			x = memory.consumedMemory();
			new SmallObject();
			y = memory.consumedMemory();
			devices.Console.println("---- " + name + ", y = " + y + ", x = " + x);
			if (y != (x + sizeOfSmallObject)) {
				devices.Console.println("!!!! " + name + " Fail 2, y = " + y + ", x = " + x);
				fails++;
			}
		}
	}

	static class SafeletStub implements Safelet<MissionStub> {
		public MissionSequencer<MissionStub> getSequencer() {
			// seq in immortal memory
			MissionSequencer<MissionStub> seq = new SequencerStub();

			Work.doWork("1 SafeletStub");

			devices.Console.println("---- area 1 is " + Memory.getCurrentMemoryArea() + "\n");

			memRecord[1] = MemoryArea.getMemoryArea(seq);
			if (!(MemoryArea.getMemoryArea(seq) instanceof ManagedMemory.ImmortalMemory))
				errors++;

			return seq;
		}

		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}

		public void initializeApplication() {
		}
	}

	static class SequencerStub extends MissionSequencer<MissionStub> {
		private MissionStub mission;

		SequencerStub() {
			super(new PriorityParameters(Priorities.PR95), storageParameters_Sequencer);
			mission = new MissionStub(this);

			Work.doWork("0 SequencerStub");

			devices.Console.println("---- area 0 is " + MemoryArea.getMemoryArea(mission) + "\n");
			memRecord[0] = MemoryArea.getMemoryArea(mission);
			if (!(MemoryArea.getMemoryArea(mission) instanceof ManagedMemory.ImmortalMemory))
				errors++;
		}

		public MissionStub getNextMission() {
			if (mission.terminationPending())
				return null;
			else
				return mission;
		}
	}

	static class MissionStub extends Mission {
		private MissionSequencer<MissionStub> missSeq;

		public MissionStub(MissionSequencer<MissionStub> missSeq) {
			this.missSeq = missSeq;
		}

		public void initialize() {
			// obj in mission memory
			Object obj = new Integer(2);
			Work.doWork("2 MissionStub");

			devices.Console.println("---- area 2 is " + MemoryArea.getMemoryArea(obj) + "\n");
			memRecord[2] = MemoryArea.getMemoryArea(obj);
			if (!(MemoryArea.getMemoryArea(obj) instanceof MissionMemory))
				errors++;

			// outer memory is immortal memory  
			devices.Console.println("---- area 3 is "
					+ TestPortal.ManagedMemory_getOuterMemory((ManagedMemory) MemoryArea.getMemoryArea(obj)) + "\n");

			memRecord[3] = TestPortal.ManagedMemory_getOuterMemory((ManagedMemory) MemoryArea.getMemoryArea(obj));
			if (!(TestPortal.ManagedMemory_getOuterMemory((ManagedMemory) MemoryArea.getMemoryArea(obj)) instanceof ManagedMemory.ImmortalMemory))
				errors++;

			// missSeq is in immortal
			ManagedMemory.executeInAreaOf(missSeq, new Runnable() {
				public void run() {
					// obj in immortal memory
					Object obj = new Integer(4);
					Work.doWork("4 executeInAreaOf");

					devices.Console.println("---- area 4 is " + MemoryArea.getMemoryArea(obj) + "\n");
					memRecord[4] = MemoryArea.getMemoryArea(obj);
					if (!(MemoryArea.getMemoryArea(obj) instanceof ManagedMemory.ImmortalMemory))
						errors++;
				}
			});

			//      ManagedMemory.executeInOuterArea( 
			//          new Runnable() 
			//          {
			//             public void run()
			//             {
			//               // obj in immortal memory
			//               Object obj = new Integer (5);
			//               Work.doWork("5 executeInOuterArea");
			//               
			//               devices.Console.println("---- area 5 is " + MemoryArea.getMemoryArea(obj) + "\n");
			//               memRecord[5] = MemoryArea.getMemoryArea(obj);
			//               if (!( MemoryArea.getMemoryArea(obj) instanceof ManagedMemory.ImmortalMemory )) 
			//                 errors++;
			//             }
			//          });

			//      ManagedMemory.enterPrivateMemory(4000, 
			//          new Runnable() 
			//          {
			//             public void run()
			//             {
			//               // obj in inner private memory
			//               Object obj = new Integer (6);
			//               Work.doWork("6 enterPrivateMemory");
			//               
			//               devices.Console.println("---- area 6 is " + MemoryArea.getMemoryArea(obj) + "\n");
			//               memRecord[6] = MemoryArea.getMemoryArea(obj);
			//               if (!( MemoryArea.getMemoryArea(obj) instanceof InnerPrivateMemory )) 
			//                 errors++;
			//             }
			//          });

			// pevh in mission memory
			PeriodicEventHandler pevh = new PeriodicEvhStub(new PriorityParameters(Priorities.PR98),
					new PeriodicParameters(new RelativeTime(), // start  
							new RelativeTime(2000, 0)), // period 
					storageParameters_Handlers, missSeq);
			pevh.register();

			devices.Console.println("---- area 7 is " + MemoryArea.getMemoryArea(pevh) + "\n");
			memRecord[7] = MemoryArea.getMemoryArea(pevh);
			if (!(MemoryArea.getMemoryArea(pevh) instanceof MissionMemory))
				errors++;
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		/*@ 
		  protected behaviour
		    requires true; 
		  
		    ensures memRecord[0] instanceof ManagedMemory.ImmortalMemory;
		    ensures memRecord[1] instanceof ManagedMemory.ImmortalMemory;
		    // ...        
		@*/
		protected boolean cleanUp() {
			//      devices.Console.println("\nMissionStub.cleanUp: not finished ... ??");
			//      for (int i = 0; i < RECORDSIZE; i++)
			//        devices.Console.println("Mem " + i + " : " + memRecord[i]);
			return true;
		}
	}

	static class PeriodicEvhStub extends PeriodicEventHandler {
		MissionSequencer<MissionStub> missSeq;

		protected PeriodicEvhStub(PriorityParameters priority, PeriodicParameters periodic, StorageParameters storage,
				MissionSequencer<MissionStub> missSeq) {
			super(priority, periodic, storage);
			this.missSeq = missSeq;
		}

		public void handleAsyncEvent() {
			// obj in private memory
			Object obj = new Integer(8);
			Work.doWork("8 handleAsyncEvent");
			devices.Console.println("---- area 8  is " + MemoryArea.getMemoryArea(obj) + "\n");

			memRecord[8] = MemoryArea.getMemoryArea(obj);
			if (!(MemoryArea.getMemoryArea(obj) instanceof PrivateMemory))
				errors++;

			ManagedMemory.enterPrivateMemory(4000, new Runnable() {
				public void run() {
					// obj in inner private memory
					Object obj = new Integer(9);
					Work.doWork("9 enterPrivateMemory");

					devices.Console.println("---- area 9 is " + MemoryArea.getMemoryArea(obj) + "\n");
					memRecord[9] = MemoryArea.getMemoryArea(obj);
					if (!(MemoryArea.getMemoryArea(obj) instanceof InnerPrivateMemory))
						errors++;
				}
			});

			ManagedMemory.executeInAreaOf(this.missSeq, new Runnable() {
				public void run() {
					// obj in immortal memory
					Object obj = new Integer(10);
					Work.doWork("10 executeInAreaOf");

					devices.Console.println("---- area 10  is " + MemoryArea.getMemoryArea(obj) + "\n");
					memRecord[10] = MemoryArea.getMemoryArea(obj);
					if (!(MemoryArea.getMemoryArea(obj) instanceof ManagedMemory.ImmortalMemory))
						errors++;
				}
			});

			ManagedMemory.executeInAreaOf(this, new Runnable() {
				public void run() {
					// obj in mission memory
					Object obj = new Integer(11);
					Work.doWork("11 executeInAreaOf");
					devices.Console.println("---- area 11 is " + MemoryArea.getMemoryArea(obj) + "\n");
					memRecord[11] = MemoryArea.getMemoryArea(obj);
					if (!(MemoryArea.getMemoryArea(obj) instanceof MissionMemory))
						errors++;
				}
			});

			//      ManagedMemory.executeInOuterArea( 
			//          new Runnable() 
			//          {
			//             public void run()
			//             {
			//               // obj in mission memory
			//               Object obj = new Integer (12);
			//               Work.doWork("12 executeInOuterArea");
			//               
			//               devices.Console.println("---- area 12  is " + MemoryArea.getMemoryArea(obj) + "\n");
			//               memRecord[12] = MemoryArea.getMemoryArea(obj);
			//               if (!( MemoryArea.getMemoryArea(obj) instanceof MissionMemory )) 
			//                 errors++;
			//             }
			//          });

			missSeq.signalTermination();
		}
	}

	/*
	 * The MemoryAreas should be 
	 *   [0]:  ImmortalMemory,
	 *   [1]:  ImmortalMemory,
	 *   [2]:  MissionMemory,
	 *   [3]:  ImmortalMemory,
	 *   [4]:  ImmortalMemory,
	 *   [5]:  ImmortalMemory,
	 *   [6]:  InnerPrivateMemory,
	 *   [7]:  MissionMemory,
	 *   [8]:  PrivateMemory,
	 *   [9]:  InnerPrivateMemory,
	 *   [10]: ImmortalMemory,
	 *   [11]: MissionMemory,
	 *   [12]: MissionMemory
	 *   
	 */
	static final int RECORDSIZE = 13;
	static MemoryArea[] memRecord = new MemoryArea[RECORDSIZE];

	static int errors = 0;

	static StorageParameters storageParameters_Sequencer;
	static StorageParameters storageParameters_Handlers;

	static void printMemRecords() {
		for (int i = 0; i < RECORDSIZE; i++) {
			devices.Console.println("area " + i + " is " + memRecord[i]);
		}
	}

	public static void main(String[] args) {
		devices.Console.println("\n***** TestSCJManagedMemory begin *****");

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

		//    Const.setDefaultErrorReporter();
		//    vm.Memory.startMemoryAreaTracking();

		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { 2 * Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM + 3, Const.IMMORTAL_MEM,
				Const.MISSION_MEM);

		storageParameters_Handlers = new StorageParameters(Const.PRIVATE_BACKING_STORE + 4,
				new long[] { 2 * Const.HANDLER_STACK_SIZE }, 15002, 0, 0);

		new LaunchLevel2(new SafeletStub());

		devices.Console.println("***** TestSCJManagedMemory end ***** \n");

		devices.Console.println("Errors: " + errors);
		devices.Console.println("Fails : " + fails);
		//printMemRecords();
		//vm.Memory.reportMemoryUsage();

		if (fails == 0 && errors == 0)
			args = null;
	}
}
