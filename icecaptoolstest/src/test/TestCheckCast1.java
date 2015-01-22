package test;

public class TestCheckCast1 {

    public static void main(String[] args) {
        args = test(args);
    }

    @SuppressWarnings("unused")
    public static String[] test(String[] args) {
        TestIf1 testIf1 = new TestIf1();
        Object testIf2 = new TestIf2();

        try {
            testIf1 = (TestIf1) testIf2;
            testIf1 = null;

        } catch (ClassCastException ex) {
            
            return null;
        }
        return args;
    }
}
