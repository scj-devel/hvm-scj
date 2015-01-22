package test;

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
        args = test(args);
    }

    public static String[] test(String[] args) {

        if (SubClass.testNativeStatic() == 42) {
            return null;
        }
        return args;
    }
}
