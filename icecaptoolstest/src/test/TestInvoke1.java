package test;


public class TestInvoke1 {
    @SuppressWarnings("unused")
    private int field;

    public static void main(String args[]) {
        args = test(args);
    }

	public static String[] test(String args[]) {
		TestInvoke1 test = new TestInvoke1();
        test.foo();
        return null;
	}

    private void foo() {
    }
}
