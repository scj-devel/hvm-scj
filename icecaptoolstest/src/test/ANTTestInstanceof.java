package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class ANTTestInstanceof {

    private static class A
    {
        
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test(new A());
        VMTest.markResult(failure);
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
