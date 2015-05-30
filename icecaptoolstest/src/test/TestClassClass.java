package test;

import vm.VMTest;

public class TestClassClass {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    public static boolean test() {
        Class<Object[]> clazz = Object[].class;
        Class<?> type = clazz.getComponentType();
        if (type == Object.class) {
            @SuppressWarnings("rawtypes")
            Class<Class[]> clazz1 = Class[].class;
            type = clazz1.getComponentType();
            if (type == Class.class) {
                return false;
            }
        }
        return true;
    }
}
