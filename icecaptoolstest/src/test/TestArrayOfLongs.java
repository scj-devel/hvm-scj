package test;

import vm.VMTest;

public class TestArrayOfLongs {
    public static void main(String args[])
    {
        VMTest.markResult(test());
    }

    public static boolean test() {
        long[] array = new long[2];
        array[0] = 0x1122334455667788L;
        array[1] = 0x1122334455667788L;
        
        if (array[0] == 0x1122334455667788L)
        {
            if (array[1] == 0x1122334455667788L)
            {
                return false;
            }
        }
        return true;
    }
}
