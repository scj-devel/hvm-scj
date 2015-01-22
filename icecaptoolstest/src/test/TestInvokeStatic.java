package test;

public class TestInvokeStatic {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		int x = staticMethod(10, 32);
        
        if (x == 42)
        {
            return null;
        }
        return args;
	}

    private static int staticMethod(int i, int j) {
        return i + j;
    }

}
