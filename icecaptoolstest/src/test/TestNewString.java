package test;

import vm.VMTest;

public class TestNewString {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        VMTest.markResult(failed);
    }

    private static boolean test() {
        byte[] chars;
        chars = new byte[5];
        chars[1] = 'h';
        chars[2] = 'e';
        chars[3] = 'j';

        String str = new String(chars, 1, 3);

        if (str.equals("hej")) {
            return false;
        } else {
            return true;
        }
    }

}
