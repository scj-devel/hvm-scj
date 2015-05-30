package test;

import vm.VMTest;

public class TestProgram2 {
    public static char[] randomString(int len) {
        char[] string = new char[len];

        for (int i = 0; i < len; i++) {
            int x = 33 + i%32;
            string[i] = (char) x;
        }

        return string;
    }

    public static char[] reverseString(char[] string) {
        int len = string.length - 1;

        for (int i = 0; i < len - i; i++) {
            char tmp = string[len - i];
            string[len - i] = string[i];
            string[i] = tmp;
        }

        return string;
    }

    public static void main(String[] args) {
    	 VMTest.markResult(test());
    }

	public static boolean test() {
		char[] string = new char[2];
        int i = 0;
        while (i < 1) {
            string = randomString(2);
            reverseString(string);
            //System.out.println(string);
            //System.out.println(reverseString(string));
            ++i;
        }
        return false;
	}
}
