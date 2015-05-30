package test;

import vm.VMTest;

public class TestIntegerCache {

    private static final Integer integer = 1;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        if (integer == 1)
        {
            VMTest.markResult(false);
        }
    }
}
