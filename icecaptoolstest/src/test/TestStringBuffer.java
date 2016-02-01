package test;

import vm.VMTest;

public class TestStringBuffer {

    public static void main(String args[]) {
    	VMTest.markResult(test());
    }

    public static boolean test() {
        StringBuffer strbuf = new StringBuffer();
        String str1 = strbuf.toString();
        
        if (str1.equals("")) {
            return false;
        }
        return true;
    }
}
