package test;

import thread.Thread;
import vm.VMTest;;

public class TestCurrentThread {

    private static boolean passed;
    
    private static class MyThread extends Thread
    {

        @Override
        public void run() {
            if (Thread.currentThread() == this)
            {
                passed = true;
            }
        }
        
    }
    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        Thread tester = new MyThread();
        passed = false;
        tester.start();
        tester.join();
        VMTest.markResult(passed == false);
    }
}
