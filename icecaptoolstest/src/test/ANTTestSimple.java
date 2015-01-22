package test;

import icecaptools.IcecapCompileMe;

// avr-gcc  -mmcu=atmega2560 -Wall -gdwarf-2 -std=gnu99  -Os -funsigned-char -funsigned-bitfields -fpack-struct -fshort-enums -S methods.c

public class ANTTestSimple {

    /**
     * @param args
     */
    public static void main(String[] args) {
       args = test(args);
    }

    public static String[] test(String[] args) {
        byte x = testBooleanFunction((byte) 42);
        if (x == 1) {
            return null;
        }
        return args;
    }

    @IcecapCompileMe
    public static byte testBooleanFunction(byte b) {
        if (b == 42) {
            return 1;
        } else {
            return 0;
        }
    }
}
