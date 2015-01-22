package test.icecaptools.minitests;

public class TestI2C {
    public static void main(String[] args) {
        char x = 0;
        int y = 42;

        x = (char) y;
        if (x < 0) {
            return;
        }

        y = -42;
        x = (char) y;
        if (x > 0) {
            return;
        }
    }

}
