package test;

public class TestInvoke2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);

    }

	public static String[] test(String[] args) {
		TestInvoke2 test = new TestInvoke2();
        int res = test.foo();
        if (res == 42) {
            return null;
        }
        return args;
	}

    private int foo() {
        return 42;

    }

}
