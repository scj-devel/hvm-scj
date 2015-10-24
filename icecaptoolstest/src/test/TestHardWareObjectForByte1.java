package test;

import javax.realtime.device.RawByteHW;

import vm.Memory;

public class TestHardWareObjectForByte1 {
	private static final int SCRATCHPADSTORESIZE = 4000;

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean success = test();
        if (success) {
            args = null;
        }
    }

    public static boolean test() {
        int base = Memory.allocateInHeap(SCRATCHPADSTORESIZE).getStartMemoryAddress();
        
        RawByteHW byteObj = new RawByteHW(base, 5, 1);
        
        // testing set- and getByte at offset 0
        
        byteObj.setByte((byte) 42);
        
		byte b = byteObj.getByte();		
		
		if (b != (byte) 42)
			return false;
		
		// testing set- and getByte at other offsets
   
        for (int i = 0; i < byteObj.getSize(); i++)         	
        	byteObj.setByte(i, (byte) (40 + i));
        
        for (int i = 0; i < byteObj.getSize(); i++)  {
    		byte b2 = byteObj.getByte(i);
    	
    		if (b2 != (byte) (40 + i) )
    			return false;
        }
        
        // testing: public int get(int offset, byte[] values)
                
        byte[] values = new byte[3];
        
        for (int i = 0; i < byteObj.getSize(); i++) {        	
        	byteObj.setByte(i, (byte) (40 + i));
        	System.out.println("insert value[" + i + "] = " + byteObj.getByte(i));
        }
        
        System.out.println("\nTesting: public int get(int offset, byte[] values)");
        
        int n = byteObj.get(0, values);
        
        if (n != 3) 
        	return false;
        
        for (int i = 0; i < n; i++) {
        	byte b3 = values[i];
        	System.out.println("get: value[" + i + "] = " + b3);
        	if (b3 != (byte) (40 + i))
        		return false;
        }
                        
        // testing: public int set(int offset, byte[] values)   
        System.out.println("\nTesting: public int set(int offset, byte[] values)");
        		
        values = new byte[] {50, 51, 52};
        n = byteObj.set(0, values);
        
        for (int i = 0; i < n; i++)  {
    		byte b2 = byteObj.getByte(i);
    		System.out.println("set: value[" + i + "] = " + b2);
    	
    		if (b2 != (byte) (50 + i) )
    			return false;
        }
        
        return true;       
    }

}
