package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class TestRegAllocator1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    @IcecapCompileMe
    public static boolean test() {
        byte x, y;
        x = 10;
        y = 12;
        if (x == y)
        {
            return true;
        }
        else
        {
            return false;    
        }
    }
}
