package test.icecaptools.minitests;

public class TestI2L {
    public static void main(String[] args) {
        long x = 0;
        int y = 42;

        x = (long) y;
        if (x < 0) {
            return;
        }

        y = -42;
        x = (long) y;
        if (x > 0) {
            return;
        }
    }
}
