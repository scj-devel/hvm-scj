package test;

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
        args = test(args);
    }

    @SuppressWarnings("unused")
    public static String[] test(String[] args) {
        Interface1 inter1;
        Object a = new A();
        try
        {
            inter1 = (Interface1) a;
            return null;
        } catch (ClassCastException cce)
        {
            
        }
        return args;
    }
}
