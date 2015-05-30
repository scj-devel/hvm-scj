package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class ANTTestPutField3 {

    private static class A {
        byte f;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        A a = new A();
        a.f = 10;
        boolean failure = test(a);

        VMTest.markResult(failure);
    }

    @IcecapCompileMe
    public static boolean test(A a) {
        if (a.f + a.f == 20)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
