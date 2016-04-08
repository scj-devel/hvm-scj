package devices.arm.ATSAMe70;

import java.io.File;

import devices.Console;
import devices.TargetConfiguration;
import devices.Writer;
import icecaptools.IcecapCFunc;
import icecaptools.IcecapCVar;
import icecaptools.IcecapCompileMe;
import icecaptools.IcecapInlineNative;
import util.BaseTargetConfiguration;
import vm.Machine;
import vm.RealtimeClock.DefaultRealtimeClock;

public abstract class ATSAMe70TargetConfiguration extends BaseTargetConfiguration implements TargetConfiguration {

	public static class ATSAMe70RealtimeClock extends DefaultRealtimeClock {

	}

	private static boolean initialized = false;
	
	static {
		Machine.setMachineFactory(new ATSAMe70MachineFactory());
		Console.writer = new ATSAMe70Writer();
	}
	
	@Override
	public String[][] getBuildCommands() {
		String ASF = trim(getASFLocation());
		return new String[][] {
				new String[] {
						"C:\\Program Files (x86)\\Atmel\\Studio\\7.0\\toolchain\\arm\\arm-gnu-toolchain\\bin\\arm-none-eabi-gcc.exe",
						"-DPC32", "-Os", "-c", "-DJAVA_STACK_SIZE=420", "-x", "c", "-mthumb", "-D__SAME70Q21__",
						"-DDEBUG", "-DPACKED=", "-D__SAME70Q21__", "-DBOARD=SAME70_XPLAINED", "-Dscanf=iscanf",
						"-DARM_MATH_CM7=true", "-Dprintf=iprintf ",
						"-I\"" +  trim(getASFLocation())+ "\"",
						"-I\"" + ASF + "/thirdparty/CMSIS/Lib/GCC\"", "-I\"" + ASF + "/common/utils\"",
						"-I\"" + ASF + "/sam/boards/same70_xplained\"", "-I\"" + ASF + "/sam/utils/fpu\"", "-I\"../src\"",
						"-I\"" + ASF + "/common/services/clock\"",
						"-I\"" + ASF + "/sam/utils/cmsis/same70/source/templates\"", "-I\"" + ASF + "/sam/drivers/pmc\"",
						"-I\"" + ASF + "/common/services/delay\"", "-I\"" + ASF + "/sam/utils\"",
						"-I\"" + ASF + "/sam/utils/preprocessor\"", "-I\"" + ASF + "/sam/boards\"",
						"-I\"" + ASF + "/common/boards\"", "-I\"" + ASF + "/common/services/gpio\"",
						"-I\"" + ASF + "/sam/drivers/pio\"", "-I\"" + ASF + "/sam/utils/header_files\"",
						"-I\"" + ASF + "/common/services/ioport\"", "-I\"" + ASF + "/sam/drivers/mpu\"",
						"-I\"" + ASF + "/thirdparty/CMSIS/Include\"", "-I\"" + ASF + "/sam/utils/cmsis/same70/include\"",
						"-Os", "-fdata-sections", "-ffunction-sections", "-mlong-calls", "-Wall", "-mcpu=cortex-m7",
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
						"-o", "main.elf", "*.o", "-mthumb", "-Wl,-Map=\"main.map\"", "-Wl,--start-group",
						"-larm_cortexM7lfsp_math_softfp", "-lm", "-latsame70", "-Wl,--end-group",
						"-L\"" + trim(getASFLocation()) + "\"",
						"-L\"" + trim(getASFLocation()) + "/thirdparty/CMSIS/Lib/GCC\"", "-Wl,--gc-sections",
						"-mcpu=cortex-m7", "-Wl,--entry=Reset_Handler", "-Wl,--cref", "-mthumb",
						"-T" + trim(getASFLocation()) + "/sam/utils/linker_scripts/same70/same70q21/gcc/flash.ld" },
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
						"-i", "SWD", "-d", "atsame70q21", "program", "-f", "main.elf", } };
	}

	
	@IcecapInlineNative(functionBody = ""
			+ "{\n"
			+ "   sysclk_init();\n"
			+ "   board_init();\n"
			+ "   ioport_init();\n"
			+ "   delay_init(sysclk_get_cpu_hz());\n"
			+ "   return -1;\n"
			+ "}\n"
			)
	private static native void initNative();

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

	protected abstract String getASFLocation();

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
		
		set_led_output();

		while (true) {
			toggle_led();
			devices.System.delay(i);
		}
	}
	
	@IcecapInlineNative(functionBody = ""
			+ "{\n"
			+ "   ioport_toggle_port_level(EXAMPLE_LED_PORT, EXAMPLE_LED_MASK);\n"
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = "#include \"asf.h\"\n"
			)
	protected static native void toggle_led();

	@IcecapInlineNative(functionBody = ""
			+ "{\n"
			+ "   ioport_set_port_dir(EXAMPLE_LED_PORT, EXAMPLE_LED_MASK,IOPORT_DIR_OUTPUT);\n"
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = "#define EXAMPLE_LED_PORT (2)\n#define EXAMPLE_LED_MASK ((1 << 8))\n"
			)
	protected static native void set_led_output();

	@IcecapInlineNative(functionBody = "" + "{\n" + "   SysTick_Config(30000000);\n" + "   return -1;\n" + "}\n")
	static native void initSystemTick();
	
	@IcecapCVar(expression = "systemTick", requiredIncludes = "extern volatile uint8 systemTick;")
	private static byte systemTick;
	
	@IcecapCVar(expression = "systemClock", requiredIncludes = "extern volatile uint32 systemClock;")
	private static byte systemClock;
	
	@IcecapCFunc(signature = "void SysTick_Handler(void)" /*, requiredIncludes = "static int32 fp[4];"*/)
	private static void handleSystemTick() {
		systemTick++;
		systemClock++;
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
				+ "   putchar(sp[0] && 0xFF);\n"
				+ "   return -1;\n"
				+ "}\n",
				requiredIncludes = "#include <stdio.h>\n"
				)
		private static native void putc(byte b);

		@Override
		public short getMaxLineLength() {
			return 0;
		}
	}
}
