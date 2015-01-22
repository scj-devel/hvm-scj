package test;

public class TestNullPointerException1 {

    private static class Super {
        @SuppressWarnings("unused")
        public int x;
    }

    public static void main(String[] args) {
        args = test(args);
    }

    @SuppressWarnings("null")
    public static String[] test(String[] args) {
        Super a = null;
        try {
            a.x = 42;
        } catch (NullPointerException ne1) {
            StringBuffer buffer = getStringBuffer();
            try {
                if (buffer.length() > 0) {
                    return args;
                } else {
                    return args;
                }
            } catch (NullPointerException ne) {
                return null;
            }
        }

        return args;
    }

    private static StringBuffer getStringBuffer() {
        return null;
    }

}
