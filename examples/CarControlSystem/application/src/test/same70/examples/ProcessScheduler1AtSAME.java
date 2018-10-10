package test.same70.examples;


import test.same70.configuration.MachineFactorySAME;
import test.same70.configuration.SCJTargetConfigurationSAME;
import vm.Machine;
import vm.MachineFactory;
import vm.Monitor;
import vm.Process;
import vm.Scheduler;

public class ProcessScheduler1AtSAME extends SCJTargetConfigurationSAME {
	
	static volatile int count = 0;

	private static class TwoProcessScheduler implements Scheduler {
		private Process p1;
		private Process p2;
		private Process current;
		private Process mainProcess;
		private MachineFactory mFactory;

		public TwoProcessScheduler(Process task1, Process task2, Process mainProcess, int[] stack, MachineFactory mFactory) {
			this.p1 = task1;
			this.p2 = task2;
			this.mainProcess = mainProcess;
			this.mFactory = mFactory;
			p1.initialize();
			p2.initialize();
			current = task1;
			
		}

		@Override
		public Process getNextProcess() {
			if (count > 20) {
				terminated();
				current.transferTo(mainProcess);
			}

			if (current == p1) {
				current = p2;
			} else {
				current = p1;
			}
			return current;
		}

		@Override
		public void wait(Object target) {
		}
		@Override
		public void notify(Object target) {
		}
		@Override
		public Monitor getDefaultMonitor() {
			return null;
		}
		@Override
		public void notifyAll(Object target) {
		}
		@Override
		public void terminated() {
			devices.Console.println("terminated");
			mFactory.stopSystemTick();
		}
	}

	public static void main(String args[]) {
		
		init();

		devices.Console.println("ProcessScheduler1AtSAME begin");
		
		MachineFactory mFac = new MachineFactorySAME();		
				
		Process p1 = new vm.Process(new vm.ProcessLogic() {

			@Override
			public void run() {
				while (true) {
					if ((count & 0x1) == 1) {  // if count is odd
					//if ((count % 2) == 1) {  // if count is odd
						count++;
						blink (1);
					}
					vm.ClockInterruptHandler.instance.disable();
	                devices.Console.println("p1:" + count);
	                vm.ClockInterruptHandler.instance.enable();
				}
			}

			@Override
			public void catchError(Throwable t) {
				devices.Console.println("catchError p1");
			}
		}, new int[getReasonableProcessStackSize()]);

		Process p2 = new vm.Process(new vm.ProcessLogic() {

			@Override
			public void run() {
				while (true) {
					if ((count & 0x1) == 0) {  // if count is even
					//if ((count % 2) == 0) {  // if count is even
						count++;
						blink (1);					
					}	
					vm.ClockInterruptHandler.instance.disable();
	                devices.Console.println("p2:" + count);
	                vm.ClockInterruptHandler.instance.enable();
				}
			}

			@Override
			public void catchError(Throwable t) {
				devices.Console.println("catchError p2");
			}
		}, new int[getReasonableProcessStackSize()]);

		int[] sequencerStack = new int[getReasonableProcessStackSize()];
		Process mainProcess = new vm.Process(null, null);
		Scheduler scheduler = new TwoProcessScheduler(p1, p2, mainProcess, sequencerStack, mFac);

		vm.ClockInterruptHandler.initialize(scheduler, sequencerStack);
		vm.ClockInterruptHandler clockHandler = vm.ClockInterruptHandler.instance;

		devices.Console.println("startClockHandler..");
		clockHandler.startClockHandler(mainProcess, mFac);

		devices.Console.println("ProcessScheduler1AtSAME end");
		blink(10000);
	}
}
