package test;

import icecaptools.IcecapCompileMe;

public class AOTTestException3 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args); 
        
    }

    @IcecapCompileMe
    private static String[] test(String[] args) {
        try {
            test();
        } catch (Exception ex) {
            return null;
        }
        return args;
    }

    public static void test() throws Exception {
        throw new Exception();
    }
}
