package test;

public class TestInvoke4 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		TestInvoke4 test = new TestInvoke4();
        test.foo(32);
        int res = 42;
        if (res == 42) {
            return null;
        }
        return args;
	}

    private void foo(int x) {
        ;
    }
}
