package test;

import vm.ClockInterruptHandler;
import vm.Monitor;
import vm.Process;
import vm.Scheduler;

public class TestWaitNotify {

    private static class SharedResource {
        boolean empty;

        int number;

        public SharedResource()
        {
            empty = true;
        }
        
        public synchronized int take() throws InterruptedException {
            while (empty) {
                wait();
            }
            empty = true;
            notify();
            devices.Console.println("take " + number);
            return number;
        }

        public synchronized void put(int number) throws InterruptedException {
            while (!empty) {
                wait();
            }
            devices.Console.println("put " + number);
            this.number = number;
            
            empty = false;
            notify();
        }
    }

    private static class Putter implements vm.ProcessLogic {
        private SharedResource sr;

        public Putter(SharedResource sr) {
            this.sr = sr;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    sr.put(i);
                } catch (InterruptedException e) {
                    ;
                }
            }
            devices.Console.println("Putter finished");
        }

        @Override
        public void catchError(Throwable t) {
            devices.Console.println("Process: exception -> " + t);
        }
    }

    private static class Taker implements vm.ProcessLogic {
        private SharedResource sr;
        private boolean error;

        public boolean isError() {
            return error;
        }

        public Taker(SharedResource sr) {
            this.sr = sr;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    if (sr.take() != i) {
                        error = true;
                    }
                } catch (InterruptedException e) {
                    error = true;
                }
            }
            devices.Console.println("Taker finished");
        }

        @Override
        public void catchError(Throwable t) {
            devices.Console.println("Process: exception -> " + t);
        }
    }

    private static class ProcessScheduler implements Scheduler {
        private Process putter;
        private Process taker;
        private Process mainProcess;

        private Process currentProcess;
        private SharedResource sr;

        public ProcessScheduler(Process putter, Process taker, Process mainProcess, SharedResource sr) {
            this.putter = putter;
            this.taker = taker;
            this.mainProcess = mainProcess;
            this.sr = sr;
            putter.initialize();
            taker.initialize();
            currentProcess = null;
        }

        @Override
        public Process getNextProcess() {
            
            if (currentProcess == null) {
                currentProcess = putter;
            } else if (currentProcess == putter) {
                if (putter.isFinished())
                {
                    currentProcess = mainProcess;
                }
            } else if (currentProcess == taker) {
            } else {
                throw new RuntimeException("Unexpected currentProcess in getNextProcess");
            }
            return currentProcess;
        }

        @Override
        public void wait(Object target) {
            if (target != sr) {
                throw new RuntimeException("Unexpected target in wait");
            }
            if (currentProcess == putter) {
                currentProcess = taker;
            } else if (currentProcess == taker) {
                currentProcess = putter;
            } else {
                throw new RuntimeException("Unexpected currentProcess in wait");
            }
            devices.Console.println("waiting");
            ClockInterruptHandler.instance.yield();
        }

        @Override
        public void notify(Object target) {
            if (target != sr) {
                throw new RuntimeException("Unexpected target in notify");
            }
            if (currentProcess == putter) {
                currentProcess = taker;
            } else if (currentProcess == taker) {
                currentProcess = putter;
            } else {
                throw new RuntimeException("Unexpected currentProcess in notify");
            }
            devices.Console.println("notifying");
        }

        @Override
        public Monitor getDefaultMonitor() {
            return null;
        }

        @Override
        public void notifyAll(Object target) {
            // TODO Auto-generated method stub
            
        }
    }

    public static void main(String[] args) {
        SharedResource sr = new SharedResource();
        Process putter = new vm.Process(new Putter(sr), new int[1024]);
        Taker takerLogic = new Taker(sr);
        Process taker = new vm.Process(takerLogic, new int[1024]);

        int[] sequencerStack = new int[1024];
        Process mainProcess = new vm.Process(null, null);
        Scheduler scheduler = new ProcessScheduler(putter, taker, mainProcess, sr);

        vm.ClockInterruptHandler.initialize(scheduler, sequencerStack);
        vm.ClockInterruptHandler clockHandler = vm.ClockInterruptHandler.instance;

        clockHandler.register();
        clockHandler.enable();
        clockHandler.startClockHandler(mainProcess);
        clockHandler.yield();

        devices.Console.println("finished");
        if (!takerLogic.isError()) {
            args = null;
        }
        args = null;
    }
}
