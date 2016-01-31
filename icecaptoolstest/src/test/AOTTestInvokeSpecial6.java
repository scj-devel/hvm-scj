package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

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
        VMTest.markResult(test());
    }

    @IcecapCompileMe
    public static boolean test() {
        MyException mex = new MyException(43);
        
        try {
            if (mex.getX() == 43)
            {
                return true;
            }
        } catch (MyException e) {
            if (e == mex)
            {
                return false;
            }
        }
        return true;
    }
}
