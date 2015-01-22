package test;

public class TestException1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		int result = 0;
        try {
            result++;
            if (result == 1) {
                throw new Exception();
            }
            result++;
        } catch (Exception e) {
            result++;
        }
        if (result == 2) {
            return null;
        }
        return args;
	}

}
