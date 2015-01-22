package test;

import gc.GCMonitor;
import gc.GarbageCollector;
import icecaptools.IcecapCompileMe;
import test.icecapvm.DefaultGCMonitor;
import thread.Thread;

public class TestGCSimple {

    private static class MyMonitor extends DefaultGCMonitor {
        @Override
        public void visitChild(int parent, int child) {
            super.visitChild(parent, child);
        }

        @Override
        public void addStaticRoot(int ref) {
            // devices.Console.println("static: " + ref);
        }

        @Override
        @IcecapCompileMe
        public void addStackRoot(int ref) {
            // devices.Console.println("stack: " + ref);
        }

        @Override
        public void freeObject(int ref) {
            super.freeObject(ref);
            // printRef(ref, "free: ");
        }
    }

    private static class Mutator implements Runnable {

        int[] array;

        public Mutator(int[] array) {
            this.array = array;
        }

        @Override
        public void run() {
            GCMonitor monitor = new MyMonitor();
            GarbageCollector.registerMonitor(monitor);
            GarbageCollector.start();
            GarbageCollector.requestCollection();
            GarbageCollector.waitForNextCollection();
            array[0] = monitor.getFreeedObjects();
            monitor.reset();
            
            GarbageCollector.requestCollection();
            GarbageCollector.waitForNextCollection();
            array[1] = monitor.getFreeedObjects();
            monitor.reset();
            
            GarbageCollector.requestCollection();
            GarbageCollector.waitForNextCollection();
            array[2] = monitor.getFreeedObjects();
            monitor.reset();
            
            GarbageCollector.requestCollection();
            GarbageCollector.waitForNextCollection();
            array[3] = monitor.getFreeedObjects();
            monitor.reset();
            
            /*
            GarbageCollector.requestCollection();
            GarbageCollector.waitForNextCollection();

            array[1] = monitor.getFreeedObjects();
            monitor.reset();

            test1();

            GarbageCollector.requestCollection();
            GarbageCollector.waitForNextCollection();

            array[2] = monitor.getFreeedObjects();
            monitor.reset();*/
            /*

            test2();

            GarbageCollector.requestCollection();
            GarbageCollector.waitForNextCollection();

            array[3] = monitor.getFreeedObjects();
            monitor.reset();*/
        }
    }

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args)  {
        int[] array = new int[4];

        Thread m = new Thread(new Mutator(array));

        m.start();

        try {
            m.join();
        } catch (InterruptedException e) {
        }
        for (int i = 0; i < array.length; i++) {
            devices.Console.println("array[" + i + "] = " + array[i]);
            if (array[i] == 0)
            {
                return;
            }
        }

        args = null;
    }
}
