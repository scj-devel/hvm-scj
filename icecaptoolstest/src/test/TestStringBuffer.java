package test;

public class TestStringBuffer {

    public static void main(String args[]) {
        args = test(args);
    }

    public static String[] test(String[] args) {
        StringBuffer strbuf = new StringBuffer();
        String str1 = strbuf.toString();
        
        if (str1.equals("")) {
            return null;
        }
        return args;
    }
}
