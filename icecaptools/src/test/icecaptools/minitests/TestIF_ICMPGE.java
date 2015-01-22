package test.icecaptools.minitests;

public class TestIF_ICMPGE {
    public static void main(String[] args) {
        int x = 42;
        int y = 16;

        if (x >= y) {
            x = 1;
            args = null;
        } else {
            x = 0;
        }
    }
}