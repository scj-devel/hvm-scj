package test;

public class TestStringBuffer2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		StringBuffer strBuffer = new StringBuffer();
        strBuffer.append('H');
        strBuffer.append('e');
        strBuffer.append('l');
        strBuffer.append('l');
        strBuffer.append('o');
        String str = strBuffer.toString();
        if (str.equals("Hello")) {
            strBuffer.append("World!");
            if (strBuffer.length() == 11) {
                if (!strBuffer.toString().equals("HelloWorld")) {
                    if (strBuffer.toString().equals("HelloWorld!")) {
                       return null;
                    }
                }
            }
        }
        return args;
	}
}
