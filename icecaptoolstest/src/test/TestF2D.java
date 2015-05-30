package test;

import vm.VMTest;

public class TestF2D {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        VMTest.markResult(failed);
    }

    public static boolean test() {
        float f = 2.0f;
        double d = f;
        double diff = d - 2.0d;
        if (diff < 0)
        {
            diff = -diff;
        }
        if (diff < 0.00001)
        {
            return false;
        }
            
        return true;
    }

}
