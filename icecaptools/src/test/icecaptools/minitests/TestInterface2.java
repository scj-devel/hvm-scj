package test.icecaptools.minitests;

public class TestInterface2 {

    private static interface Interface1 {
        void foo();
    }

    @SuppressWarnings("unused")
    private static class Impl implements Interface1
    {
        @Override
        public void foo() {
        }
        
    }
    
    public static void main(String args[]) {
        method(null);
        args = null;
    }

    private static void method(Interface1 if1) {
        if (if1 != null) {
            if1.foo();
        }
    }
}
