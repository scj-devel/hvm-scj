package test;

public class TestLong3 {

    public static void main(String args[])
    {
        args = test(args);
    }

    public static String[] test(String args[]) {
        long r = getLong();
        if (r == 0x0123456789ABCDEFL)
        {
            return null;
        }
        else
        {
            return args;
        }
    }

    public static long getLong() {
        return 0x0123456789ABCDEFL;
    }
}
