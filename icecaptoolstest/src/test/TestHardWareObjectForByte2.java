package test;

import javax.realtime.device.RawByteMM;

import vm.Memory;
import vm.VMTest;

public class TestHardWareObjectForByte2 {
	
	private static final int SCRATCHPADSTORESIZE = 4000;

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean success = test();
        VMTest.markResult(success == false);
    }

    public static boolean test() {
        int base = Memory.allocateInHeap(SCRATCHPADSTORESIZE).getStartMemoryAddress();
        
        RawByteMM byteObj = new RawByteMM(base, 6, 1);
        
        for (int i = 0; i < byteObj.getSize(); i++) {        	
        	byteObj.setByte(i, (byte) (40 + i));
        	System.out.println("insert value[" + i + "] = " + byteObj.getByte(i));
        }

        // public int get(int offset, byte[] values, int start, int count)
        System.out.println("\nTesting: public int get(int offset, byte[] values, int start, int count)");
        
        byte[] values = new byte[5];
        
        int n = byteObj.get(1, values, 2, 3);
        
        if (n != 3) 
        	return false;
        
        for (int i = 2; i < 2+n; i++) {
        	byte b3 = values[i];
        	System.out.println("get: value[" + i + "] = " + b3);
        	if (b3 != (byte) (40 + i - 1))
        		return false;
        }
        
        // testing: public int set(int offset, byte[] values, int start, int count)
        System.out.println("\nTesting: public int set(int offset, byte[] values, int start, int count)");
        
        values = new byte[] {60, 61, 62, 63};
        n = byteObj.set(2, values, 1, 3);
        
        for (int i = 2; i < 2+n; i++)  {
    		byte b2 = byteObj.getByte(i);
    		System.out.println("set: value[" + i + "] = " + b2);
    	
    		if (b2 != (byte) (60 + i -1) )
    			return false;
        }
        
        return true;       
    }

}
