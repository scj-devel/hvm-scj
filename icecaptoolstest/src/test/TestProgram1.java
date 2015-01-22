package test;

import icecaptools.IcecapCompileMe;

// import devices.Console;

public class TestProgram1 {
    public static void main(String[] args) {
        args = test(args);
    }

    public static String[] test(String[] args) {
        short[] numberSet = new short[20];

        for (byte i = 0; i < numberSet.length; i++) {
            numberSet[i] = (short) (numberSet.length - i);
        }

        quicksort(numberSet, (short)0, (short)(numberSet.length - 1));

        for (byte i = 0; i < numberSet.length - 1; i++) {
            // Console.println(i + ": " + numberSet[i]);
            if (numberSet[i] > numberSet[i + 1]) {
                return args;
            } 
        }
        return null;
    }

    @IcecapCompileMe
    public static void quicksort(short array[], short left, short right) {
        if (array != null) {
            short low = left;
            short high = right;

            if (low >= right) {
                return;
            }

            short pivot = array[low + ((high - low) >> 1)];

            while (low <= high) {

                while (array[low] < pivot) {
                    low++;
                }

                while (array[high] > pivot) {
                    high--;
                }

                if (low <= high) {
                    short temp = array[low];
                    array[low] = array[high];
                    array[high] = temp;

                    low++;
                    high--;
                }
            }

            if (high < low) {
                short T = high;
                high = low;
                low = T;
            }

            if (left < low)
                quicksort(array, left, low);
            if (high < right)
                quicksort(array, (short) (low == left ? low + 1 : low), right);
        }
    }
}
