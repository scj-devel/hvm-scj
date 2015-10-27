package test;

import javax.realtime.device.RawByte;
import javax.realtime.device.RawMemoryFactory;

import vm.Memory;


public class TestSCJSingleRawMemory_IOPort1 {
	
	private static final int SCRATCHPADSTORESIZE = 4000;
	
		public static void main(String[] args) {	       
		       
			boolean success = test_RawMemory();
		 	
		 	if (success) {
	          args = null; 
		 	}
	 }
	 
	 // using RawMemoryFactory.IO_PORT_MAPPED_REGION
		
	 static boolean test_RawMemory() {
		 
		 System.out.println("test_RawMemory, IO_PORT_MAPPED_REGION");
		 
		 RawMemoryFactory factory = RawMemoryFactory.getDefaultFactory();
		 
		 System.out.println("RawMemoryFactory: " + factory);
		 
		 //int base = Memory.allocateInHeap(SCRATCHPADSTORESIZE).getStartMemoryAddress();
		 
		 int base = 0; // what is base; how can we get base?
		 
		 //RawByte register = RawMemoryFactory.createRawByte(RawMemoryFactory.IO_PORT_MAPPED_REGION, base, 3, 1);  
		 // count = 3: three registers are defined in ByteHWObjectATMega2560IOPort: PINA, DDRA, PORTA
		 
//		 if (register != null) {
//			 int registerSize = register.getSize();
//			 System.out.println("RegisterSize: " + registerSize);
//		 }
//		 else {
//			 return false;
//		 }
		 
		 // test set- and getByte at offset 0:
		 
//		 byte b1 = 12;
//		 register.setByte(b1);	
//		 byte b2 = register.getByte();
//		 System.out.println("Byte is: " + b2);
//		 
//		 if (b2 != b1) 
//	        return false;
		 
		// set- and getByte at other offsets
		   
//        for (int i = 0; i < register.getSize(); i++)         	
//        	register.setByte(i, (byte) (40 + i));
//        
//        for (int i = 0; i < register.getSize(); i++)  {
//    		byte b3 = register.getByte(i);
//    	
//    		if (b3 != (byte) (40 + i) )
//    			return false;
//        }
		 
        		
      
        return true;
	 }
	 
}


