package test;

import vm.VMTest;

public class TestF2I {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        VMTest.markResult(failed);
    }

    public static boolean test() {
        float f = 4.2f;
        int x = (int) f;
        if (x == 4) {
            return false;
        } else {
            return true;
        }
    }

}
