package test;

public class TestObjectToString {

    private static class A {
        @SuppressWarnings("unused")
        public int x;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    public static String[] test(String[] args) {
        A a = new A();
        String str = a.toString();
        if (str != null) {
            if (str.length() > 0) {
                return null;
            }
        }
        return args;
    }
}
