package test.icecaptools.minitests;

public class TestNEW {

    @SuppressWarnings("unused")
    private int x;

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        TestNEW obj = new TestNEW();
        int res;

        if (obj == null) {
            res = 0;
        } else {
            res = 42;
            args = null;
        }

    }
}
