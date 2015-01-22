package test;

public class TestWhile1 {

    public static void main(String args[]) {
        args = test(args);

    }

	public static String[] test(String args[]) {
		int count = -5;
        int result = 10;
        while (count < 5) {
            result--;
            count++;
        }
        if (result == 0) {
            return null;
        }
        return args;
	}
}
