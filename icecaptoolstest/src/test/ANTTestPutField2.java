package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class ANTTestPutField2 {

    private static class A {
        boolean f;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        A a = new A();
        boolean failure = test(a);

        VMTest.markResult(failure);
    }

    @IcecapCompileMe
    public static boolean test(A a) {
        a.f = true;
        if (a.f) {
            return false;
        } else {
            return true;
        }
    }
}
