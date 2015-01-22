package test;

import icecaptools.IcecapCompileMe;
import thread.Thread;

public class TestThread3 extends Thread  {

    private static int count;
    private static boolean stop;

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test();
        if (!failure)
        {
            args = null;
        }
    }
    
    @IcecapCompileMe
    protected static boolean test() {
        Thread thr1 = new TestThread3();
        @SuppressWarnings("unused")
        Thread thr2 = new Thread(thr1);

        stop = false;
        count = 0;
        
        thr1.start();

        stop = true;

        try {
            thr1.join();
            if (count > 0)
            {
                return false;
            }
        } catch (InterruptedException e) {
        }
        return true;
    }

    @Override
    public void run() {
        do {
            count++;
        } while (!stop);
    }
}
