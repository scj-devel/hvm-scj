package test;

import icecaptools.IcecapCompileMe;

public class TestDownload1 {

	public static void main(String[] args) {
		int sum = test1() + test2();
		if (sum == 42)
		{
			args = null;
		}
	}

	@IcecapCompileMe
	private static int test2() {
		return 41;
	}

	private static int test1() {
		return 1;
	}
}
