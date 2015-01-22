package test.icecaptools.minitests;

public class TestIF_ICMPLT {
    public static void main(String[] args) {
        int x = 42;
        int y = 16;

        if (x < y) {
            x = 0;
        } else {
            x = 1;
            args = null;
        }
    }
}