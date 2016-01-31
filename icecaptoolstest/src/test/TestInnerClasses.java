package test;

import vm.VMTest;

public class TestInnerClasses {

	public static class A {

		public int foo() {
			return 42;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VMTest.markResult(test());
	}

	public static boolean test() {
		A a = new A();
		if (a != null) {
			int x = a.foo();
			if (x == 42) {
				return false;
			}
		}
		return true;
	}
}
