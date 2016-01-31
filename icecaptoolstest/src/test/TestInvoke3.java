package test;

import vm.VMTest;

public class TestInvoke3 {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

	public static boolean test() {
		TestInvoke3 test = new TestInvoke3();
        int res = test.foo(32);
        if (res == 42) {
            return false;
        }
        return true;
	}

    private int foo(int x) {
        return x + 10;
    }
}
