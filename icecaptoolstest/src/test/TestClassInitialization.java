package test;

import vm.VMTest;

public class TestClassInitialization {

	static int result;

	private interface Interface {
		void foo();
	}

	private static class Default implements Interface {

		@Override
		public void foo() {
			devices.Console.println("default");
			result = 43;
		}
	}

	private static class Special implements Interface {

		@Override
		public void foo() {
			devices.Console.println("special");
			result = 42;
		}
	}

	static Interface theImplementation;

	private static class Logic {
		private static int element;

		static {
			theImplementation = new Special();
		}

		public static void bar() {
			if (element == 0) {
				element++;
			}
		}
	}

	public static void main(String[] args) {
		if (theImplementation == null) {
			theImplementation = new Default();
		}

		theImplementation.foo();

		Logic.bar();

		VMTest.markResult(result != 42);
	}
}
