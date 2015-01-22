package test;


public class TestGetSuperClass {

    private static class A {
        @SuppressWarnings("unused")
        public int foo(int x) {
            return x + 10;
        }
    }

    private static class B extends A {

    }

    public static void main(String[] args) {
       B b = new B();
       Class<?> cl = b.getClass();
       if (cl == B.class)
       {
           cl = cl.getSuperclass();
           if (cl == A.class)
           {
               cl = cl.getSuperclass();
               if (cl == Object.class)
               {
                   args = null;
               }
           }
       }
    }
}
