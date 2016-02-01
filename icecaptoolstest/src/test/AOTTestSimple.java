package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class AOTTestSimple {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    @IcecapCompileMe
    public static boolean test() {
        return false;
    }
}
