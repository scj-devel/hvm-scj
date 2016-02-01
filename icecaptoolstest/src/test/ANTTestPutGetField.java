package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class ANTTestPutGetField {
    private static class TempClass
    {
        public short x;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test(new TempClass());
        VMTest.markResult(failure);
    }
    
    @IcecapCompileMe
    public static boolean test(TempClass tempClass) {
        tempClass.x = 10;
        tempClass.x++;
        if (tempClass.x == 11)
        {
            return false;
        }
        return true;
    }

}
