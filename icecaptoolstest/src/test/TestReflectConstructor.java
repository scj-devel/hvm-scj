package test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TestReflectConstructor {

    public static class A {
        public int x;

        public A(int x) {
            this.x = x;
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        @SuppressWarnings("rawtypes")
        Class cls = Class.forName("test.TestReflectConstructor$A");

        @SuppressWarnings("rawtypes")
        Class[] carray = new Class[1];

        carray[0] = int.class;

        @SuppressWarnings({ "rawtypes", "unchecked" })
        Constructor constructor = cls.getConstructor(carray);

        Object[] initargs = new Object[1];
        initargs[0] = new Integer(42);

        A a = (A) constructor.newInstance(initargs);

        A anotherA = new A(42);

        if (a.x == 42) {
            if (anotherA != a) {
                args = null;
            }
        }
    }
}
