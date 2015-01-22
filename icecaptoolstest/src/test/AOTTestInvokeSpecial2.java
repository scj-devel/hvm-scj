package test;

import icecaptools.IcecapCompileMe;

public class AOTTestInvokeSpecial2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    @IcecapCompileMe
    public static String[] test(String[] args) {
        if (foo(10) == 42) {
            return null;
        } else {
            return args;
        }
    }

    @IcecapCompileMe
    public static int foo(int x) {
        return x + 32;
    }
}
