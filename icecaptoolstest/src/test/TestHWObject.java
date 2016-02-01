package test;

import vm.Address32Bit;
import vm.HardwareObject;
import vm.VMTest;

public class TestHWObject {

    private static class Port extends HardwareObject {
        public byte byte1;
        public byte byte2;
        @SuppressWarnings("unused")
        public byte byte3;
        @SuppressWarnings("unused")
        public byte byte4;
        @SuppressWarnings("unused")
        public byte byte5;

        public short short1;
        public int int1;

        @SuppressWarnings("unused")
        public byte byte6;

        public Port(int address) {
            super(new Address32Bit(address));
        }

    }

    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    protected static boolean test() {
        Port port = new Port(0x12345678);

        port.byte1 = 42;
        if (port.byte1 == 42) {
            if (port.byte2 != 42) {
                port.short1 = (short) 0xfefe;
                if (port.short1 == (short) 0xfefe) {
                    port.int1 = 0xabbaabba;
                    if (port.int1 == 0xabbaabba) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
