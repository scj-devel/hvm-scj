package test;

import vm.VMTest;

public class TestCheckCast3 {
    
    private static interface Interface1
    {
        void foo();
    }
    
    private static class B
    {
        
    }
    
    private static class A extends B implements Interface1 
    {
        @Override
        public void foo() {
        }        
    }
 
    public static void main(String args[])
    {
        VMTest.markResult(test());
    }

    @SuppressWarnings("unused")
    public static boolean test() {
        Interface1 inter1;
        Object a = new A();
        try
        {
            inter1 = (Interface1) a;
            return false;
        } catch (ClassCastException cce)
        {
            
        }
        return true;
    }
}
