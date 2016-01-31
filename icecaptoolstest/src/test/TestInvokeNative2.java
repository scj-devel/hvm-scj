package test;

import vm.VMTest;

public class TestInvokeNative2 {

    private static class Super {
        public native static int testNativeStatic();
    }

    private static class SubClass extends Super {

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

    public static boolean test() {

        if (SubClass.testNativeStatic() == 42) {
            return false;
        }
        return true;
    }
}
