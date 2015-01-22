package test;

import icecaptools.IcecapCompileMe;

public class AOTTestInvokeStatic {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(12, 42, "hej", args);
    }

    @IcecapCompileMe
    public static String[] test(int i, int j, String string, String[] args) {
        if (i == 12) {
            if (j == 42) {
                return foo(j, args);
            }
        }
        return args;
    }

    public static String[] foo(int j, String[] args) {
        if (j == 42) {
            return null;
        } else {
            return args;
        }
    }
}
