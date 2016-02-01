package test;

import vm.VMTest;

public class TestInvoke4 {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

	public static boolean test() {
		TestInvoke4 test = new TestInvoke4();
        test.foo(32);
        int res = 42;
        if (res == 42) {
            return false;
        }
        return true;
	}

    private void foo(int x) {
        ;
    }
}
