package test;

import vm.VMTest;

public class TestObjectToString {

    private static class A {
        @SuppressWarnings("unused")
        public int x;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    public static boolean test() {
        A a = new A();
        String str = a.toString();
        if (str != null) {
            if (str.length() > 0) {
                return false;
            }
        }
        return true;
    }
}
