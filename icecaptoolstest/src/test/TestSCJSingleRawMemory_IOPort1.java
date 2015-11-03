package test;

import javax.realtime.device.RawByte;
import javax.realtime.device.RawMemoryFactory;

import vm.Memory;


public class TestSCJSingleRawMemory_IOPort1 {
	
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
		 
		 int base = 18; // PING  = 0x12 = 18;  // AVR ATmega
		                // DDRG  = 0x13 = 19;
		                // PORTG = 0x14 = 20
		 
		 RawByte register = RawMemoryFactory.createRawByte(RawMemoryFactory.IO_PORT_MAPPED_REGION, base, 3, 1); 
		 
		 if (register != null) {
			 int registerSize = register.getSize();
			 System.out.println("RegisterSize: " + registerSize);
		 }
		 else {
			 return false;
		 }
		 
		 // test set- and getByte at offset 0:
		 
		 byte b = register.getByte();
		 System.out.println("Byte is: " + b);
		 
		 byte b1 = 12;
		 register.setByte(b1);	
		 byte b2 = register.getByte();
		 System.out.println("Byte is: " + b2);
		 
		 if (b2 != b1) 
	        return false;
		 
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


