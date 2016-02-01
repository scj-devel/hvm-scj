package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class ANTTestInvokeSpecial {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = true;
    	String failure = test1(true);
        if (failure != null) {
            failure = test2(true);
            if (failure != null) {
            	failed = false;
            }
        }
        VMTest.markResult(failed);
    }

    @IcecapCompileMe
    public static String test1(boolean b) {
        String str = new String();
        return str;
    }
    
    @IcecapCompileMe
    public static String test2(boolean b) {
        String str;
        
        if (b)
        {
            str = new String();
        }
        else
        {
            str = null;
        }
        return str;
    }

}
