package test;

import vm.VMTest;

public class TestOutOfMemory1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VMTest.markResult(test());
	}

	public static boolean test() {

		try {
			while (true) {
				new TestOutOfMemory1();
			}
		} catch (OutOfMemoryError error) {
			return false;
		}
	}
}
