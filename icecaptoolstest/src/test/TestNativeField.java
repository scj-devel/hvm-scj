package test;

public class TestNativeField {

	private static class SuperClass {
		public static byte superByte;
	}

	private static class SubClass extends SuperClass {
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
			args = null;
		}
	}
}
