package test;

import icecaptools.IcecapCompileMe;

public class AOTTestLoop1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    @IcecapCompileMe
    public static String[] test(String[] args) {
        int x = 5;
        for (int i = 0; i < 5; i++) {
            x--;
        }
        if (x == 0) {
            return null;
        } else {
            return args;
        }
    }
}
