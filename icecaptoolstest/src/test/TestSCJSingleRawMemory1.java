package test;

import javax.realtime.device.RawByte;
import javax.realtime.device.RawMemoryFactory;



public class TestSCJSingleRawMemory1 {
	
		public static void main(String[] args) {	       
		       
		 	int result = test_RawMemory();
		 	
		 	if (result >= 0)
	          args = null; 
	 }
	 
	 
	 
	 static int test_RawMemory() {
		 
		 System.out.println("test_RawMemory");
		 
		 RawMemoryFactory factory = RawMemoryFactory.getDefaultFactory();
		 
		 System.out.println("RawMemoryFactory: " + factory);
		 
		 RawByte controlReg = factory.createRawByte(RawMemoryFactory.MEMORY_MAPPED_REGION, 0x20, 4, 1);
		 
		 RawByte dataReg    = factory.createRawByte(RawMemoryFactory.MEMORY_MAPPED_REGION, 0x24, 8, 1);
		 
		 if (controlReg != null) {
			 int controlRegSize = controlReg.getSize();
			 System.out.println("controlRegSize: " + controlRegSize);
		 }
		 else
			 System.out.println("controlReg: " + controlReg); 
		 
		 
		 return 1;
	 }
	 
}


