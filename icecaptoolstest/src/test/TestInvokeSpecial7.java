package test;

import vm.VMTest;

public class TestInvokeSpecial7 {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

    private static boolean test() {
        TestInvokeSpecial7 obj = getObj();
        try {
            obj.foo(2);
        } catch (NullPointerException npe) {
            return false;
        }

        return true;
    }

    private int foo(int i) {
        return i;
    }

    private static TestInvokeSpecial7 getObj() {
        return null;
    }

}
