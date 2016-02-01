package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class AOTTestInvokeStatic {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test(12, 42, "hej"));
    }

    @IcecapCompileMe
    public static boolean test(int i, int j, String string) {
        if (i == 12) {
            if (j == 42) {
                return foo(j);
            }
        }
        return true;
    }

    public static boolean foo(int j) {
        if (j == 42) {
            return false;
        } else {
            return true;
        }
    }
}
