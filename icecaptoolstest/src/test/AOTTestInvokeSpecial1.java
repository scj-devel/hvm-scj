package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class AOTTestInvokeSpecial1 {

    private static class MyWeirdClass
    {
        public MyWeirdClass() throws Exception
        {
            throw new Exception();
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test() );
    }

    @IcecapCompileMe
    private static boolean test() {
        try {
            @SuppressWarnings("unused")
            MyWeirdClass instance = new MyWeirdClass();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
