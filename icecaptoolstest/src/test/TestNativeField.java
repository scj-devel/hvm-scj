package test;

import icecaptools.IcecapCVar;
import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class TestNativeField {

	private static class SuperClass {
		@IcecapCVar
		public static byte superByte;
	}

	
	private static class SubClass extends SuperClass {
		
		@IcecapCompileMe
		public static boolean testField() {
			superByte = 42;
			if (superByte == 42) {
				return false;
			} else {
				return true;
			}

		}
	}

	public static void main(String[] args) {
		if (!SubClass.testField())
		{
			VMTest.markResult(false);
		}
	}
}
