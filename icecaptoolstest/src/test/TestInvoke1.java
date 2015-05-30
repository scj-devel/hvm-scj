package test;

import vm.VMTest;

public class TestInvoke1 {
    @SuppressWarnings("unused")
    private int field;

    public static void main(String args[]) {
        VMTest.markResult(test());
    }

	public static boolean test() {
		TestInvoke1 test = new TestInvoke1();
        test.foo();
        return false;
	}

    private void foo() {
    }
}
