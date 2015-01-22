package test;

public class TestInvokeSpecial7 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    private static String[] test(String[] args) {
        TestInvokeSpecial7 obj = getObj();
        try {
            obj.foo(2);
        } catch (NullPointerException npe) {
            return null;
        }

        return args;
    }

    private int foo(int i) {
        return i;
    }

    private static TestInvokeSpecial7 getObj() {
        return null;
    }

}
