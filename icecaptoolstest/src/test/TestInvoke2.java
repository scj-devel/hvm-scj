package test;

import vm.VMTest;

public class TestInvoke2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

	public static boolean test() {
		TestInvoke2 test = new TestInvoke2();
        int res = test.foo();
        if (res == 42) {
            return false;
        }
        return true;
	}

    private int foo() {
        return 42;

    }

}
