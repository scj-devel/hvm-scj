package test;

import javax.realtime.device.RawByteHW;

import vm.Memory;

public class TestHardWareObjectForByte {
	private static final int SCRATCHPADSTORESIZE = 4000;

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        if (!failed) {
            args = null;
        }
    }

    public static boolean test() {

        int base = Memory.allocateInHeap(SCRATCHPADSTORESIZE).getBase();
        
        RawByteHW byteObj = new RawByteHW(base, 4, 1);
        System.out.println("TestHardWareObjectForByte1; byteObj = " + byteObj);
        
        byte b1 = 12;
        
//        byteObj.setByte(b1);
//        System.out.println("TestHardWareObjectForByte2; b1 = " + b1);
        
		byte b2 = byteObj.getByte();
		System.out.println("Byte is: " + b2);
		 
		
        return true;
    }

}
