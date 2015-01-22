package test;

public class TestFor2 {
    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		for (int i = 42; i >= 0; i--) {
            for (int j = 0; j < 3; j++) {
                if (i == 0 && j==2) {
                    return null;
                }
            }
        }
		return args;
	}
}
