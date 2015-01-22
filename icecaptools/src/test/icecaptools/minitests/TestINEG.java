package test.icecaptools.minitests;

public class TestINEG {
    public static void main(String[] args) {
        int x = 42;
        x = -x;

        if (x == -42) {
            args = null;
        }
    }
}
