package test;

import icecaptools.IcecapCompileMe;

public class TestInterface3 {

    private static interface Interface1 {
        void foo();
    }

    private static interface Interface2 {
        void bar();
    }
    
    private static class Implementor implements Interface1, Interface2 {

        @Override
        public void foo() {

        }

        @Override
        public void bar() {
            
        }

    }

    public static void main(String[] args) {
        if (!test()) {
            args = null;
        }
    }

    public static boolean test() {
        Implementor a = new Implementor();

        return callIt(a);
    }

    @IcecapCompileMe
    public static boolean callIt(Object a) {
        try {
            @SuppressWarnings("unused")
            Interface2 thisA =  (Interface2) a;
            return false;
        } catch (ClassCastException cce) {
            return true;
        }
    }
}
