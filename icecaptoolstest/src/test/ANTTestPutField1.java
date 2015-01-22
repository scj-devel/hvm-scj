package test;

import icecaptools.IcecapCompileMe;

public class ANTTestPutField1 {

    private static class A 
    {
        boolean f;
        byte x;
        short y;
        int h;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        A a = new A();
        boolean failure = test(a);

        if (!failure)
        {
            args = null;
        }
    }

    @IcecapCompileMe
    public static boolean test(A a) {
        a.x = 42;
        a.y = 42;
        a.h = 42;
        a.f = true;
        if (a.x + a.y + a.h == 42 * 3)
        {
            if (a.f)
            {
                return false;
            }
        }
        return true;
    }
}
