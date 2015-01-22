package test;

import icecaptools.IcecapCompileMe;

public class TestRegAllocator1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    @IcecapCompileMe
    public static String[] test(String[] args) {
        byte x, y;
        x = 10;
        y = 12;
        if (x == y)
        {
            return args;
        }
        else
        {
            return null;    
        }
    }

}
