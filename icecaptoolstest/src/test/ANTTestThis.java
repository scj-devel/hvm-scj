package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class ANTTestThis {

    public int x;
    
    @IcecapCompileMe
    public boolean foo()
    {
        if (x == 42)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    @IcecapCompileMe
    public boolean bar()
    {
        x = 42;
        return foo();
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        ANTTestThis obj = new ANTTestThis();
        boolean failure = true;
        if (obj.bar())
        {
        	failure = false;
        }
        VMTest.markResult(failure);
    }
}
