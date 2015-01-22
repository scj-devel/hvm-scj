package test;

import icecaptools.IcecapCompileMe;

public class AOTTestInvokeVirtual2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    @IcecapCompileMe
    private static String[] test(String[] args) {
        AOTTestInvokeVirtual2 obj = new AOTTestInvokeVirtual2();
        int i = obj.foo(1);
        i += obj.foo(1);
        i += obj.foo(1);
        i += obj.foo(1);
        i += obj.foo(1);
        i += obj.foo(1);

        if (i == 6) {
            return null;
        } else {
            return args;
        }
    }

    public int foo(int i) {
        return i;
    }
}
