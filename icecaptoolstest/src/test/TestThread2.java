package test;

import icecaptools.IcecapCompileMe;
import thread.Thread;
import vm.VMTest;

public class TestThread2 {
    private static class Counter
    {
        int count;

        public Counter()
        {
            count = 0;
        }
        
        public int getCount() {
            return count;
        }

        @IcecapCompileMe
        void incCounter()
        {
            count++;
        }
    }
    
    private static class CounterRunnable implements Runnable {
        private int top;
        private Counter counter;

        public CounterRunnable(int i, Counter counter) {
            this.top = i;
            this.counter = counter;
        }
        
        @Override
        @IcecapCompileMe
        public void run() {
            while (top > 0) {
                top--;
                counter.incCounter();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Counter counter = new Counter();
        
        Thread thr1 = new Thread(new CounterRunnable(10000, counter));
        Thread thr2 = new Thread(new CounterRunnable(32000, counter));

        thr1.start();
        thr2.start();
        try {
            thr1.join();
            thr2.join();
        } catch (InterruptedException e) {
            devices.Console.println("got interrupted");
            return;
        }

        if (counter.getCount() == 42000) {
        	VMTest.markResult(false);
        }

        devices.Console.println("got count = " + counter.getCount());
    }
}
