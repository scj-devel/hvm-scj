package devices.arm.ATSAMe70;

import java.io.File;

import devices.Console;
import devices.TargetConfiguration;
import devices.Writer;
import icecaptools.IcecapCompileMe;
import icecaptools.IcecapInlineNative;
import util.BaseTargetConfiguration;
import vm.Machine;
import vm.RealtimeClock.DefaultRealtimeClock;

public abstract class ATSAMe70MinimalTargetConfiguration extends BaseTargetConfiguration implements TargetConfiguration {

	public static class ATSAMe70RealtimeClock extends DefaultRealtimeClock {

	}

	protected static boolean initialized = false;
	
	static {
		Machine.setMachineFactory(new ATSAMe70MachineFactory());
		Console.writer = new ATSAMe70Writer();
	}
	
	@Override
	public String[][] getBuildCommands() {
		String srcASFInclude = trim(getSrcASFLocation());
		String debugSrcASF = trim(getDebugSrcASFLocation());
		String srcInclude = trim(getSrcLocation());
		
		return new String[][] {
				
				new String[] {
						ATSAMe70Config.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-gcc.exe",
						
						"-DPC32", "-c", "-DJAVA_STACK_SIZE=420", "-x", "c", "-mthumb", "-D__SAME70Q21__",
						"-DDEBUG", "-DPACKED=", "-D__SAME70Q21__", "-DBOARD=SAME70_XPLAINED", "-Dscanf=iscanf",
						"-DARM_MATH_CM7=true", "-Dprintf=iprintf",	
						//ConfigPath.GCC_D_OPTIONS,						
						
						"-I\"" + srcASFInclude + "\"",
						//"-I" +  ConfigPath.SAME_src_ASF + File.separator,	
						
						"-I\"" + srcASFInclude + "/sam/drivers/uart\"",						
						"-I\"" + srcASFInclude + "/sam/drivers/usart\"",
						
						//"-I" +  ConfigPath.SAME_src_ASF_sam_drivers + File.separator + "uart",
						//"-I" +  ConfigPath.SAME_src_ASF_sam_drivers + File.separator + "usart",
						
						"-I\"" + srcASFInclude + "/sam/drivers/spi\"",
						"-I\"" + srcASFInclude + "/sam/drivers/pwm\"",
						"-I\"" + srcASFInclude + "/common/utils/stdio/stdio_serial\"",
						"-I\"" + srcASFInclude + "/common/services/serial\"",						
						"-I\"" + srcASFInclude + "/thirdparty/CMSIS/Lib/GCC\"", 
						"-I\"" + srcASFInclude + "/common/utils\"",
						"-I\"" + srcASFInclude + "/sam/boards/same70_xplained\"", 
						"-I\"" + srcASFInclude + "/sam/utils/fpu\"", 
						
						//"-I\"../src\"",
						"-I\"" + srcInclude + "\"",
						
						"-I\"" + srcASFInclude + "/common/services/clock\"",
						"-I\"" + srcASFInclude + "/common/services/fifo\"",
						"-I\"" + srcASFInclude + "/sam/utils/cmsis/same70/source/templates\"", 
						"-I\"" + srcASFInclude + "/sam/drivers/pmc\"",
						"-I\"" + srcASFInclude + "/common/services/delay\"", 
						"-I\"" + srcASFInclude + "/sam/utils\"",
						"-I\"" + srcASFInclude + "/sam/utils/preprocessor\"", 
						"-I\"" + srcASFInclude + "/sam/boards\"",
						"-I\"" + srcASFInclude + "/common/boards\"", 
						"-I\"" + srcASFInclude + "/common/services/gpio\"",
						"-I\"" + srcASFInclude + "/sam/drivers/pio\"", 
						"-I\"" + srcASFInclude + "/sam/utils/header_files\"",
						"-I\"" + srcASFInclude + "/common/services/ioport\"", 
						"-I\"" + srcASFInclude + "/sam/drivers/mpu\"",
						"-I\"" + srcASFInclude + "/thirdparty/CMSIS/Include\"", 
						"-I\"" + srcASFInclude + "/sam/utils/cmsis/same70/include\"",						
					
						//"-I\"C:\\Users\\hso\\same70\\SAME70Xplained-sandbox\\src\\config\"",
						"-I\"" + srcInclude + "/config\"", 						
						//"-I" + ConfigPath.SAME_src_config,
						
						"-O0", "-g", "-fdata-sections", "-ffunction-sections", "-mlong-calls", "-Wall", "-mcpu=cortex-m7",
						"-pipe", "-fno-strict-aliasing", "-Wall", "-Wstrict-prototypes", "-Wmissing-prototypes",
						"-Werror-implicit-function-declaration", "-Wpointer-arith", "-std=gnu99", "-ffunction-sections",
						"-fdata-sections", "-Wchar-subscripts", "-Wcomment", "-Wformat=2", "-Wimplicit-int", "-Wmain",
						"-Wparentheses", "-Wsequence-point", "-Wreturn-type", "-Wswitch", "-Wtrigraphs", "-Wunused",
						"-Wuninitialized", "-Wunknown-pragmas", "-Wfloat-equal", "-Wundef", "-Wshadow",
						"-Wbad-function-cast", "-Wwrite-strings", "-Wsign-compare", "-Waggregate-return",
						"-Wmissing-declarations", "-Wformat", "-Wmissing-format-attribute",
						"-Wno-deprecated-declarations", "-Wpacked", "-Wnested-externs",
						"-Wlong-long", "-Wunreachable-code", "-Wcast-align", "--param", "max-inline-insns-single=500",
						"-mfloat-abi=softfp", "-mfpu=fpv5-sp-d16", 	
						//ConfigPath.GCC_W_OPTIONS,
						
						"natives_arm.c" },
				
				new String[] {
						ATSAMe70Config.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-gcc.exe",
						"-o", 
						"main.elf", 
						"*.o", 
						debugSrcASF + "/common/services/clock/same70/sysclk.o", 
						debugSrcASF + "/common/services/serial/usart_serial.o",
						debugSrcASF + "/common/utils/interrupt/interrupt_sam_nvic.o",
						debugSrcASF + "/common/utils/stdio/read.o",
						debugSrcASF + "/common/utils/stdio/write.o",
						debugSrcASF + "/sam/boards/same70_xplained/init.o",
						debugSrcASF + "/sam/drivers/mpu/mpu.o",
						debugSrcASF + "/sam/drivers/pwm/pwm.o",
						debugSrcASF + "/sam/drivers/pio/pio.o",
						debugSrcASF + "/sam/drivers/pio/pio_handler.o",
						debugSrcASF + "/sam/drivers/pmc/pmc.o",
						debugSrcASF + "/sam/drivers/pmc/sleep.o",
						debugSrcASF + "/sam/drivers/uart/uart.o",
						debugSrcASF + "/sam/drivers/usart/usart.o",									
						debugSrcASF + "/sam/utils/cmsis/same70/source/templates/gcc/startup_same70.o",
						debugSrcASF + "/sam/utils/cmsis/same70/source/templates/system_same70.o",
						debugSrcASF + "/sam/utils/syscalls/gcc/syscalls.o",	
						debugSrcASF + "/../console_setup.o",
						"-mthumb", 
						"-Wl,-Map=\"main.map\"", 
						"-Wl,--start-group",
						"-larm_cortexM7lfsp_math_softfp", 
						"-lm", 
						"-Wl,--end-group",
						"-L\"" + trim(getSrcASFLocation()) + "/thirdparty/CMSIS/Lib/GCC\"", 
						"-Wl,--gc-sections",
						"-mcpu=cortex-m7", 
						"-Wl,--entry=Reset_Handler", 
						"-Wl,--cref", 
						"-mthumb",
						"-T" + trim(getSrcASFLocation()) + "/sam/utils/linker_scripts/same70/same70q21/gcc/flash.ld" },
				
				new String[] {
						ATSAMe70Config.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-nm.exe",
						"--print-size", "--size-sort", "main.elf" },
				new String[] {
						ATSAMe70Config.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-size.exe",
						"-d", "-A", "main.elf" },
				new String[] {
						ATSAMe70Config.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-strip.exe",
						"main.elf" },
				new String[] { ATSAMe70Config.ATMEL_STUDIO + File.separator + "atbackend"  + File.separator + "atprogram.exe", "-t", "edbg",
						"-i", "SWD", "-d", "atsame70q21", "program", "-f", "main.elf", }, 
				new String[] { "cmd", "/c", "del", "main.elf" } 
				};		
	}
	
	
	@IcecapInlineNative(functionBody = ""
			+ "{\n"
			+ "   sysclk_init();\n"
			+ "   board_init();\n"
			+ "   configure_console();\n"
			+ "   front_light_init();\n"
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = ""
					+ "#include \"..\\asf.h\"\n"
					+ "#include \"..\\console_setup.h\"\n"
					+ "#include \"..\\scalextric\\front_light.h\"\n"
		)
	protected static native void initNative();

