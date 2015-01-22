package test;

import icecaptools.IcecapCompileMe;

public class ANTTestInvokeSpecial {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String failure = test1(true);
        if (failure != null) {
            failure = test2(true);
            if (failure != null) {
                args = null;
            }
        }
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
