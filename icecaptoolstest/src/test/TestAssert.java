package test;

public class TestAssert {

    public static void main(String[] args) {

        args = testIt(args);

    }

    public static String[] testIt(String[] args) {
        try {
            assert testThrow();
            return args;
        } catch (java.lang.AssertionError ae) {
            try {
                assert testNotThrow();
                return null;
            } catch (java.lang.AssertionError ae1) {
                return args;
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
