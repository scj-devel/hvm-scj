package test;

import icecaptools.IcecapCompileMe;

public class ANTTestArrayLength {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test();
        if (!failure) {
            args = null;
        }
    }

    @IcecapCompileMe
    public static boolean test() {
        byte[] array = new byte[1];
        if (array.length == 1) {
            return false;
        } else {
            return true;
        }
    }
}
