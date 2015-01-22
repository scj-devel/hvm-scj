package test;

public class TestStringToCharArray {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test();

        if (!failure)
        {
            args = null;
        }
    }

    private static boolean test() {
        String s1 = "AB";
        char[] chars = s1.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (i == 0) {
                if (chars[i] != 'A') {
                    return true;
                }
            } else if (i == 1) {
                if (chars[i] != 'B') {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }
}
