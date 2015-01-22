package test;

import icecaptools.IcecapCompileMe;

public class AOTTestInvokeSpecial1 {

    private static class MyWeirdClass
    {
        public MyWeirdClass() throws Exception
        {
            throw new Exception();
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    @IcecapCompileMe
    private static String[] test(String[] args) {
        try {
            @SuppressWarnings("unused")
            MyWeirdClass instance = new MyWeirdClass();
        } catch (Exception e) {
            return null;
        }
        return args;
    }
}
