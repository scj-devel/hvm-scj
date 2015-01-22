package test;

import icecaptools.IcecapCompileMe;

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

        if (!failure) {
            args = null;
        }
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
