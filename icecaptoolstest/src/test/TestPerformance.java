package test;

import icecaptools.IcecapCompileMe;

import javax.realtime.AbsoluteTime;
import javax.realtime.Clock;

public class TestPerformance {

    private static final int RUNS = 5;

    public static void main(String[] args) {
        boolean failed;
        try {
            failed = test();
            if (!failed) {
                args = null;
            }
        } catch (Exception e) {
        }
    }

    private static boolean test() throws Exception {
        return testQuickSort();
    }

    private static boolean testQuickSort() throws Exception {
        short[] numberSet = new short[32000];

        int cTimeSum = 0;
        int javaTimeSum = 0;

        for (int i = 0; i < RUNS; i++) {
            initialize(numberSet);

            Clock rtClock = Clock.getRealtimeClock();
            AbsoluteTime start = rtClock.getTime();

            long millisStart = start.getMilliseconds();

            quicksortC(numberSet);

            AbsoluteTime end = rtClock.getTime();

            long millisEnd = end.getMilliseconds();

            long diff = millisEnd - millisStart;

            verifySorted(numberSet);

            cTimeSum += (int) diff;
        }

        for (int i = 0; i < RUNS; i++) {
            initialize(numberSet);

            Clock rtClock = Clock.getRealtimeClock();
            AbsoluteTime start = rtClock.getTime();

            long millisStart = start.getMilliseconds();

            quicksortJava(numberSet, (short) 0, (short) (numberSet.length - 1));

            AbsoluteTime end = rtClock.getTime();

            long millisEnd = end.getMilliseconds();

            long diff = millisEnd - millisStart;

            verifySorted(numberSet);

            javaTimeSum += (int) diff;
        }

        devices.Console.println("cTimeSum = " + cTimeSum);
        devices.Console.println("javaTimeSum = " + javaTimeSum);
        
        if (javaTimeSum > (cTimeSum + cTimeSum))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private static native void quicksortC(short[] numberSet);

    private static void initialize(short[] numberSet) {
        for (short i = 0; i < numberSet.length; i++) {
            numberSet[i] = (short) (numberSet.length - i);
        }
    }

    private static void verifySorted(short[] numberSet) throws Exception {
        for (short i = 0; i < numberSet.length - 1; i++) {
            if (numberSet[i] > numberSet[i + 1]) {
                throw new Exception();
            }
        }
    }

    @IcecapCompileMe
    private static void quicksortJava(short array[], short left, short right) {
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
                quicksortJava(array, left, low);
            if (high < right)
                quicksortJava(array, (short) (low == left ? low + 1 : low), right);
        }
    }
}
