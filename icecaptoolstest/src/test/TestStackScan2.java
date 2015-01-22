package test;

import reflect.ClassInfo;
import reflect.ObjectInfo;
import thread.Semaphore;
import thread.Thread;
import util.ReferenceIterator;
import vm.HVMHeap;
import vm.Heap;

public class TestStackScan2 {

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

            Heap heap = HVMHeap.getHeap();
            
            ReferenceIterator refs = heap.getRefFromStacks();
            
            while (refs.hasNext())
            {
                int possibleRef = refs.next();
                Thread.print("ref: " + possibleRef);
                if (isWithinRangeOfHeap(heap, possibleRef))
                {
                    ObjectInfo oia = new ObjectInfo(possibleRef);

                    short classId = oia.classId;
                    
                    if (classId >= 0)
                    {
                        if (classId < ClassInfo.getNumberOfClasses())
                        {
                            ClassInfo cinfo = ClassInfo.getClassInfo(classId);
                            StringBuffer name = cinfo.getName();
                            
                            if (name.toString().equals("test.TestStackScan2$A"))
                            {
                                return false;
                            }
                        }
                    }
                }
            
            }
            devices.Console.println("done!");
            return error;
        }

        private boolean isWithinRangeOfHeap(Heap heap, int possibleRef) {
            return (possibleRef > heap.getStartAddress()) && (possibleRef < heap.getStartAddress() + heap.getHeapSize());
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
