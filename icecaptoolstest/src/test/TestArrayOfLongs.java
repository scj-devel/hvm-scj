package test;

public class TestArrayOfLongs {
    public static void main(String args[])
    {
        args = test(args);
    }

    public static String[] test(String args[]) {
        long[] array = new long[2];
        array[0] = 0x1122334455667788L;
        array[1] = 0x1122334455667788L;
        
        if (array[0] == 0x1122334455667788L)
        {
            if (array[1] == 0x1122334455667788L)
            {
                return null;
            }
        }
        return args;
    }
}
