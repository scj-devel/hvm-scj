package test;

public class TestString1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		char[] chars = new char[11];
        chars[0] = 'H';
        chars[1] = 'e';
        chars[2] = 'l';
        chars[3] = 'l';
        chars[4] = 'o';
        chars[5] = 'W';
        chars[6] = 'o';
        chars[7] = 'r';
        chars[8] = 'l';
        chars[9] = 'd';
        chars[10] = '!';
        String str = new String(chars);
        if (str.length() == 11)
        {
            return null;
        }
        return args;
	}
}
