package test;

import icecaptools.IcecapCompileMe;

public class TestBug3 {

    private static class Bug3
    {
         private int arrayLength;
         public int[] array;
         
         public Bug3()
         {
             arrayLength = 10;
         }
         
         @IcecapCompileMe
         public void foo(boolean tag)
         {
             array = new int[tag ? arrayLength + 1: arrayLength];
         }
        
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        Bug3 b = new Bug3();
        b.foo(true);
        if (b.array.length == 11)
        {
            b.foo(false);
            
            if (b.array.length == 10)
            {
                args = null;
            }
        }
    }
}
