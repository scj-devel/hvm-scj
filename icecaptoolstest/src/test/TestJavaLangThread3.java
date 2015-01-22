package test;

import icecaptools.IcecapCompileMe;
import icecaptools.IcecapVolatile;

public class TestJavaLangThread3 extends Thread {


    private static int count;
    private static boolean stop;

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test();
        if (!failure) {
            args = null;
        }
    }
    
    
    @IcecapCompileMe
    protected static boolean test() {
        Thread thr1 = new TestJavaLangThread3();

        stop = false;
        count = 0;

        thr1.start();

        wiatForIt();

        stop = true;

        try {
            thr1.join();
            devices.Console.println("count = " + count);
            if (count >= 1000) {
                return false;
            }
        } catch (InterruptedException e) {
        }
        return true;
    }

    @IcecapVolatile("x")
    private static void wiatForIt() {
        int x = count;
        while (x < 1000) {
            x = count;
        }
    }

    @Override
    public void run() {
        do {
            count++;
        } while (!stop);
    }
}
