package test;

import vm.VMTest;

public class TestDLoad {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        VMTest.markResult(failed);
    }

    @SuppressWarnings("unused")
    public static boolean test() {
        boolean a, b, c, e;
        double d = 1.0;
        if (d + 200.0 == 201.0){
            return false;
        }
        else
        {
            return true;
        }
    }
}
