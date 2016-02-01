package test;

import vm.VMTest;

public class TestAssert {

    public static void main(String[] args) {
        VMTest.markResult(testIt());
    }

    public static boolean testIt() {
        try {
            assert testThrow();
            return true;
        } catch (java.lang.AssertionError ae) {
            try {
                assert testNotThrow();
                return false;
            } catch (java.lang.AssertionError ae1) {
                return true;
            }
        }
    }

    private static boolean testNotThrow() {
        return true;
    }

    private static boolean testThrow() {
        return false;
    }

}
