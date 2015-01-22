package test;

public class TestInvoke3 {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	args = test(args);
    }

	public static String[] test(String[] args) {
		TestInvoke3 test = new TestInvoke3();
        int res = test.foo(32);
        if (res == 42) {
            return null;
        }
        return args;
	}

    private int foo(int x) {
        return x + 10;
    }
}
