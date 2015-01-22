package test;

public class TestMultiArray1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test1();

        failed |= test2();

        if (!failed) {
            args = null;
        }

    }

    public static boolean test2() {
        int[][] matrix = new int[10][10];

        for (byte i = 0; i < 10; i++) {
            for (byte j = 0; j < 10; j++) {
                matrix[i][j] = i + j;
            }
        }

        short sum = 0;

        for (byte i = 0; i < 10; i++) {
            for (byte j = 0; j < 10; j++) {
                sum += matrix[i][j];
            }
        }

        if (sum == 900) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean test1() {
        int[][] matrix = new int[10][10];

        matrix[0][0] = 42;

        int sum = matrix[0][0];

        if (sum == 42) {
            return false;
        } else {
            return true;
        }
    }

}
