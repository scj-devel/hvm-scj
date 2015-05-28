package test;

import icecaptools.IcecapCompileMe;

import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.Services;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

public class TestSCJWaitAndNotifyAll1 {

	private static class SharedResource {
		int count = 0;

		public synchronized void wait0ForGo() {
			count++;
			devices.Console.println("> before wait 0"); 
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			devices.Console.println("< after wait 0"); 
		}

		public synchronized void wait1ForGo() {
			count++;
			devices.Console.println("> before wait 1"); 
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			devices.Console.println("< after wait 1"); 
		}

		public synchronized void go() {
			if (count > 0) {
				devices.Console.println("    before notify 2");
				count--; 
				notifyAll(); 
				devices.Console.println("    after notify 2");
			}
		}
	}

	private static class MyMission extends Mission {

		@Override
		protected void initialize() {

			SharedResource shared = new SharedResource();

			MyPEH0 myPEH0 = new MyPEH0(new PriorityParameters(12),
					new PeriodicParameters(new RelativeTime(Clock
							.getRealtimeClock()), new RelativeTime(500, 0,
							Clock.getRealtimeClock())),
					storageParameters_Handlers, shared, this);
			myPEH0.register();

			MyPEH1 myPEH1 = new MyPEH1(new PriorityParameters(12),
					new PeriodicParameters(new RelativeTime(Clock
							.getRealtimeClock()), new RelativeTime(500, 0,
							Clock.getRealtimeClock())),
					storageParameters_Handlers, shared);
			myPEH1.register();

			MyPEH2 myPEH2 = new MyPEH2(new PriorityParameters(12),
					new PeriodicParameters(new RelativeTime(0, 0, Clock
							.getRealtimeClock()), new RelativeTime(500, 0,
							Clock.getRealtimeClock())),
					storageParameters_Handlers, shared);
			myPEH2.register();

			Services.setCeiling(shared, 12);
		}

		@Override
		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		private class MyPEH0 extends PeriodicEventHandler {
			private int cnt = 0;
			Mission m;

			SharedResource shared;

			@IcecapCompileMe
			public MyPEH0(PriorityParameters priority,
					PeriodicParameters release, StorageParameters storage,
					SharedResource shared, Mission m) {
				super(priority, release, storage);
				this.m = m;
				this.shared = shared;
			}

			@Override
			@IcecapCompileMe
			public void handleAsyncEvent() {
				devices.Console.println("  --------> PEH0.handleAsyncEvent");

				shared.wait0ForGo();

				cnt++;
				devices.Console.println("      PEH0 end \n");

				if (cnt == 4) {
					m.requestTermination();
					devices.Console.println("			Mission T");
				}
			}
		}

		private class MyPEH1 extends PeriodicEventHandler {
			@SuppressWarnings("unused")
			private int cnt = 0;

			SharedResource shared;

			@IcecapCompileMe
			public MyPEH1(PriorityParameters priority,
					PeriodicParameters release, StorageParameters storage,
					SharedResource shared) {
				super(priority, release, storage);
				this.shared = shared;
			}

			@Override
			@IcecapCompileMe
			public void handleAsyncEvent() {
				devices.Console.println("  --------> PEH1.handleAsyncEvent");

				shared.wait1ForGo();

				cnt++;
				devices.Console.println("      PEH1 end \n");
			}
		}

		private class MyPEH2 extends PeriodicEventHandler {
			@SuppressWarnings("unused")
			private int cnt = 0;

			SharedResource shared;

			public MyPEH2(PriorityParameters priority,
					PeriodicParameters release, StorageParameters storage,
					SharedResource shared) {
				super(priority, release, storage);
				this.shared = shared;
			}

			@Override
			public void handleAsyncEvent() {
				devices.Console.println("  --------> PEH2.handleAsyncEvent");

				shared.go();

				cnt++;
				devices.Console.println("			PEH2 end \n");
			}
		}
	}

	private static class MySequencer extends MissionSequencer<Mission> {
		private Mission mission;
		private int count = 0;

		public MySequencer(PriorityParameters priority,
				StorageParameters storage) {
			super(priority, storage);
			mission = new MyMission();
		}

		@Override
		protected Mission getNextMission() {
			if (count == 1) {
				devices.Console.println("MySequencer.getNextMission.count: "
						+ count + "; null");
				return null;
			} else {
				count++;
				return mission;
			}
		}
	}

	private static class MyApp implements Safelet<Mission> {

		@Override
		public MissionSequencer<Mission> getSequencer() {
			return new MySequencer(new PriorityParameters(
					Priorities.SEQUENCER_PRIORITY), storageParameters_Sequencer);
		}

		@Override
		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}

		@Override
		public void initializeApplication() {
			// TODO Auto-generated method stub
		}
	}

	public static StorageParameters storageParameters_Sequencer;
	public static StorageParameters storageParameters_Handlers;

	public static void main(String[] args) {
		Const.setDefaultErrorReporter();
		storageParameters_Sequencer = new StorageParameters(
				Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM,
				Const.IMMORTAL_MEM, Const.MISSION_MEM);

		storageParameters_Handlers = new StorageParameters(
				Const.PRIVATE_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, 0,
				0);

		devices.Console.println("\n***** TestSCJWaitAndNotifyAll1 main.begin *****");
		new LaunchLevel1(new MyApp());
		devices.Console.println("***** TestSCJWaitAndNotifyAll1 main.end *****");
		args = null;
	}

}
