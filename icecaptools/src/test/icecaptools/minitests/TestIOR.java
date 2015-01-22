package test.icecaptools.minitests;

public class TestIOR {
    public static void main(String[] args) {
        int x = 42;
        int y = 21;
        x = x | y;

        if (x == 63) {
            args = null;
        }
    }
}
