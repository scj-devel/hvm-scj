package test;

import icecaptools.IcecapCompileMe;

public class AOTTestException2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    @IcecapCompileMe 
    public static String[] test(String[] args) {
        try {
            throw new Exception();
        } catch (Exception e) {
            return null;
        }
    }
}
