package test;

import icecaptools.IcecapCVar;
import icecaptools.IcecapCompileMe;

public class TestCVar1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test1();
        failed |= test2();
        failed |= test3();
        failed |= test4();
        if (!failed) {
            args = null;
        }
    }

    @IcecapCVar
    static private int ivar;

    @IcecapCompileMe
    private static boolean test1() {
        ivar = 0x42424242;
        if (getIVar() == 0x42424242) {
            if (ivar == 0x42424242) {
                return false;
            }
        }
        return true;
    }

    @IcecapCVar
    static private byte bvar;

    @IcecapCompileMe
    private static boolean test2() {
        bvar = 0x42;
        if (getBVar() == 0x42) {
            if (bvar == 0x42) {
                return false;
            }
        }
        return true;
    }

    @IcecapCVar
    static private short svar;

    @IcecapCompileMe
    private static boolean test3() {
        svar = 0x4242;
        if (getSVar() == 0x4242) {
            if (svar == 0x4242) {
                return false;
            }
        }
        return true;
    }
    
    @IcecapCVar
    static private long lvar;

    @IcecapCompileMe
    private static boolean test4() {
        lvar = 0x1234567887654321L;
        if (getLVar() == 0x1234567887654321L) {
            if (lvar == 0x1234567887654321L) {
                return false;
            }
        }
        return true;
    }
    
    private static native int getIVar();
    private static native byte getBVar();
    private static native short getSVar();
    private static native long getLVar();
}
