package test;

import icecaptools.IcecapCompileMe;

public class TestAnnotator1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    private static String[] test(String[] args) {
        quicksort(10, 10);
        return null;
    }

    @IcecapCompileMe
    public static void quicksort(int left, int right) {
        int low = left;
        int high = right;

        if (left < low)
            quicksort(left, low);
        if (high < right)
            quicksort(low == left ? low + 1 : low, right);
    }

}
