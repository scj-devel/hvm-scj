package test.same70.configuration;

import java.io.File;

import devices.Console;
import devices.TargetConfiguration;
import devices.Writer;
import icecaptools.IcecapCompileMe;
import icecaptools.IcecapInlineNative;
import util.BaseTargetConfiguration;
import vm.Machine;
import vm.RealtimeClock.DefaultRealtimeClock;

public abstract class BluetoothTargetConfigurationSAME extends BaseTargetConfiguration implements TargetConfiguration {

	public static class ATSAMe70RealtimeClock extends DefaultRealtimeClock {

	}

	protected static boolean initialized = false;
	
	static {
		Machine.setMachineFactory(new MachineFactorySAME());
		Console.writer = new ATSAMe70Writer();
	}

	@Override
	public int getJavaHeapSize() {
		return 8192;  // HSO: remember to set this heap size in 
                      // sameProject -> Toolchain ->ARM/GNU C Compiler -> Symbols
	}
	
	@Override
	public String[][] getBuildCommands() {
		String srcASFInclude = trim(getSrcASFLocation());
		String debugSrcASF = trim(getDebugSrcASFLocation());
		String srcInclude = trim(getSrcLocation());
		
		return new String[][] {
				
			new String[] {
				ConfigSAME.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-gcc.exe",
				
				"-DPC32", "-c", "-DJAVA_STACK_SIZE=840", "-x", "c", "-mthumb", "-D__SAME70Q21__",
				"-DDEBUG", "-DPACKED=", "-D__SAME70Q21__", "-DBOARD=SAME70_XPLAINED", "-Dscanf=iscanf",
				"-DARM_MATH_CM7=true", "-Dprintf=iprintf",	

				"-I\"" + srcInclude + "\"",
				"-I\"" + srcInclude + "/config\"",
				"-I\"" + srcASFInclude + "\"",
				
				"-I\"" + srcASFInclude + "/sam/drivers/uart\"",						
				"-I\"" + srcASFInclude + "/sam/drivers/usart\"",						
				"-I\"" + srcASFInclude + "/sam/drivers/spi\"",
				"-I\"" + srcASFInclude + "/sam/drivers/pwm\"",
				"-I\"" + srcASFInclude + "/common/utils/stdio/stdio_serial\"",
				"-I\"" + srcASFInclude + "/common/services/serial\"",						
				"-I\"" + srcASFInclude + "/thirdparty/CMSIS/Lib/GCC\"", 
				"-I\"" + srcASFInclude + "/common/utils\"",
				"-I\"" + srcASFInclude + "/sam/boards/same70_xplained\"", 
				"-I\"" + srcASFInclude + "/sam/utils/fpu\"",						
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
				"natives_arm.c" },
				
			new String[] {
				ConfigSAME.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-gcc.exe",
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
				"-mthumb", 
				"-Wl,-Map=\"main.map\"", 
				"-Wl,--start-group",
				"-larm_cortexM7lfsp_math_softfp", 
				"-lm", 
				"-Wl,--end-group",
				"-L\"" + srcASFInclude + "/thirdparty/CMSIS/Lib/GCC\"", 
				"-Wl,--gc-sections",
				"-mcpu=cortex-m7", 
				"-Wl,--entry=Reset_Handler", 
				"-Wl,--cref", 
				"-mthumb",
				"-T" + srcASFInclude + "/sam/utils/linker_scripts/same70/same70q21/gcc/flash.ld" }, // no \" ??
				
			new String[] {
				ConfigSAME.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-nm.exe",
				"--print-size", "--size-sort", "main.elf" },
			new String[] {
				ConfigSAME.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-size.exe",
				"-d", "-A", "main.elf" },
			new String[] {
				ConfigSAME.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-strip.exe",
				"main.elf" },
			new String[] { 
				ConfigSAME.ATMEL_STUDIO + File.separator + "atbackend"  + File.separator + "atprogram.exe", "-t", "edbg",
				"-i", "SWD", "-d", "atsame70q21", "program", "-f", "main.elf", }, 
			new String[] { "cmd", "/c", "del", "main.elf" } 
		};		
	}
	
	@IcecapInlineNative(functionBody = ""
			+ "{\n"
			+ "   sysclk_init();\n"
			+ "   board_init();\n"
			+ "   configure_console();\n"
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = ""
					+ "#include \"..\\asf.h\"\n"
					+ "#include \"..\\deviceconfig\\console_setup.h\"\n"
					+ "#include \"..\\scalextric\\bluetooth.h\"\n"
		)
	protected static native void initNative();

	@IcecapCompileMe
	protected static void init()
	{
		if (!initialized) {
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
		
	
	@IcecapCompileMe
	protected static void delay(int i) {

		for (int j = 0; j < 2000; j++) {
			devices.System.delay(i);
		}
	}
	
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

	protected String getSrcASFLocation() {	
		return ConfigSAME.SAME_src_ASF;
	}
	
	protected String getDebugSrcASFLocation() {
		return ConfigSAME.SAME_Debug_src_ASF;
	}
	
	protected String getSrcLocation() {
		return ConfigSAME.SAME_src;
	}

	@Override
	public String getOutputFolder() { 
		return ConfigSAME.OUTPUT_FOLDER;
	}
}
