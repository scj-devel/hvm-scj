package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class TestAnnotator1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    private static boolean test() {
        quicksort(10, 10);
        return false;
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
