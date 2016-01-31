package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class ANTTestArraySimple {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

    @IcecapCompileMe
    public static boolean test() {
        byte[] array = new byte[10];
        for (byte x = 0; x < array.length; x++)
        {
            array[x] = x;
        }
        return false;
    }
}
