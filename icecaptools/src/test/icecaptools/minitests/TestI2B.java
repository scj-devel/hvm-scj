package test.icecaptools.minitests;

public class TestI2B {
    public static void main(String args[]) {
        byte x = 0;
        int y = 42;

        x = (byte) y;
        if (x < 0) {
            return;
        }

        y = -42;
        x = (byte) y;
        if (x > 0) {
            return;
        }

        y = 32763;
        x = (byte) y; // JVM Specs 2.6.3 Narrowing Primitive Conversions; should
                      // produce value of -5
    }
}
