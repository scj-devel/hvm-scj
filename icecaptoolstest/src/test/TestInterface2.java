package test;

import vm.VMTest;

public class TestInterface2 {

    private interface ISuper
    {
        int foo();
    }
    
    private interface ISub extends ISuper
    {
        int bar();
    }
    
    private static class Sub implements ISub
    {

        @Override
        public int bar() {
            return 0;
        }

        @Override
        public int foo() {
            return 42;
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

    public static boolean test() {
        ISub sub = new Sub();
        if (sub.foo() == 42)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
