package test;

public class TestIDIV2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        if (!failed) {
            args = null;
        }
    }

    private static boolean test() {
        int x = 2059771672;
        int y = 100;
        int result = x / y;
        if (result == 20597716) {
            return false;
        } else {
            return true;
        }
    }
}
