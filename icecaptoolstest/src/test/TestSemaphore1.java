package test;

import thread.Thread;
import thread.Semaphore;

public class TestSemaphore1 {

    private static int count;
    private static boolean succeeded;
    
    private static class Controller extends Thread {
        private Semaphore gate;

        Controller(Semaphore gate) {
            this.gate = gate;
        }

        @Override
        public void run() {
            if (count == 0) {
                gate.release();
                while (count == 0)
                {
                    ;
                }
                if (count == 1)
                {
                    gate.release();
                    while (count == 1)
                    {
                        ;
                    }
                    if (count == 2)
                    {
                        succeeded = true;
                    }
                }
            }
        }
    }

    private static class Worker extends Thread {
        private Semaphore gate;

        Worker(Semaphore gate) {
            this.gate = gate;
        }

        @Override
        public void run() {
            gate.acquire();
            count++;
            gate.acquire();
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Semaphore gate = new Semaphore((byte) 0);

        count = 0;
        succeeded = false;
        
        Controller controller = new Controller(gate);
        Worker worker = new Worker(gate);
        
        worker.start();
        controller.start();
        
        worker.join();
        controller.join();
        
        if (succeeded)
        {
            args = null;
        }
    }
}
