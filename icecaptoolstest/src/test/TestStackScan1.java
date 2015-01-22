package test;

import java.util.Iterator;

import thread.Semaphore;
import thread.Thread;
import thread.ThreadManager;

public class TestStackScan1 {

    private static class A {
        @SuppressWarnings("unused")
        int x;

        A() {
            this.x = 42;
        }
    }

    private static class ThreadToScan extends Thread {

        private Semaphore sem1;
        private Semaphore sem2;

        ThreadToScan(Semaphore sem1, Semaphore sem2) {
            this.sem1 = sem1;
            this.sem2 = sem2;
        }

        @Override
        public void run() {
            A a = new A();
            foo(a);
        }

        public void foo(A a) {
            bar(a);
        }

        private void bar(A a) {
            sem2.release();
            sem1.acquire();
        }
    }

    private static class StackScanner extends Thread {

        private Semaphore sem1;
        private Semaphore sem2;

        public boolean failed;

        StackScanner(Semaphore sem1, Semaphore sem2) {
            this.sem1 = sem1;
            this.sem2 = sem2;
        }

        @Override
        public void run() {
            sem2.acquire();
            this.failed = scanStack();
            sem1.release();
        }

        private boolean scanStack() {
            boolean error = true;
            devices.Console.println("starting stack scan...");

            ThreadManager manager = Thread.getScheduler();

            Iterator<Thread> threads = manager.getThreads();

            while (threads.hasNext()) {
                Thread current = threads.next();
                if (current != Thread.currentThread()) {
                    int[] stack = current.getStack();
                    if (stack != null) {
                        error = handleStack(current, stack);
                    }
                }
            }
            devices.Console.println("done!");
            return error;
        }

        protected boolean handleStack(Thread current, int[] stack) {
            short jtop = current.getJavaStackTop();
            devices.Console.println("thread [" + stack.length + "], top = " + jtop);
            for (short index = 0; index < jtop; index++) {
                devices.Console.println("stack[" + index + "] = " + stack[index]);
            }
            short ctop = (short) current.getCStackTop();   
            
            if (ctop <= jtop)
            {
                return true;
            }

            if (ctop >= stack.length)
            {
                return true;
            }

            for (short index = ctop; index < stack.length; index++) {
                devices.Console.println("stack[" + index + "] = " + stack[index]);
            }
            if (jtop > 10) {
                return false;
            }
            return true;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Semaphore sem1 = new Semaphore((byte) 0);
        Semaphore sem2 = new Semaphore((byte) 0);

        ThreadToScan threadToScan = new ThreadToScan(sem1, sem2);
        StackScanner stackScanner = new StackScanner(sem1, sem2);

        threadToScan.start();
        stackScanner.start();

        try {
            threadToScan.join();
            stackScanner.join();
            if (!stackScanner.failed) {
                args = null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
