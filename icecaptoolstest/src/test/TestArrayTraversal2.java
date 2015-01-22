package test;

import reflect.HeapAccessor;
import reflect.ObjectInfo;
import vm.Address32Bit;

public class TestArrayTraversal2 {

    public static void main(String[] args) {
        boolean failed = test();
        if (!failed) {
            args = null;
        }
    }

    private static class ArrayOverlay extends HeapAccessor {
        @SuppressWarnings("unused")
        short classId;
        short count;
        int data;

        public ArrayOverlay(int address) {
            super(address);
        }

        public void scanToNext() {
            ((Address32Bit) address).address += 4;
        }
    }

    private static boolean test() {
        int[] array = new int[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        int address = ObjectInfo.getAddress(array);

        ArrayOverlay ao = new ArrayOverlay(address);

        int numberOfElements = ao.count;

        int sum1 = 0;
        int sum2 = 0;
        if (numberOfElements == 10) {
            while (numberOfElements > 0) {
                int nextElement;
                nextElement = ao.data;
                sum1 += nextElement;
                devices.Console.println(nextElement);
                ao.scanToNext();
                numberOfElements--;
            }

            for (int i = 0; i < array.length; i++) {
                devices.Console.println(array[i]);
                sum2 += array[i];
            }
            if (sum1 == sum2) {
                return false;
            }
        }
        return true;
    }
}
