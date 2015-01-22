package test;

public class TestOutOfMemory1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		args = test(args);
	}

	public static String[] test(String[] args) {

		try {
			while (true) {
				new TestOutOfMemory1();
			}
		} catch (OutOfMemoryError error) {
			return null;
		}
	}
}
