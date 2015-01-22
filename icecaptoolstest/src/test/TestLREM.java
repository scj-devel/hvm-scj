package test;

public class TestLREM {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);

    }

    private static String[] test(String[] args) {
        long x = 0x1ffffffffL;
        long y = 0x23L;

        long expected = x % y;

        long temp = x / y;
        long actual = x - (temp * y);

        if (expected == actual) {
            x = 0x1ffffffffL;
            y = 0x0L;

            try {
                expected = x % y;
            } catch (ArithmeticException e) {
                return null;
            }
        }
        return args;
    }

}
