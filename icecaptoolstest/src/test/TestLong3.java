package test;

import vm.VMTest;

public class TestLong3 {

    public static void main(String args[])
    {
        VMTest.markResult(test());
    }

    public static boolean test() {
        long r = getLong();
        if (r == 0x0123456789ABCDEFL)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static long getLong() {
        return 0x0123456789ABCDEFL;
    }
}
