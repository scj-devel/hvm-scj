package test;

public class TestInvokeDynamic5 {

	private static int count = 0;

	private static void print(String str) {
		devices.Console.println(str);
		//System.out.println(str);
	}

	private interface Action {
		void doIt();
	}

	public static void doIt() {
		print("do it first time");
		count++;
	}

	public static void doItTwice() {
		print("and one more time");
		count++;
	}

	public static void main(String[] args) {
		Action action;

		action = TestInvokeDynamic5::doIt;

		action.doIt();

		action = TestInvokeDynamic5::doItTwice;

		action.doIt();

		doItTwice();
		if (count == 3) {
			args = null;
		}
	}

}
