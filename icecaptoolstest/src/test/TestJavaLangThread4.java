package test;

import thread.JavaLangThreadScheduler;
import vm.Machine;

public class TestJavaLangThread4 {

    private static class Queue  {
        private Object[] queue;
        private int top;
        private int inout;
        private int putWaitCount;
        private int takeWaitCount;
        
        public Queue(int maxCount) {
            this.queue = new Object[maxCount];
            this.top = 0;
        }

        synchronized public void put(Object obj) {
            while (top == queue.length)
            {
                try {
                    putWaitCount++;
                    wait();
                } catch (InterruptedException e) {
                }
            }
            queue[top++] = obj;
            inout++;
            notify();
        }

        synchronized public Object take() {
            while (top == 0)
            {
                try {
                    takeWaitCount++;
                    wait();
                } catch (InterruptedException e) {
                }                
            }
            Object obj = queue[--top];
            inout--;
            notify();
            return obj;
        }

        public int getInOut() {
            return inout;
        }

        public int getPutWaitCount() {
            return putWaitCount;
        }

        public int getTakeWaitCount() {
            return takeWaitCount;
        }
    }
    
    private static class Taker implements Runnable {
        private Queue queue;
        private int count;

        public Taker(int i, Queue queue) {
            this.count = i;
            this.queue = queue;
        }

        @Override
        public void run() {
            while (count > 0) {
                count--;
                queue.take();
            }
        }
    }
    
    private static class Putter implements Runnable {
        private Queue queue;
        private int count;

        public Putter(int i, Queue queue) {
            this.count = i;
            this.queue = queue;
        }

        @Override
        public void run() {
            while (count > 0) {
                count--;
                queue.put(this);
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Machine.setCurrentScheduler(new JavaLangThreadScheduler());

        Queue queue = new Queue(5);

        Thread thr1 = new Thread(new Putter(100000, queue));
        Thread thr2 = new Thread(new Taker(100000, queue));

        thr1.start();
        thr2.start();
        try {
            thr1.join();
            thr2.join();
        } catch (InterruptedException e) {
            devices.Console.println("got interrupted");
            return;
        }

        devices.Console.println("putwait: " + queue.getPutWaitCount());
        devices.Console.println("takewait: " + queue.getTakeWaitCount());
        //System.out.println("putwait: " + queue.getPutWaitCount());
        //System.out.println("takewait: " + queue.getTakeWaitCount());
        
        if (queue.getInOut() == 0) {
            args = null;
        }
    }
}
