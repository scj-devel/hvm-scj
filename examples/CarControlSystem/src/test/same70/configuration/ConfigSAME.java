package test.same70.configuration;

import java.io.File;

public abstract class ConfigSAME {
	
	// The following are highly installation dependent:
	
	// At HSO: C:\Users\hso\sameout
	public static String SAME_LOCATION = 
		System.getProperty("user.home") +  File.separator + "sameout";
	
			
	// Maybe this should be changed too (but normally not):
	
	// At HSO: C:\\Users\\hso\\sameout\\src	
	public static final String SAME_src = 
		SAME_LOCATION  +  File.separator + "src";
	
	public static final String OUTPUT_FOLDER = 
		SAME_src;	
	
	// These are unlikely to change:
	
	public static final String SAME_src_ASF =
		SAME_LOCATION + File.separator + "src" + File.separator + "ASF";
	
	public static final String SAME_Debug_src_ASF = 
		SAME_LOCATION + File.separator + "Debug" + File.separator + "src" + File.separator + "ASF";
	
	public static final String SAME_src_config =
		SAME_LOCATION + File.separator + "src" + File.separator + "config"; 
	
	public static final String SAME_src_ASF_sam_drivers =
		SAME_src_ASF + File.separator + "sam" + File.separator + "drivers";
	
	
	// Atmel Studio paths:
	
	public static final String ATMEL_STUDIO = 
		"C:" + File.separator + "Program Files (x86)" + File.separator + "Atmel" + File.separator + "Studio" + File.separator + "7.0";
	
	public static final String ATMEL_TOOLCHAIN_bin = 
		ATMEL_STUDIO + File.separator + "toolchain" + File.separator + "arm" + File.separator + "arm-gnu-toolchain" + File.separator + "bin" + File.separator;


//	"-DPC32", "-c", "-DJAVA_STACK_SIZE=420", "-x", "c", "-mthumb", "-D__SAME70Q21__",
//	"-DDEBUG", "-DPACKED=", "-D__SAME70Q21__", "-DBOARD=SAME70_XPLAINED", "-Dscanf=iscanf",
//	"-DARM_MATH_CM7=true", "-Dprintf=iprintf",	
	public static final String GCC_D_OPTIONS =  // does not work
		"-DPC32 -c -DJAVA_STACK_SIZE=420 -x c -mthumb -D__SAME70Q21__ "
		+ "-DDEBUG -DPACKED -D__SAME70Q21__ -DBOARD=SAME70_XPLAINED -Dscanf=iscanf "
		+ "-DARM_MATH_CM7=true -Dprintf=iprintf ";
	
//	"-O0", "-g", "-fdata-sections", "-ffunction-sections", "-mlong-calls", "-Wall", "-mcpu=cortex-m7",
//	"-pipe", "-fno-strict-aliasing", "-Wall", "-Wstrict-prototypes", "-Wmissing-prototypes",
//	"-Werror-implicit-function-declaration", "-Wpointer-arith", "-std=gnu99", "-ffunction-sections",
//	"-fdata-sections", "-Wchar-subscripts", "-Wcomment", "-Wformat=2", "-Wimplicit-int", "-Wmain",
//	"-Wparentheses", "-Wsequence-point", "-Wreturn-type", "-Wswitch", "-Wtrigraphs", "-Wunused",
//	"-Wuninitialized", "-Wunknown-pragmas", "-Wfloat-equal", "-Wundef", "-Wshadow",
//	"-Wbad-function-cast", "-Wwrite-strings", "-Wsign-compare", "-Waggregate-return",
//	"-Wmissing-declarations", "-Wformat", "-Wmissing-format-attribute",
//	"-Wno-deprecated-declarations", "-Wpacked", "-Wnested-externs",
//	"-Wlong-long", "-Wunreachable-code", "-Wcast-align", "--param", "max-inline-insns-single=500",
//	"-mfloat-abi=softfp", "-mfpu=fpv5-sp-d16", 	
	public static final String GCC_W_OPTIONS =  // does nor work
		"-O0 -g -fdata-sections -ffunction-sections -mlong-calls -Wall -mcpu=cortex-m7 "
		+ "-pipe -fno-strict-aliasing -Wall -Wstrict-prototypes -Wmissing-prototypes "
		+ "-Werror-implicit-function-declaration -Wpointer-arith -std=gnu99 -ffunction-sections "
		+ "-fdata-sections -Wchar-subscripts -Wcomment -Wformat=2 -Wimplicit-int -Wmain "
		+ "-Wparentheses -Wsequence-point -Wreturn-type -Wswitch -Wtrigraphs -Wunused "
		+ "-Wuninitialized -Wunknown-pragmas -Wfloat-equal -Wundef -Wshadow "
		+ "-Wbad-function-cast -Wwrite-strings -Wsign-compare -Waggregate-return "
		+ "-Wmissing-declarations -Wformat -Wmissing-format-attribute "
		+ "-Wno-deprecated-declarations -Wpacked -Wnested-externs "
		+ "-Wlong-long -Wunreachable-code -Wcast-align --param max-inline-insns-single=500 "
		+ "-mfloat-abi=softfp -mfpu=fpv5-sp-d16 ";
			
}
