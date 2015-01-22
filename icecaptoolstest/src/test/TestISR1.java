package test;

import icecaptools.IcecapCFunc;

public class TestISR1 {

	private static int x;
	
	@IcecapCFunc(signature = "int16 n_test_TestISR1_callFoo(int32 *sp)", hasReturnValue = "true")
	private static void foo()
	{
		x = 42;
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
