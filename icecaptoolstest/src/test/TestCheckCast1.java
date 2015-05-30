package test;

import vm.VMTest;

public class TestCheckCast1 {

    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    @SuppressWarnings("unused")
    public static boolean test() {
        TestIf1 testIf1 = new TestIf1();
        Object testIf2 = new TestIf2();

        try {
            testIf1 = (TestIf1) testIf2;
            testIf1 = null;

        } catch (ClassCastException ex) {
            
            return false;
        }
        return true;
    }
}
