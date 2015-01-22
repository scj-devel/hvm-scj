package test;

import icecaptools.IcecapCompileMe;

public class ANTTestInvokeVirtual {

    private static class TempClass
    {
        public int x = 0;
        
        public void foo() {
            x++;
        }        
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test(new TempClass());
        if (!failure) {
            args = null;
        }
    }

    @IcecapCompileMe
    public static boolean test(TempClass temp) {
        temp.foo();
        temp.foo();
        temp.foo();
        temp.foo();
        if (temp.x == 4)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
