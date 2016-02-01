package test;

import vm.VMTest;

public class TestInvokeStatic1 {
    public static void main(String args[]) {
        try {
            args = test(args);
        } catch (Exception e) {
            VMTest.markResult(false);
        }
    }

    public static String[] test(String args[]) throws Exception {
        static1();
        return args;
    }

    public static void static1() throws Exception {
        throw new Exception("hello");
    }
}
