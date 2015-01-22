package test;

public class TestInvokeDynamic4 {

	private static void print(String str)
	{
		devices.Console.println(str);
		// System.out.println(str);
	}
	
	private interface Action {
		void doIt();
	}

	private static void actionExecutor(Action action) {
		action.doIt();
	}

	private static void doItNow()
	{
		print("and one more time");
	}
	
	public static void main(String[] args) {
		Action action = new Action() {

			@Override
			public void doIt() {
				print("action executed");
			}
		};

		action.doIt();

		action = () -> {
			print("action executed again");
		};

		action.doIt();

		actionExecutor(() -> {
			print("and again");
		});
		
		action = TestInvokeDynamic4::doItNow;
		
		action.doIt();
		
		actionExecutor(TestInvokeDynamic4::doItNow);
		
		args = null;
	}
}
