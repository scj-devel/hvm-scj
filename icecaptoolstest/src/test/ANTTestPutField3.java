package test;

import icecaptools.IcecapCompileMe;

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

        if (!failure) {
            args = null;
        }
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
