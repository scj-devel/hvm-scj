package test;

import icecaptools.IcecapCompileMe;

public class ANTTestIf {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test1(true);
        failure |= test2(true);
        failure |= test3(true);
        if (!failure) {
            args = null;
        }
    }

    @IcecapCompileMe
    public static boolean test3(boolean b) {
        String str1 = new String();
        String str2 = null;
        String str;
        str =  (b ? str1: str2);
        return str == str2;
    }

    @IcecapCompileMe
    public static boolean test1(boolean b) {
        byte[] array;
        if (b) {
            array = null;
        } else {
            array = new byte[5];
        }
        try {
            array[0] = 42;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    @IcecapCompileMe
    public static boolean test2(boolean b) {
        byte[] array;
        if (b) {
            array = new byte[10];
        } else {
            array = new byte[5];
        }
        try {
            array[0] = 42;
        } catch (Exception e) {
            return true;
        }
        return false;
    }
}
