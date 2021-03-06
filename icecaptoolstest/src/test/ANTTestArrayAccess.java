package test;

import vm.VMTest;

public class ANTTestArrayAccess {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test(new int[10]);
        try {
            failure |= test(null);
            failure = true;
        } catch (NullPointerException e) {

        }
        VMTest.markResult(failure);
    }

    @SuppressWarnings("null")
    private static boolean test(int[] array) {
        if (array != null) {
            if (array[0] == 0) {
                return false;
            }
        } else {
            if (array[0] == 0) {
                return true;
            }
        }
        return true;
    }
}
