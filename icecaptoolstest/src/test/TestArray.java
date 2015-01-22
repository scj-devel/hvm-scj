package test;

import icecaptools.IcecapCompileMe;

public class TestArray {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        if (!failed) {
            args = null;
        }
    }

    @SuppressWarnings("unused")
    @IcecapCompileMe
    public static boolean test() {
        byte[] array1 = new byte[1];
        byte[] array2 = new byte[1];

        array1[0] = (byte) 0xff;
        array2[0] = (byte) 0xff;

        if (array1.length == 1) {
            if (array1[0] == (byte) 0xff) {
                if (array2.length == 1) {
                    if (array2[0] == (byte) 0xff) {
                        short[] array3 = new short[10];
                        for (int i = 0; i < 10; i++) {
                            array3[i] = 1;
                        }
                        int sum = 0;
                        for (int i = 0; i < array3.length; i++) {
                            sum += array3[i];
                        }
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
