package test;

import icecaptools.IcecapInlineNative;
import vm.VMTest;

public class TestNativeInclude {

	private static int x;
	
	private static class SuperClassWithNative
	{
		public void callNative()
		{
			callTheWeirdNative();
		}

		@IcecapInlineNative(
				functionBody = ""
				+ "{\n"
				+ "   some wrong C code that won't parse\n"
				+ "}\n",
				requiredIncludes = "#include <a non existing header file>\n")
		protected native void callTheWeirdNative();
	}
	
	private static class SubClassWithoutNative extends SuperClassWithNative
	{
		protected void callTheWeirdNative()
		{
			x = 42;
		}
	}
	
	public static void main(String[] args) {
		x = 43;
		
		SuperClassWithNative sc = new SubClassWithoutNative();
		
		sc.callNative();
		
		if (x == 42)
		{
			VMTest.markResult(false);
		}
		else
		{
			VMTest.markResult(true);
		}
	}
}
