package test;

import icecaptools.IcecapCFunc;
import icecaptools.IcecapInlineNative;
import vm.VMTest;

public class TestInlineNativeFunctions {

	@IcecapInlineNative(
			functionBody = ""
			+ "{\n"
			+ "   sp[0] = 42;\n"
			+ "   printf(\"calling native method body\\n\");\n" 
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = "#include <stdio.h>\n")
	private static native int foo();
	
	@IcecapInlineNative(
			functionBody = ""
			+ "{\n"
			+ "   sp[0] = doo();\n"
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = "#include <stdio.h>\n")
	private static native int baz();
	
	@IcecapCFunc(
			signature = "uint32 doo(void)",	requiredIncludes = "uint32 doo(void);\n")
	@IcecapInlineNative(
			functionBody = ""
			+ "{\n"
			+ "   printf(\"calling native method body again\\n\");\n" 
			+ "   return 43;\n"
			+ "}\n")
	private static native int daa();
	
	public static void main(String[] args) {
		
		int x = foo();
		
		x = x + bar();

		if (x == 84)
		{
			x += baz();
		}
		VMTest.markResult(x != 127);
	}

	private static int bar() {
		return foo();
	}
}
