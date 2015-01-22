package test;

public class TestException2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		int result = 0;
        TestException2 test = new TestException2();
        try {
            result++;
            if (result == 1) {
                test.foo();
            }
            result++;
        } catch (Exception e) {
            result++;
        }
        if (result == 2) {
            return null;
        }
        return args;
	}

    private void foo() throws Exception {
        throw new Exception();
    }
}
