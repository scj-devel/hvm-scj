package test.icecaptools.minitests;

public class TestI2D {
    public static void main(String[] args) {
        double x = 0;
        int y = 42;

        x = (double) y;
        if (x < 0) {
            return;
        }

        y = -42;
        x = (double) y;
        if (x > 0) {
            return;
        }
    }
}
