package test;

import icecaptools.IcecapInlineNative;
import vm.VMTest;

public class TestInlineNativeFunctions {

	@IcecapInlineNative(
			functionBody = ""
			+ "{\n"
			+ "   sp[0] = 42;\n"
			+ "   return -1;\n"
			+ "}\n")
	private static native int foo();
	
	public static void main(String[] args) {
		
		int x = foo();
		
		x = x + bar();

		VMTest.markResult(x != 84);
	}

	private static int bar() {
		return foo();
	}
}
