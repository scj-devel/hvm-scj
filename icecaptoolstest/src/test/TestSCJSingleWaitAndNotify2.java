package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.Services;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

public class TestSCJSingleWaitAndNotify2 {

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
				notify(); 
				devices.Console.println("    after notify 2");
			}
		}
	}

	private static class MyMission extends Mission {

		@Override
		protected void initialize() {

			SharedResource shared = new SharedResource();

			MyPEH0 myPEH0 = new MyPEH0(new PriorityParameters(15),
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

			Services.setCeiling(shared, 15);
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
					PeriodicParameters release, ScopeParameters storage,
					SharedResource shared, Mission m) {
				super(priority, release, storage, configParameters);
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

				if (cnt >= 4) {
					m.requestTermination();
					devices.Console.println("			Mission T");
				}
			}
		}

		private class MyPEH1 extends PeriodicEventHandler {
			SharedResource shared;

			@IcecapCompileMe
			public MyPEH1(PriorityParameters priority,
					PeriodicParameters release, ScopeParameters storage,
					SharedResource shared) {
				super(priority, release, storage, configParameters);
				this.shared = shared;
			}

			@Override
			@IcecapCompileMe
			public void handleAsyncEvent() {
				devices.Console.println("  --------> PEH1.handleAsyncEvent");

				shared.wait1ForGo();

				devices.Console.println("      PEH1 end \n");
			}
		}

		private class MyPEH2 extends PeriodicEventHandler {

			SharedResource shared;

			public MyPEH2(PriorityParameters priority,
					PeriodicParameters release, ScopeParameters storage,
					SharedResource shared) {
				super(priority, release, storage, configParameters);
				this.shared = shared;
			}

			@Override
			public void handleAsyncEvent() {
				devices.Console.println("  --------> PEH2.handleAsyncEvent");

				shared.go();

				devices.Console.println("			PEH2 end \n");
			}
		}
	}

	private static class MySequencer extends MissionSequencer {
		private MyMission mission;
		private int count = 0;

		public MySequencer(PriorityParameters priority,
				ScopeParameters storage) {
			super(priority, storage, configParameters);
			mission = new MyMission();
		}

		@Override
		protected MyMission getNextMission() {
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

	private static class MyApp implements Safelet {

		@Override
		public MissionSequencer getSequencer() {
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
		Const.setDefaultErrorReporter();
//		storageParameters_Sequencer = new ScopeParameters(
//				Const.OUTERMOST_SEQ_BACKING_STORE, Const.IMMORTAL_MEM,
//				Const.PRIVATE_MEM, Const.MISSION_MEM);
//
//		storageParameters_Handlers = new ScopeParameters(
//				Const.PRIVATE_BACKING_STORE, 0, Const.PRIVATE_MEM,
//				0);
		
		storageParameters_Sequencer = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO		
		storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO
		
		configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

		devices.Console
				.println("\n***** TestSCJWaitAndNotify2 main.begin *****");
		new LaunchLevel1(new MyApp());
		devices.Console.println("***** TestSCJWaitAndNotify2 main.end *****");
		VMTest.markResult(false);
	}

}
