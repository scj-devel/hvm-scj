package test;

import icecaptools.IcecapCompileMe;

public class AOTTestInvokeSpecial6 {

    @SuppressWarnings("serial")
    private static class MyException extends Exception {
        int x;

        MyException(int x) {
            this.x = x;
        }

        public int getX() throws MyException {
            if (x == 43) {
                throw this;
            }
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
        MyException mex = new MyException(43);
        
        try {
            if (mex.getX() == 43)
            {
                return args;
            }
        } catch (MyException e) {
            if (e == mex)
            {
                return null;
            }
        }
        return args;
    }
}
