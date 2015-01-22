package test;

public class TestIf1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

	@SuppressWarnings("unused")
	public static String[] test(String[] args) {
		if (true)
        {
            return null;
        }
		return args;
	}
}
