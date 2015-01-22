package test;

import icecaptools.IcecapVolatile;

public class TestVolatile7 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test1();
        failure |= test2();
        failure |= test3();
        if (!failure) {
            args = null;
        }
    }

    @IcecapVolatile("x")
    private static boolean test1() {
        int x = 0;
        while (x < 10) {
            x++;
        }
        return false;
    }

    @IcecapVolatile("x, y")
    private static boolean test2() {
        int x = 0;
        int y;
        while (x < 10) {
            x++;
            y = 0;
            while (y < 10) {
                y++;
            }
        }
        return false;
    }

    @IcecapVolatile("")
    private static boolean test3() {
        return false;
    }

}
