package test;

import icecaptools.IcecapCompileMe;

public class ANTTestInstanceof {

    private static class A
    {
        
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test(new A());
        if (!failure) {
            args = null;
        }
    }

    @IcecapCompileMe
    public static boolean test(Object obj) {
        if (obj instanceof A)
        {
            return false;
        }
        else
        {
            return true;
        }
    }   
}
