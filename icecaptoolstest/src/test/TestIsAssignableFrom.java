package test;

import vm.VMTest;

public class TestIsAssignableFrom {

	public static void main(String[] args) {
		VMTest.markResult(test());
	}

	private static class A {
		int x;
	}

	private static class B extends A {
		int y;
	}

	private static boolean test() {
		if (B.class.isAssignableFrom(A.class)) {
			return true;
		}

		if (A.class.isAssignableFrom(B.class)) {
			return false;
		}

		return true;
	}

}
