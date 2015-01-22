package test;

import icecaptools.IcecapCompileMe;

public class ANTTestMethodCall {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = testByte();
        failure |= testShort();
        failure |= testShortByte();
        
        if (!failure)
        {
            args = null;
        }
    }

    @IcecapCompileMe
    public static boolean testByte() {
        byte x, y;
        y = 10;
        x = byteMethod(y);
        if (x != y) {
            return true;
        }
        return false;
    }

    @IcecapCompileMe
    public static boolean testShort() {
        short a, b;
        b = 11;
        a = shortMethod(b);
        if (a != b) {
            return true;
        }
        return false;
    }
    
    @IcecapCompileMe
    public static boolean testShortByte() {
        short a, b;
        byte y;
        y = 10;
        b = 11;
        a = shortByteMethod(b, y, b);
        if (a != b) {
            return true;
        }
        return false;
    }
    
    
    private static short shortByteMethod(short i, byte b, short j) {
        if (j - i == 0)
            return i;
        return 0;
    }

    @IcecapCompileMe
    private static short shortMethod(short b) {
        return b;
    }

    @IcecapCompileMe
    public static byte byteMethod(byte y) {
        return y;
    }
}
