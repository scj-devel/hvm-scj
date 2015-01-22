package test;

import icecaptools.IcecapCompileMe;

public class AOTTestSimple {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    @IcecapCompileMe
    public static String[] test(String[] args) {
        return null;
    }
}
