package test;

public class TestIREM {
    public static void main(String[] args) {
        args = test(args);
    }

    private static String[] test(String[] args) {
        int x = 13;
        int y = 3;
        x = x % y;

        if (x == 1) {
            x = 13;
            y = 0;

            try {
                x = x % 0;
            } catch (ArithmeticException ex) {
                return null;
            }
        }
        return args;
    }
}
