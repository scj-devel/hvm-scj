package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import reflect.ReflectionUtils;
import vm.Memory;

public class TestReflectMethod4 {

    private static class A {
        public int foo(int x) {
            return x + 10;
        }
    }

    private static class B extends A {

    }

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        B b = new B();
        Class<? extends B> cl = b.getClass();
        Class<Integer> type = int.class;
        Class<?>[] types = new Class<?>[1];
        types[1] = type;
        Method m = ReflectionUtils.getMethod(cl, "foo", types);

        if (m != null) {
            Memory mainArea = Memory.getHeapArea();

            int waterMark = mainArea.consumedMemory();
            m = ReflectionUtils.getMethod(cl, "foo", types);
            int consumed = mainArea.consumedMemory() - waterMark;
            if (consumed == 0) {
                short index = ReflectionUtils.getMethodIndex(cl, "foo", types);
                if (index == ReflectionUtils.readMethodIndex(m)) {
                    Integer x = (Integer) m.invoke(b, new Integer(32));
                    if (x == b.foo(32)) {
                        args = null;
                    }
                }
            } else {
                devices.Console.println("Leaking " + consumed + " bytes");
            }
        }
    }
}
