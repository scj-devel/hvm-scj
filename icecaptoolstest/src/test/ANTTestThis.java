package test;

import icecaptools.IcecapCompileMe;

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
        if (obj.bar())
        {
            args = null;
        }
    }
}
