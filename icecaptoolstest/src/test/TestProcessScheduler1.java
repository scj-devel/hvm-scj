package test;

import vm.MachineFactory;
import vm.Monitor;
import vm.POSIX64BitMachineFactory;
import vm.Process;
import vm.Scheduler;
import vm.VMTest;

public class TestProcessScheduler1 {
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
            if (count > 100) {
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
    	MachineFactory mFactory = new POSIX64BitMachineFactory();
    	
        Process p1 = new vm.Process(new vm.ProcessLogic() {
            
            @Override
            public void run() {
                while (true) {
                    if ((count & 0x1) == 1) {
                        count++;
                    }
                    vm.ClockInterruptHandler.instance.disable();
                    devices.Console.println("task1");
                    vm.ClockInterruptHandler.instance.enable();
                }
            }
            
            @Override
            public void catchError(Throwable t) {
                devices.Console.println("Process: exception -> " + t);
            }
        }, new int[1024]);

        Process p2 = new vm.Process(new vm.ProcessLogic() {

            @Override
            public void run() {
                while (true) {
                    if ((count & 0x1) == 0) {
                        count++;
                    }
                    vm.ClockInterruptHandler.instance.disable();
                    devices.Console.println("task2");
                    vm.ClockInterruptHandler.instance.enable();
                }
            }
            @Override
            public void catchError(Throwable t) {
                devices.Console.println("Process: exception -> " + t);
            }            
        }, new int[1024]);

        count = 0;
        
        int[] sequencerStack = new int[1024];
        Process mainProcess = new vm.Process(null, null);
        Scheduler scheduler = new TwoProcessScheduler(p1, p2, mainProcess, sequencerStack, mFactory);

        vm.ClockInterruptHandler.initialize(scheduler, sequencerStack);
        vm.ClockInterruptHandler clockHandler = vm.ClockInterruptHandler.instance;
        
        clockHandler.startClockHandler(mainProcess, mFactory);
        
        devices.Console.println("finished");
        VMTest.markResult(false);
    }
}
