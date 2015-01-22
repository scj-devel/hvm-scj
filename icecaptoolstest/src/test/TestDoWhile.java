package test;

public class TestDoWhile {
    public static void main(String[] args) {
    	args = test(args);
    }

	public static String[] test(String[] args) {
		int i = 1;

        do {
            i = i << 2;
        } while (i < 255);

        if (i == 256) {
            return null;
        }
        return args;
	}

}
