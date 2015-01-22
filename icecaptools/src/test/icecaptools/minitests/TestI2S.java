package test.icecaptools.minitests;

public class TestI2S {
    public static void main(String[] args) {
        short x = 0;
        int y = 42;

        x = (short) y;
        if (x < 0) {
            return;
        }

        y = -42;
        x = (short) y;
        if (x > 0) {
            return;
        }
    }
}
