package test;

public class TestInvokeDynamic6 {

	private static void print(String str)
	{
		devices.Console.println(str);
		// System.out.println(str);
	}
	
	private interface Action {
		void doIt();
	}

	private static class MyAction 
	{
		public void doItAgain() {
			print("and one final time");
		}		
	}
	
	public static void main(String[] args) {
		Action action;
		
		MyAction myAction = new MyAction();
		
		action = myAction::doItAgain;
		
		action.doIt();
		
		args = null;
	}
}
