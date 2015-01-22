package test;

public class TestMultiDimentionalArrays {

    public static boolean test() {
        int[][] mda = new int[4][2];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                mda[i][j] = i + j;
            }
        }

        int sum = 0;

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                sum += mda[i][j];
            }
        }

        if (sum == 16) {
            return false;
        }
        return true;

    }

    public static void main(String[] args) {
        boolean failed = test();
        if (!failed) {
            args = null;
        }
    }
}
