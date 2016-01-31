package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import vm.VMTest;

public class TestReflectMethod2 {

    public int foo(int x) {
        return x + 10;
    }

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TestReflectMethod2 trm = new TestReflectMethod2();
        Class<? extends TestReflectMethod2> cl = trm.getClass();
        Class<Integer> type = int.class;
        @SuppressWarnings("rawtypes")
        Class[] types = new Class[1];
        types[0] = type;
        Method m = cl.getMethod("foo", types);
        Integer x = (Integer) m.invoke(trm, new Integer(32));
        if (x == trm.foo(32)) {
            try {
                m = cl.getMethod("nonexistant", types);
            } catch (NoSuchMethodException e) {
            	VMTest.markResult(false);
            }
        }
    }
}
