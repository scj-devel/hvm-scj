package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class AOTTestException3 {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

    @IcecapCompileMe
    private static boolean test() {
        try {
            test1();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static void test1() throws Exception {
        throw new Exception();
    }
}
