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
        int base = Memory.allocateInHeap(SCRATCHPADSTORESIZE).getStartMemoryAddress();
        
        RawByteHW byteObj = new RawByteHW(base, 4, 1);
        
        byteObj.setByte((byte) 42);
        
		byte b2 = byteObj.getByte();
		
		
        return b2 != 42;
    }

}
