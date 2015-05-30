package test;

import vm.VMTest;

public class TestIREM {
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    private static boolean test() {
        int x = 13;
        int y = 3;
        x = x % y;

        if (x == 1) {
            x = 13;
            y = 0;

            try {
                x = x % 0;
            } catch (ArithmeticException ex) {
                return false;
            }
        }
        return true;
    }
}
