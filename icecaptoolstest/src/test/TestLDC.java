package test;

public class TestLDC {

    static String str;

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		String s = "HelloWorld!";
        str = s;
        if (str == s) {
            int x = 123456;
            if (x > 0) {
                String other = "HelloAgain";
                str = other;
                if (str == other) {
                    String oneMore = "HelloWorld!";
                    str = s;
                    if (oneMore == str) {
                        if (oneMore.getClass() == String.class) {
                            return null;
                        }
                    }
                }
            }
        }
        return args;
	}
}
