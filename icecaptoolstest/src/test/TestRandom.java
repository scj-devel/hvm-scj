package test;

import util.Random;
import vm.VMTest;

public class TestRandom {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        {
        	VMTest.markResult(failed);
        }
    }

    private static boolean test() {
        Random generator = new Random();
        int next;

        for (int i = 0; i < 10; i++) {
            next = generator.getInt();
            devices.Console.println("" + next);
        }

        for (int i = 0; i < 10; i++) {
            next = generator.getByte();
            devices.Console.println("" + next);
        }
        return false;
    }
}
