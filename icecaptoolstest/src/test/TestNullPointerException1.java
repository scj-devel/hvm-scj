package test;

import vm.VMTest;

public class TestNullPointerException1 {

    private static class Super {
        @SuppressWarnings("unused")
        public int x;
    }

    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    @SuppressWarnings("null")
    public static boolean test() {
        Super a = null;
        try {
            a.x = 42;
        } catch (NullPointerException ne1) {
            StringBuffer buffer = getStringBuffer();
            try {
                if (buffer.length() > 0) {
                    return true;
                } else {
                    return true;
                }
            } catch (NullPointerException ne) {
                return false;
            }
        }

        return true;
    }

    private static StringBuffer getStringBuffer() {
        return null;
    }

}
