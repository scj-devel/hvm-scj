package test.icecaptools.minitests;

public class TestStackUsage1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String result = foo("hej", 10);
        System.out.println(result);
    }

    public static String foo(String string, int i) {
        String str = "hej";
        return str;
    }
}
