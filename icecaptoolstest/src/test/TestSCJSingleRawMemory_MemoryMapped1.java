package test;

import javax.realtime.device.RawByte;
import javax.realtime.device.RawMemoryFactory;

import vm.Memory;
import vm.VMTest;


public class TestSCJSingleRawMemory_MemoryMapped1 {
	
	private static final int SCRATCHPADSTORESIZE = 4000;
	
		public static void main(String[] args) {	       
		       
			boolean success = test_RawMemory();
		 	
		 	if (success) {
		 		VMTest.markResult(false);
		 	}
	 }
	 
	 // using RawMemoryFactory.MEMORY_MAPPED_REGION
		
	 static boolean test_RawMemory() {
		 
		 System.out.println("test_RawMemory");
		 
		 RawMemoryFactory factory = RawMemoryFactory.getDefaultFactory();
		 
		 System.out.println("RawMemoryFactory: " + factory);
		 
		 int base = Memory.allocateInHeap(SCRATCHPADSTORESIZE).getStartMemoryAddress();
		 
		 RawByte _register_ = RawMemoryFactory.createRawByte(RawMemoryFactory.MEMORY_MAPPED_REGION, base, 5, 1);
		 
		 if (_register_ != null) {
			 int registerSize = _register_.getSize();
			 System.out.println("RegisterSize: " + registerSize);
		 }
		 else {
			 return false;
		 }
		 
		 // test set- and getByte at offset 0:
		 
		 byte b1 = 12;
		 _register_.setByte(b1);	
		 byte b2 = _register_.getByte();
		 System.out.println("Byte is: " + b2);
		 
		 if (b2 != b1) 
	        return false;
		 
		// set- and getByte at other offsets
		   
        for (int i = 0; i < _register_.getSize(); i++)         	
        	_register_.setByte(i, (byte) (40 + i));
        
        for (int i = 0; i < _register_.getSize(); i++)  {
    		byte b3 = _register_.getByte(i);
    	
    		if (b3 != (byte) (40 + i) )
    			return false;
        }
		 
        // testing: public int get(int offset, byte[] values)
        
        byte[] values = new byte[3];
        
        for (int i = 0; i < _register_.getSize(); i++) {        	
        	_register_.setByte(i, (byte) (40 + i));
        	System.out.println("insert value[" + i + "] = " + _register_.getByte(i));
        }
        
        System.out.println("\nTesting: public int get(int offset, byte[] values)");
        
        int n = _register_.get(0, values);
        
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
        n = _register_.set(0, values);
        
        for (int i = 0; i < n; i++)  {
    		byte b4 = _register_.getByte(i);
    		System.out.println("set: value[" + i + "] = " + b2);
    	
    		if (b4 != (byte) (50 + i) )
    			return false;
        }
        
        return true;
	 }
	 
}


