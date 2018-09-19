package carcontrol.infrastructure;

import java.io.File;

public class VMConfiguration {
	
	// Files and folders used in Car compilation and execution
	// They are highly installation dependent	
    public static final String ICECAPVM_SRC =  
    	System.getProperty("user.home") + File.separator + 
    	  "git" + File.separator + "hvm-scj" + File.separator + "icecapvm" + File.separator + "src";
    
    public static final String INPUTFOLDER = 
    	System.getProperty("user.home") + File.separator + "CarControlSystem" + File.separator + "bin";
    
    /* For 64 bit Linux */
    // Removed: -Wall -pedantic
    // Added: -DBOARD=SAME70_XPLAINED
    public static final String GCC_COMMAND = 
    		"gcc -Wall -pedantic -Os -DPC64 -DREF_OFFSET -DPRINTFSUPPORT -DSUPPORTGC -DJAVA_HEAP_SIZE=10240000 "
			+ "-L/usr/lib64 "
    				
    		//+ "-I" + File.separator + "mnt" + File.separator + System.getProperty("user.home") + File.separator + "CarControlSystem" + File.separator + "hal "
    		
    		//+ "-I/mnt/c/Users/hso/CarControlSystem/hal "
    		
//    		+ "-I/mnt/c/Users/hso/sameout/src "
//    		+ "-I/mnt/c/Users/hso/sameout/src/scalextric "    		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/sam/utils "   		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/common/utils "  		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/sam/utils/preprocessor "
//    		+ "-I/mnt/c/Users/hso/sameout/src/loader "   		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/common/services/fifo " 		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/common/services/gpio "   		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/common/boards "   		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/common/services/ioport "  		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/common/services/ioport/sam "      		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/sam/drivers/mpu "     		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/sam/drivers/pio "     		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/sam/drivers/pmc "    		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/sam/drivers/pwm "     		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/sam/utils/fpu "    		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/sam/boards/same70_xplained "    		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/sam/drivers/spi "  		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/common/utils/stdio/stdio_serial "  		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/common/services/serial "       		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/common/services/clock "
//    		+ "-I/mnt/c/Users/hso/sameout/src/config "   		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/sam/drivers/uart "   		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/sam/drivers/usart "   		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/sam/utils/cmsis/same70/include/component "	 		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/sam/utils/cmsis/same70/include/pio "	 		
//    		+ "-I/mnt/c/Users/hso/sameout/src/ASF/sam/utils/cmsis/same70/include/instance "		
    		
    		+ "-I" + System.getProperty("user.home") + File.separator 
    		       + "git" + File.separator + "hvm-scj" + File.separator + "icecaptools" + File.separator + "resources "
    		//+ "-IC:\\Users\\hso\\git\\hvm-scj\\icecaptools\\resources "
    		//+ "-I/mnt/c/Users/hso/git/hvm-scj/icecaptools/resources "
    		
    		//+ File.separator + "mnt" + File.separator + System.getProperty("user.home") + File.separator + "CarControlSystem" + File.separator + "hal" + File.separator + "front_light.c "     
    		     
    	    		
    		//+ "/mnt/c/Users/hso/CarControlSystem/hal/front_light.c "
    		//+ "/mnt/c/Users/hso/sameout/src/scalextric/light_control.c "
    		
			+ "classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c "
			+ "allocation_point.c rom_access.c print.c  x86_64_interrupt.s"
			+ " -lpthread -lrt -lm -o ";

    /* The actual platform */
    public static final AdapterType platform = AdapterType.CAR_VM;
    
    /* Running Car implements Safelet (CarTest = false) or running Car0Test (CarTest = true); called in CarVM */
    public static final boolean CarTest = true;
    
    
    public static final boolean MemoryTracking = true;
    
}
