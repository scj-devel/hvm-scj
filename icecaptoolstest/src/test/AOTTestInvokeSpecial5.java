package test;

import icecaptools.IcecapCompileMe;

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
        args = test(args);
    }

    @IcecapCompileMe
    private static String[] test(String[] args) {
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
                       return null;
                   }
               }
        }
        return args;
    }

    private static void foo(int i) throws MyException {
        if (i == 42)
        {
            throw new MyException(i+1);
        }
    }
}
