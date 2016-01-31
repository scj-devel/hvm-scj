package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

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
    	VMTest.markResult(test());
    }

    @IcecapCompileMe
    public static boolean test() {
        try {
            foo(32);
        } catch (MyException ex) {
            if (ex.getX() == 42) {
                return false;
            }
        }
        return true;
    }

    @IcecapCompileMe
    public static void foo(int i) throws MyException {
        if (i == 32) {
            throw new MyException(32 + 10);
        }
    }
}
