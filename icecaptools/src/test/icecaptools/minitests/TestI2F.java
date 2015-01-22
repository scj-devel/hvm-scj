package test.icecaptools.minitests;

public class TestI2F {
    public static void main(String[] args) {
        float x = 0;
        int y = 42;

        x = (float) y;
        if (x < 0) {
            return;
        }

        y = -42;
        x = (float) y;
        if (x > 0) {
            return;
        }
    }
}
