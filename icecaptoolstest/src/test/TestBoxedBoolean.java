package test;

import vm.VMTest;

public class TestBoxedBoolean {

    private static Boolean sb = false;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
       boolean failed = test();
       VMTest.markResult(failed);
    }

    private static boolean test() {
        Boolean b = true;
        if (b)
        {
            b = false;
        }
        if (!sb)
        {
            return b;
        }
        return true;
    }
}
