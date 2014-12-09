package test;

public class TestCurrentTimeMillis {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		long end;
		
		devices.Console.println("Waiting 1 sec");
		
		do {
			end = System.currentTimeMillis();
		} while (end - start < 1000);
			
		devices.Console.println("Done");
		args = null;
	}
}
