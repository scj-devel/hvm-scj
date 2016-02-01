package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class AOTTestInvokeVirtual2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    @IcecapCompileMe
    private static boolean test() {
        AOTTestInvokeVirtual2 obj = new AOTTestInvokeVirtual2();
        int i = obj.foo(1);
        i += obj.foo(1);
        i += obj.foo(1);
        i += obj.foo(1);
        i += obj.foo(1);
        i += obj.foo(1);

        if (i == 6) {
            return false;
        } else {
            return true;
        }
    }

    public int foo(int i) {
        return i;
    }
}
