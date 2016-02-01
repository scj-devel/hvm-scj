package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class AOTTestException1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = true;
    	try {
            test();
        } catch (Exception ex) {
        	failure = false;
        }
    	VMTest.markResult(failure);
    }

    @IcecapCompileMe
    public static void test() throws Exception {
        throw new Exception();
    }

}
