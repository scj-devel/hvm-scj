package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class AOTTestException2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

    @IcecapCompileMe 
    public static boolean test() {
        try {
            throw new Exception();
        } catch (Exception e) {
            return false;
        }
    }
}
