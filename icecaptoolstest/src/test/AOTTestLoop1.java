package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class AOTTestLoop1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    @IcecapCompileMe
    public static boolean test() {
        int x = 5;
        for (int i = 0; i < 5; i++) {
            x--;
        }
        if (x == 0) {
            return false;
        } else {
            return true;
        }
    }
}
