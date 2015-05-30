package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class AOTTestInvokeSpecial5 {

    @SuppressWarnings("serial")
    private static class MyException extends Exception
    {
        int x;
        
        MyException(int x)
        {
            this.x = x;
        }

        public int getX() {
            return x;
        }
        
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    @IcecapCompileMe
    private static boolean test() {
        try
        {
            foo(42);
        }
        catch (MyException ex)
        {
               if (ex != null)
               {
                   if (ex.getX() == 43)
                   {
                       return false;
                   }
               }
        }
        return true;
    }

    private static void foo(int i) throws MyException {
        if (i == 42)
        {
            throw new MyException(i+1);
        }
    }
}
