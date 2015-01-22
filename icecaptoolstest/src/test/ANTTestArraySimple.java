package test;

import icecaptools.IcecapCompileMe;

public class ANTTestArraySimple {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        if (!failed) {
            args = null;
        }
    }

    @IcecapCompileMe
    public static boolean test() {
        byte[] array = new byte[10];
        for (byte x = 0; x < array.length; x++)
        {
            array[x] = x;
        }
        return false;
    }
}
