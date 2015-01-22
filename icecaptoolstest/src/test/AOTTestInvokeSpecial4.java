package test;

import icecaptools.IcecapCompileMe;

public class AOTTestInvokeSpecial4 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    @IcecapCompileMe
    public static String[] test(String[] args) {
        try {
            foo(32);
        } catch (Exception ex) {
            if (ex != null) {
                return null;
            }
        }
        return args;
    }

    @IcecapCompileMe
    public static void foo(int i) throws Exception {
        if (i == 32) {
            throw new Exception();
        }
    }
}
