package test;

import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class TestSystemOutPrintln {

	public static void main(String args[])
	{
		test();
		VMTest.markResult(false);
	}

	@IcecapCompileMe
	private static void test() {
		System.out.println("Hello!");
	}
}
