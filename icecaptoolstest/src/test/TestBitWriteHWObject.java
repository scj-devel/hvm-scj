package test;

import vm.Address32Bit;
import vm.HardwareObject;

public class TestBitWriteHWObject {

    private static class Port extends HardwareObject {
        @SuppressWarnings("unused")
        public boolean bit0;
        @SuppressWarnings("unused")        
        public boolean bit1;
        @SuppressWarnings("unused")        
        public boolean bit2;
        @SuppressWarnings("unused")        
        public boolean bit3;
        @SuppressWarnings("unused")        
        public boolean bit4;
        @SuppressWarnings("unused")        
        public boolean bit5;
        @SuppressWarnings("unused")        
        public boolean bit6;
        public boolean bit7;
        
        
        public Port(int address) {
            super(new Address32Bit(address));
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Port port = new Port(0xFFFFF412);

        port.bit7 = true;
        
        if (port.bit7 == true)
        {
            args = null;
        }
    }
}
