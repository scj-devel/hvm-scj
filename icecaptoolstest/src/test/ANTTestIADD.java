package test;

import icecaptools.IcecapCompileMe;

public class ANTTestIADD {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = testIADD((byte) 10, (byte) 1);
        failure |= testISHR(0xf0, 4);
        failure |= testISUB((byte)10, (short)4);
        failure |= testIMUL((short)10, (short)4);
        failure |= testIDIV(10, 4);
        failure |= testIREM((byte)10, 4);
        if (!failure) {
            args = null;
        }
    }

    @IcecapCompileMe
    public static boolean testIREM(byte i, int j) {
        if (i % j == 2)
        {
            return false;
        }
        return true;
    }

    @IcecapCompileMe
    public static boolean testIDIV(int i, int j) {
        if (i / j == 2)
        {
            return false;
        }
        return true;
    }

    @IcecapCompileMe
    public static boolean testIMUL(short b, short s) {
        if (b * s == 40)
        {
            return false;
        }
        return true;
    }

    @IcecapCompileMe
    public static boolean testISUB(byte i, short j) {
        if (i - j == 6) {
            return false;
        } else {
            return true;
        }
    }

    @IcecapCompileMe
    public static boolean testIADD(byte i, byte j) {
        byte x = (byte) (i + j);
        if (x == 11) {
            return false;
        } else {
            return true;
        }
    }

    @IcecapCompileMe
    public static boolean testISHR(int i, int j) {
        byte x = (byte) (i >> j);
        if (x == 0x0f) {
            return false;
        } else {
            return true;
        }
    }

}
