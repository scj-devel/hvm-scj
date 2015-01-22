package test;

import icecaptools.IcecapCompileMe;

public class AOTTestException1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            test();
        } catch (Exception ex) {
            args = null;
        }
    }

    @IcecapCompileMe
    public static void test() throws Exception {
        throw new Exception();
    }

}
