package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestReflectMethod1 {

    private static class A
    {
        public int foo(int x)
        {
            return x + 10;
        }
    }
    
    private static class B extends A
    {
        
    }
    
    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        B b = new B();
        Class<? extends B> cl = b.getClass();
        Class<Integer> type = int.class;
        @SuppressWarnings("rawtypes")
        Class[] types = new Class[1];
        types[0] = type;
        Method m = cl.getMethod("foo", types);
        Integer x = (Integer) m.invoke(b, new Integer(32));
        if (x == b.foo(32)) {
            args = null;
        }
    }
}
