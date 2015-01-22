package test;

public class TestPrimitiveClass {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		Class<Integer> intClass = int.class;
        if (intClass == int.class)
        {
            return null;
        }
        return args;
	}
}
