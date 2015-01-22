package test;

public class TestMultiArray2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test1();

        failed |= test2();

        if (!failed) {
            args = null;
            //System.out.println ("Not failed");
            devices.Console.println ("Not failed");
        }
        //System.out.println ("End");
        devices.Console.println ("End");

    }

    public static boolean test2() {
        short[][] matrix = new short[10][10];

        for (byte i = 0; i < 10; i++) {
            for (byte j = 0; j < 10; j++) {
                matrix[i][j] = 2;
            }
        }

        short sum = 0;

        for (byte i = 0; i < 10; i++) {
            for (byte j = 0; j < 10; j++) {
                sum += matrix[i][j];
            }
        }

        if (sum == 200) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean test1() {
        short[][] matrix = new short[10][10];

        matrix[0][0] = 42;

        short sum = matrix[0][0];

        if (sum == 42) {
            return false;
        } else {
            return true;
        }
    }
}
