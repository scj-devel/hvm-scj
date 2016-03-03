package devices.arm.ATSAMe70;

import java.io.File;

import devices.TargetConfiguration;
import util.BaseTargetConfiguration;

public abstract class ATSAMe70TargetConfiguration extends BaseTargetConfiguration implements TargetConfiguration {

	@Override
	public String[][] getBuildCommands() {
		return new String[][] {
				new String[] {
						"C:\\Program Files (x86)\\Atmel\\Studio\\7.0\\toolchain\\arm\\arm-gnu-toolchain\\bin\\arm-none-eabi-gcc.exe",
						"-DPC32", "-Os", "-c", "-DJAVA_STACK_SIZE=420", "-x", "c", "-mthumb", "-D__SAME70Q21__",
						"-DDEBUG", "-DPACKED=", "-D__SAME70Q21__", "-DBOARD=SAME70_XPLAINED", "-Dscanf=iscanf",
						"-DARM_MATH_CM7=true", "-Dprintf=iprintf ",
						"-I\"../common/services/ioport/example1/same70q21_same70_xplained\"", "-I\"../src/config\"",
						"-I\"../src/ASF/thirdparty/CMSIS/Lib/GCC\"", "-I\"../src/ASF/common/utils\"",
						"-I\"../src/ASF/sam/boards/same70_xplained\"", "-I\"../src/ASF/sam/utils/fpu\"", "-I\"../src\"",
						"-I\"../src/ASF/common/services/clock\"",
						"-I\"../src/ASF/sam/utils/cmsis/same70/source/templates\"", "-I\"../src/ASF/sam/drivers/pmc\"",
						"-I\"../src/ASF/common/services/delay\"", "-I\"../src/ASF/sam/utils\"",
						"-I\"../src/ASF/sam/utils/preprocessor\"", "-I\"../src/ASF/sam/boards\"",
						"-I\"../src/ASF/common/boards\"", "-I\"../src/ASF/common/services/gpio\"",
						"-I\"../src/ASF/sam/drivers/pio\"", "-I\"../src/ASF/sam/utils/header_files\"",
						"-I\"../src/ASF/common/services/ioport\"", "-I\"../src/ASF/sam/drivers/mpu\"",
						"-I\"../src/ASF/thirdparty/CMSIS/Include\"", "-I\"../src/ASF/sam/utils/cmsis/same70/include\"",
						"-Os", "-fdata-sections", "-ffunction-sections", "-mlong-calls", "-Wall", "-mcpu=cortex-m7",
						"-pipe", "-fno-strict-aliasing", "-Wall", "-Wstrict-prototypes", "-Wmissing-prototypes",
						"-Werror-implicit-function-declaration", "-Wpointer-arith", "-std=gnu99", "-ffunction-sections",
						"-fdata-sections", "-Wchar-subscripts", "-Wcomment", "-Wformat=2", "-Wimplicit-int", "-Wmain",
						"-Wparentheses", "-Wsequence-point", "-Wreturn-type", "-Wswitch", "-Wtrigraphs", "-Wunused",
						"-Wuninitialized", "-Wunknown-pragmas", "-Wfloat-equal", "-Wundef", "-Wshadow",
						"-Wbad-function-cast", "-Wwrite-strings", "-Wsign-compare", "-Waggregate-return",
						"-Wmissing-declarations", "-Wformat", "-Wmissing-format-attribute",
						"-Wno-deprecated-declarations", "-Wpacked", "-Wredundant-decls", "-Wnested-externs",
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
}
