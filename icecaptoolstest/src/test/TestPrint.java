package test;

public class TestPrint {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String string = "abc";

        int length = string.length();

        if (length == 3) {
            if (string.charAt(0) == 'a') {
                args = null;
            }
        }

        devices.Console.println(string);
    }
}
