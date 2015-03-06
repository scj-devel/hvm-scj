package test;

import icecaptools.IcecapCompileMe;

public class TestSystemOutPrintln {

	public static void main(String args[])
	{
		test();
		args = null;
	}

	@IcecapCompileMe
	private static void test() {
		System.out.println("Hello!");
	}
}
