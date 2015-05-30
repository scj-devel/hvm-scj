package test;

import vm.VMTest;

public class TestWide {
	private static final int XXX = 479;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int x = 0;

		x = x + XXX;

		if (x == XXX) {
			VMTest.markResult(false);
		}
	}
}
