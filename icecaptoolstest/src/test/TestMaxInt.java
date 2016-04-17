package test;

import vm.VMTest;

public class TestMaxInt {

	public static void main(String[] args) {
		VMTest.markResult(test());
	}

	public static boolean test() {
		int x = Integer.MIN_VALUE;

		if (x == 0x80000000) {
			return false;
		} else {
			return true;
		}
	}
}
