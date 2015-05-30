package test;

import vm.VMTest;

public class TestStringBuffer2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

	public static boolean test() {
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
                       return false;
                    }
                }
            }
        }
        return true;
	}
}
