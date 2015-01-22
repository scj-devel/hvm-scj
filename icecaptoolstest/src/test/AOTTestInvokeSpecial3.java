package test;

import icecaptools.IcecapCompileMe;

public class AOTTestInvokeSpecial3 {

    @SuppressWarnings("serial")
    private static class MyException extends Exception {
        private int x;

        public MyException(int x) {
            this.x = x;
        }

        public int getX() {
            return x;
        }

    }

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
        } catch (MyException ex) {
            if (ex.getX() == 42) {
                return null;
            }
        }
        return args;
    }

    @IcecapCompileMe
    public static void foo(int i) throws MyException {
        if (i == 32) {
            throw new MyException(32 + 10);
        }
    }
}
