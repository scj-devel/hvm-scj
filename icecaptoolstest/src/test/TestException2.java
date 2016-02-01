package test;

import vm.VMTest;

public class TestException2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

	public static boolean test() {
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
            return false;
        }
        return true;
	}

    private void foo() throws Exception {
        throw new Exception();
    }
}
