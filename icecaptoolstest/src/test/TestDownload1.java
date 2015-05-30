package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class TestDownload1 {

	public static void main(String[] args) {
		int sum = test1() + test2();
		VMTest.markResult(sum != 42);
	}

	@IcecapCompileMe
	private static int test2() {
		return 41;
	}

	private static int test1() {
		return 1;
	}
}
