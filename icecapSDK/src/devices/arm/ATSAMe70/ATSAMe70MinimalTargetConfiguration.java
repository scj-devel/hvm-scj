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
		String ASFInclude = trim(getASFIncludeLocation());
		String ASFObject = trim(getASFObjectLocation());
		return new String[][] {
				
				new String[] {
						"C:\\Program Files (x86)\\Atmel\\Studio\\7.0\\toolchain\\arm\\arm-gnu-toolchain\\bin\\arm-none-eabi-gcc.exe",
						"-DPC32", "-Os", "-c", "-DJAVA_STACK_SIZE=420", "-x", "c", "-mthumb", "-D__SAME70Q21__",
						"-DDEBUG", "-DPACKED=", "-D__SAME70Q21__", "-DBOARD=SAME70_XPLAINED", "-Dscanf=iscanf",
						"-DARM_MATH_CM7=true", "-Dprintf=iprintf ",
						"-I\"" +  trim(getASFIncludeLocation())+ "\"",
						"-I\"" + ASFInclude + "/sam/drivers/uart\"",
						"-I\"" + ASFInclude + "/sam/drivers/usart\"",
						"-I\"" + ASFInclude + "/sam/drivers/spi\"",
						"-I\"" + ASFInclude + "/sam/drivers/pwm\"",
						"-I\"" + ASFInclude + "/common/utils/stdio/stdio_serial\"",
						"-I\"" + ASFInclude + "/common/services/serial\"",						
						"-I\"" + ASFInclude + "/thirdparty/CMSIS/Lib/GCC\"", "-I\"" + ASFInclude + "/common/utils\"",
						"-I\"" + ASFInclude + "/sam/boards/same70_xplained\"", "-I\"" + ASFInclude + "/sam/utils/fpu\"", "-I\"../src\"",
						"-I\"" + ASFInclude + "/common/services/clock\"",
						"-I\"" + ASFInclude + "/common/services/fifo\"",
						"-I\"" + ASFInclude + "/sam/utils/cmsis/same70/source/templates\"", "-I\"" + ASFInclude + "/sam/drivers/pmc\"",
						"-I\"" + ASFInclude + "/common/services/delay\"", "-I\"" + ASFInclude + "/sam/utils\"",
						"-I\"" + ASFInclude + "/sam/utils/preprocessor\"", "-I\"" + ASFInclude + "/sam/boards\"",
						"-I\"" + ASFInclude + "/common/boards\"", "-I\"" + ASFInclude + "/common/services/gpio\"",
						"-I\"" + ASFInclude + "/sam/drivers/pio\"", "-I\"" + ASFInclude + "/sam/utils/header_files\"",
						"-I\"" + ASFInclude + "/common/services/ioport\"", "-I\"" + ASFInclude + "/sam/drivers/mpu\"",
						"-I\"" + ASFInclude + "/thirdparty/CMSIS/Include\"", "-I\"" + ASFInclude + "/sam/utils/cmsis/same70/include\"",
						
						"-I" + ConfigPath.SAME_CONFIG_src,
						//"-I\"C:\\Users\\hso\\same70\\SAME70Xplained-sandbox\\src\\config\"",
						
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
						"-mfloat-abi=softfp", "-mfpu=fpv5-sp-d16", "natives_arm.c" },
				new String[] {
						"C:\\Program Files (x86)\\Atmel\\Studio\\7.0\\toolchain\\arm\\arm-gnu-toolchain\\bin\\arm-none-eabi-gcc.exe",
						"-o", 
						"main.elf", 
						"*.o", 
						ASFObject + "/common/services/clock/same70/sysclk.o", 
						ASFObject + "/common/services/serial/usart_serial.o",
						ASFObject + "/common/utils/interrupt/interrupt_sam_nvic.o",
						ASFObject + "/common/utils/stdio/read.o",
						ASFObject + "/common/utils/stdio/write.o",
						ASFObject + "/sam/boards/same70_xplained/init.o",
						ASFObject + "/sam/drivers/mpu/mpu.o",
						ASFObject + "/sam/drivers/pwm/pwm.o",
						ASFObject + "/sam/drivers/pio/pio.o",
						ASFObject + "/sam/drivers/pio/pio_handler.o",
						ASFObject + "/sam/drivers/pmc/pmc.o",
						ASFObject + "/sam/drivers/pmc/sleep.o",
						ASFObject + "/sam/drivers/uart/uart.o",
						ASFObject + "/sam/drivers/usart/usart.o",									
						ASFObject + "/sam/utils/cmsis/same70/source/templates/gcc/startup_same70.o",
						ASFObject + "/sam/utils/cmsis/same70/source/templates/system_same70.o",
						ASFObject + "/sam/utils/syscalls/gcc/syscalls.o",	
						ASFObject + "/../console_setup.o",
						"-mthumb", 
						"-Wl,-Map=\"main.map\"", 
						"-Wl,--start-group",
						"-larm_cortexM7lfsp_math_softfp", 
						"-lm", 
						"-Wl,--end-group",
						"-L\"" + trim(getASFIncludeLocation()) + "/thirdparty/CMSIS/Lib/GCC\"", 
						"-Wl,--gc-sections",
						"-mcpu=cortex-m7", 
						"-Wl,--entry=Reset_Handler", 
						"-Wl,--cref", 
						"-mthumb",
						"-T" + trim(getASFIncludeLocation()) + "/sam/utils/linker_scripts/same70/same70q21/gcc/flash.ld" },
				new String[] {
						"C:\\Program Files (x86)\\Atmel\\Studio\\7.0\\toolchain\\arm\\arm-gnu-toolchain\\bin\\arm-none-eabi-nm.exe",
						"--print-size", "--size-sort", "main.elf" },
				new String[] {
						"C:\\Program Files (x86)\\Atmel\\Studio\\7.0\\toolchain\\arm\\arm-gnu-toolchain\\bin\\arm-none-eabi-size.exe",
						"-d", "-A", "main.elf" },
				new String[] {
						"C:\\Program Files (x86)\\Atmel\\Studio\\7.0\\toolchain\\arm\\arm-gnu-toolchain\\bin\\arm-none-eabi-strip.exe",
						"main.elf" },
				new String[] { "C:\\Program Files (x86)\\Atmel\\Studio\\7.0\\atbackend\\atprogram.exe", "-t", "edbg",
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
					+ "#include \"..\\console_setup.h\"\n")
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

		while (true) {
			toggle_led();
			devices.System.delay(i);
		}
	}

	@IcecapInlineNative(functionBody = ""
			+ "{\n"
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = "#include \"..\\asf.h\"\n"
			)
	protected static native void toggle_led();

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
	
	protected String getASFIncludeLocation() {
		return "../ASF";
	}
	
	protected String getASFObjectLocation() {
		return "../../Debug/src/ASF";
	}
	
	/* Use this instead of the above */
	
	protected String getASFLocation() {
		return "C:\\Users\\hso\\same70\\SAME70Xplained-sandbox";
	}
}
