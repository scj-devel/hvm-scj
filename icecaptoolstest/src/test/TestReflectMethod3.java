package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestReflectMethod3 {

    private static class MyException extends Exception {
        private static final long serialVersionUID = 1L;

        public int x;

        public MyException(int x) {
            this.x = x;
        }
    }

    private static class A {

        public void foo() throws MyException {
            throw new MyException(42);
        }

        @SuppressWarnings("unused")
        public void bar() throws Throwable {
            throw new Error();
        }
    }

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException {
        A a = new A();
        Class<? extends A> cl = a.getClass();
        Method m = cl.getMethod("foo", (Class[]) null);

        try {
            a.foo();
        } catch (MyException me1) {
            try {
                m.invoke(a, (Object[]) null);
            } catch (InvocationTargetException ite) {
                MyException me2 = (MyException) ite.getTargetException();
                if (me2.x == 42) {
                    args = null;
                }
            }
        }
    }
}
