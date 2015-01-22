package test;

import thread.Thread;

public class TestThread1 {

    private static int count;

    private static class Counter implements Runnable {
        private int top;

        public Counter(int i) {
            this.top = i;
        }

        @Override
        public void run() {
            while (top > 0) {
                top--;
                count++;
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        count = 0;

        Thread thr1 = new Thread(new Counter(100000));
        Thread thr2 = new Thread(new Counter(320000));

        thr1.start();
        thr2.start();
        try {
            thr1.join();
            thr2.join();
        } catch (InterruptedException e) {
            devices.Console.println("got interrupted");
            return;
        }

        if (count == 420000) {
            args = null;
        }

        devices.Console.println("got count = " + count);
    }
}
