package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class AOTTestInvokeSpecial4 {

    /**
     * @param args
     */
    public static void main(String[] args) {
       VMTest.markResult(test());
    }

    @IcecapCompileMe
    public static boolean test() {
        try {
            foo(32);
        } catch (Exception ex) {
            if (ex != null) {
                return false;
            }
        }
        return true;
    }

    @IcecapCompileMe
    public static void foo(int i) throws Exception {
        if (i == 32) {
            throw new Exception();
        }
    }
}
