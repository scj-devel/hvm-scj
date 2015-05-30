package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class AOTTestInvokeSpecial2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

    @IcecapCompileMe
    public static boolean test() {
        if (foo(10) == 42) {
            return false;
        } else {
            return true;
        }
    }

    @IcecapCompileMe
    public static int foo(int x) {
        return x + 32;
    }
}
