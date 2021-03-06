package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class ANTTestArray {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    @IcecapCompileMe
    public static boolean test() {
        byte[] array = new byte[10];

        for (byte index = 0; index < array.length; index++) {
            array[index] = index;
        }

        byte max = 0;
        byte index = 0;
        while (index < array.length) {
            if (array[index] > max) {
                max = array[index];
            }
            index++;
        }

        if (max == array.length - 1) {
            return false;
        } else {
            return true;
        }
    }
}
