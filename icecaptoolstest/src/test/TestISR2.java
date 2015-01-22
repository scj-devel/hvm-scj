package test;

import icecaptools.IcecapCFunc;

public class TestISR2 {

	private static int x;
	
	@IcecapCFunc(signature = "int16 n_test_TestISR2_callFoo(int32 *fp)", hasReturnValue = "true")
	private static void foo()
	{
		x = 42;
	}
	
	@IcecapCFunc(signature = "void XXX(void)", requiredIncludes = "static int32 fp[64];")
	private static void bar() {
		x = baz(43);
	}

	private static int baz(int i) {
		return x = i;
	}

	private static native void callFoo();
	
	public static void main(String[] args) {
		if (x == 0)
		{
			callFoo();
			if (x == 42)
			{
				args = null;
			}
		}
	}
}