	@IcecapCompileMe
	protected static void init()
	{
		if (!initialized)
		{
			initNative();
			initialized = true;
		}
	}
	
	private String trim(String asfLocation) {
		if (asfLocation.endsWith(File.separator)) {
			return trim(asfLocation.substring(0, asfLocation.length() - 1));
		} else {
			return asfLocation;
		}
	}

	@Override
	public String getDeployCommand() {
		return null;
	}

	@Override
	public int getJavaHeapSize() {
		return 8192;
	}
		
	@IcecapCompileMe
	protected static void blink(int i) {
		if (!initialized)
		{
			initialized = true;
		}

		for (int j = 0; j < 3000; j++) {
			//toggle_led();
			devices.System.delay(i);
		}
	}
	
	@IcecapCompileMe
	protected static void delay(int i) {

		for (int j = 0; j < 2000; j++) {
			devices.System.delay(i);
		}
	}

//	@IcecapInlineNative(functionBody = ""
//			+ "{\n"
//			+ "   return -1;\n"
//			+ "}\n",
//			requiredIncludes = "#include \"..\\asf.h\"\n"
//			)
//	protected static native void toggle_led();
	
	//@IcecapCompileMe
	protected static void turnOnFrontLight() {
		
		front_light_turn_on();
	}

	@IcecapInlineNative(functionBody = ""
			+ "{\n"
			+ "   front_light_turn_on();\n"
			+ "   front_light_low_beam();\n"
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = ""
				+ "#include \"..\\scalextric\\front_light.h\"\n"
			)
	protected static native void front_light_turn_on();
	
