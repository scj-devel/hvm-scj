package test;

import vm.VMTest;

public class TestNewArrayInstance {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test();
        VMTest.markResult(failure);
    }

    public static boolean test() {
        boolean failure = false;

        Class<?> componentType = int.class;

        int[] array = (int[]) java.lang.reflect.Array.newInstance(componentType, 10);
        int sum = 0;
        if (array.length != 10) {
            return true;
        }
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }

        if (sum != 0) {
            return true;
        }

        componentType = boolean.class;

        boolean[] barray = (boolean[]) java.lang.reflect.Array.newInstance(componentType, 10);
        sum = 0;
        if (barray.length != 10) {
            return true;
        }
        for (int i = 0; i < barray.length; i++) {
            sum += (barray[i]? 1: 0);
        }

        if (sum != 0) {
            return true;
        }

        return failure;
    }
}
