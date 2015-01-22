package test;

import thread.JavaLangThreadScheduler;
import vm.Machine;

public class TestJavaLangThread5 {

    private static class Counter  {
        int count;
        
        public Counter() {
            count = 0;
        }

        synchronized public void incCount() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }
    
    private static class Worker implements Runnable {
        private int top;
        private Counter c;

        public Worker(int i, Counter c) {
            this.top = i;
            this.c = c;
        }

        @Override
        public void run() {
            while (top > 0) {
                top--;
                c.incCount();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Machine.setCurrentScheduler(new JavaLangThreadScheduler());

        Counter c = new Counter();

        Thread thr1 = new Thread(new Worker(100000, c));
        Thread thr2 = new Thread(new Worker(320000, c));

        thr1.start();
        thr2.start();
        try {
            thr1.join();
            thr2.join();
        } catch (InterruptedException e) {
            devices.Console.println("got interrupted");
            return;
        }

        if (c.getCount() == 420000) {
            args = null;
        }

        devices.Console.println("got count = " + c.getCount());
    }
}
