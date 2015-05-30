package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class ANTTestArrayLength {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

    @IcecapCompileMe
    public static boolean test() {
        byte[] array = new byte[1];
        if (array.length == 1) {
            return false;
        } else {
            return true;
        }
    }
}
