package test.AVR.ATMega2560;

import devices.AVR.ATMega2560.ATMega2560MachineFactory;
import devices.AVR.ATMega2560.ATMega2560SCJTargetConfiguration;
import vm.MachineFactory;
import vm.Monitor;
import vm.Process;
import vm.Scheduler;

public class TestProcessScheduler1 extends ATMega2560SCJTargetConfiguration {
	static int count;

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
			if (count > 10000) {
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
			mFactory.stopSystemTick();
		}
	}

	public static void main(String args[]) {
		MachineFactory mFactory = new ATMega2560MachineFactory();
		
		Process p1 = new vm.Process(new vm.ProcessLogic() {

			@Override
			public void run() {
				while (true) {
					if ((count & 0x1) == 1) {
						count++;
						PORTG ^= 0x3;
					}
				}
			}

			@Override
			public void catchError(Throwable t) {
				blink(1000);
			}
		}, new int[getReasonableProcessStackSize()]);

		Process p2 = new vm.Process(new vm.ProcessLogic() {

			@Override
			public void run() {
				while (true) {
					if ((count & 0x1) == 0) {
						count++;
						PORTG ^= 0x3;
					}
				}
			}

			@Override
			public void catchError(Throwable t) {
				blink(1000);
			}
		}, new int[getReasonableProcessStackSize()]);

		count = 0;

		DDRG |= 0x3;
		
		int[] sequencerStack = new int[getReasonableProcessStackSize()];
		Process mainProcess = new vm.Process(null, null);
		Scheduler scheduler = new TwoProcessScheduler(p1, p2, mainProcess, sequencerStack, mFactory);

		vm.ClockInterruptHandler.initialize(scheduler, sequencerStack);
		vm.ClockInterruptHandler clockHandler = vm.ClockInterruptHandler.instance;

		clockHandler.startClockHandler(mainProcess, mFactory);

		blink(16000);
	}

	@Override
	public String getOutputFolder() {
		return "/home/skr/hvmsrc";
	}

	@Override
	public int getJavaHeapSize() {
		return 5120;
	}
}
