package test;

import javax.realtime.device.RawByte;
import javax.realtime.device.RawMemoryFactory;

import vm.Memory;


public class TestSCJSingleRawMemory_MemoryMapped1 {
	
	private static final int SCRATCHPADSTORESIZE = 4000;
	
		public static void main(String[] args) {	       
		       
			boolean success = test_RawMemory();
		 	
		 	if (success) {
	          args = null; 
		 	}
	 }
	 
	 // using RawMemoryFactory.MEMORY_MAPPED_REGION
		
	 static boolean test_RawMemory() {
		 
		 System.out.println("test_RawMemory");
		 
		 RawMemoryFactory factory = RawMemoryFactory.getDefaultFactory();
		 
		 System.out.println("RawMemoryFactory: " + factory);
		 
		 int base = Memory.allocateInHeap(SCRATCHPADSTORESIZE).getStartMemoryAddress();
		 
		 RawByte register = RawMemoryFactory.createRawByte(RawMemoryFactory.MEMORY_MAPPED_REGION, base, 5, 1);
		 
		 if (register != null) {
			 int registerSize = register.getSize();
			 System.out.println("RegisterSize: " + registerSize);
		 }
		 else {
			 return false;
		 }
		 
		 // test set- and getByte at offset 0:
		 
		 byte b1 = 12;
		 register.setByte(b1);	
		 byte b2 = register.getByte();
		 System.out.println("Byte is: " + b2);
		 
		 if (b2 != b1) 
	        return false;
		 
		// set- and getByte at other offsets
		   
        for (int i = 0; i < register.getSize(); i++)         	
        	register.setByte(i, (byte) (40 + i));
        
        for (int i = 0; i < register.getSize(); i++)  {
    		byte b3 = register.getByte(i);
    	
    		if (b3 != (byte) (40 + i) )
    			return false;
        }
		 
        // testing: public int get(int offset, byte[] values)
        
        byte[] values = new byte[3];
        
        for (int i = 0; i < register.getSize(); i++) {        	
        	register.setByte(i, (byte) (40 + i));
        	System.out.println("insert value[" + i + "] = " + register.getByte(i));
        }
        
        System.out.println("\nTesting: public int get(int offset, byte[] values)");
        
        int n = register.get(0, values);
        
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
        n = register.set(0, values);
        
        for (int i = 0; i < n; i++)  {
    		byte b4 = register.getByte(i);
    		System.out.println("set: value[" + i + "] = " + b2);
    	
    		if (b4 != (byte) (50 + i) )
    			return false;
        }
        
        return true;
	 }
	 
}


