package test;

import icecaptools.IcecapCompileMe;

public class AOTTestInterface1 {

    private interface Interface1 {
        public void foo();

        public int bar();
    }

    private interface Interface2 {
        public int baz();
    }

    private interface Interface3 extends Interface4 {
        public int baf();
    }

    private interface Interface4 {
        public int caf();
    }
    
    private static class A implements Interface1 {
        @Override
        public void foo() {
        }

        @Override
        public int bar() {
            return 32;
        }
    }

    private static class B implements Interface1, Interface2 {
        @Override
        public int baz() {
            return 10;
        }

        @Override
        public void foo() {

        }

        @Override
        public int bar() {
            return 22;
        }
    }

    private static class C implements Interface1, Interface2, Interface3 {

        @Override
        public int baf() {
            return 20;
        }

        @Override
        public int baz() {
            return 2;
        }

        @Override
        public void foo() {
        }

        @Override
        public int bar() {
            return 52;
        }

        @Override
        public int caf() {
            return 14;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    @IcecapCompileMe
    public static String[] test(String[] args) {
        Interface1 a = new A();
        Interface1 b = new B();
        Interface2 b2 = (B) b;
        Interface1 c = new C();
        Interface2 c2 = (C) c;
        Interface3 c3 = (C) c;
        
        int x = a.bar() + b.bar() + b2.baz() + c.bar() + c2.baz() + c3.baf() + ((Interface4)c).caf();

        if (x == 152) {
           return null;
        }
        return args;
    }
}