	//@IcecapCompileMe
	protected static void turnOffFrontLight() {
		
		front_light_turn_off();
	}

	@IcecapInlineNative(functionBody = ""
			+ "{\n"
			+ "   front_light_turn_off();\n"
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = ""
				+ "#include \"..\\scalextric\\front_light.h\"\n"
			)
	protected static native void front_light_turn_off();	
	
	public static class ATSAMe70Writer implements Writer {

		@Override
		public void write(byte[] bytes, short length) {
			for (short i = 0; i < length; i++) {
				putc(bytes[i]);
			}
		}

		@IcecapInlineNative(functionBody = "" 
				+ "{\n" 
				+ "   putchar(sp[0] & 0xff);\n" 
				+ "   return -1;\n"
				+ "}\n", requiredIncludes = "#include <stdio.h>\n")
		public static native void putc(byte b);

		@Override
		public short getMaxLineLength() {
			return 128;
		}
	}
	
//	protected String getSrcASFLocation() {
//		//return ConfigPath.SAME_src_ASF;
//		return "../ASF";
//	}
//	
//	protected String getDebugSrcASFLocation() {
//		//return ConfigPath.SAME_DEBUG_ASF_src;
//		return "../../Debug/src/ASF";
//	}
//	
//	protected String getSrcLocation() {
//		//return ConfigPath.SAME_ASF_src;
//		return "../../src";
//	}
//	
//	public String getOutputFolder() { 
//		return ConfigPath.OUTPUT_FOLDER;
//	}


	protected String getSrcASFLocation() {	
		return ATSAMe70Config.SAME_src_ASF;
	}
	
	protected String getDebugSrcASFLocation() {
		return ATSAMe70Config.SAME_Debug_src_ASF;
	}
	
	protected String getSrcLocation() {
		return ATSAMe70Config.SAME_src;
	}

	@Override
	public String getOutputFolder() { 
		return ATSAMe70Config.OUTPUT_FOLDER;
	}
	
	
}
