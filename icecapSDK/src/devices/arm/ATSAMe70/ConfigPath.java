package devices.arm.ATSAMe70;

import java.io.File;

public abstract class ConfigPath {
	
	// The following are highly installation dependent:
	
	// At HSO: C:\\Users\\hso\\same70ToBoard\\src
	public static final String OUTPUT_FOLDER = 
		System.getProperty("user.home") +  File.separator + "same70ToBoard" +  File.separator + "src";
	
	// At HSO: C:\Users\hso\same70
	public static final String SAME_LOCATION = 
		System.getProperty("user.home") +  File.separator + "same70";
	
	// The name of the AtmelStudio project
	public static final String SAME_PROJECT = "SAME70Xplained-sandbox";
	
	
	// These are unlikely to change:
	
	// At HSO: C:\Users\hso\same70\SAME70Xplained-sandbox
	public static final String SAME_PATH = SAME_LOCATION + File.separator + SAME_PROJECT;
	
	public static final String SAME_ASF_src =
		SAME_PATH + File.separator + "src" + File.separator + "ASF";
	
	public static final String SAME_DEBUG_ASF_src = 
		SAME_PATH + File.separator + "Debug" + File.separator + "src" + File.separator + "ASF";
	
	public static final String SAME_CONFIG_src =
		SAME_PATH + File.separator + "src" + File.separator + "config"; // + File.separator;
	
	
	// Atmel Studio paths:
	
	public static final String ATMEL_STUDIO = 
		"C:" + File.separator + "Program Files (x86)" + File.separator + "Atmel" + File.separator + "Studio" + File.separator + "7.0";
	
	public static final String ATMEL_TOOLCHAIN_bin = 
		ATMEL_STUDIO + File.separator + "toolchain" + File.separator + "arm" + File.separator + "arm-gnu-toolchain" + File.separator + "bin" + File.separator;


	public static final String GCC_D_OPTIONS =
			"-DPC32 -Os -c -DJAVA_STACK_SIZE=420 -x c -mthumb "
			+ "-D__SAME70Q21__ -DDEBUG -DPACKED -D__SAME70Q21__ -DBOARD=SAME70_XPLAINED "
			+ "-Dscanf=iscanf -DARM_MATH_CM7=true -Dprintf=iprintf ";
			
}
