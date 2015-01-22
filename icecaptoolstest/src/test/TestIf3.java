package test;

public class TestIf3 {


    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		int x = 42;
        int y = 40;
        if (y == x)
        {
            ;
        }
        else
        {
            return null;
        }
        return args;
	}
}
