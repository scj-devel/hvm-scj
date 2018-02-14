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
import util.StringUtil;
import vm.Machine;
import vm.RealtimeClock.DefaultRealtimeClock;

public abstract class ATSAMe70TargetConfiguration extends BaseTargetConfiguration implements TargetConfiguration {

	public static class ATSAMe70RealtimeClock extends DefaultRealtimeClock {

	}

	protected static boolean initialized = false;
	
	static {
		Machine.setMachineFactory(new ATSAMe70MachineFactory());
		Console.writer = new ATSAMe70Writer();
	}
	
	@Override
	public String[][] getBuildCommands() {
		//String ASF = trim(getASFLocation());
		String srcASFInclude = trim(getSrcASFLocation());
		String debugSrcASF = trim(getDebugSrcASFLocation());
		String srcInclude = trim(getSrcLocation());
		
		return new String[][] {
				
			new String[] {
				ATSAMe70Config.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-gcc.exe",
				
				"-DPC32", "-c", "-DJAVA_STACK_SIZE=420", "-x", "c", "-mthumb", "-D__SAME70Q21__",
				"-DDEBUG", "-DPACKED=", "-D__SAME70Q21__", "-DBOARD=SAME70_XPLAINED", "-Dscanf=iscanf",
				"-DARM_MATH_CM7=true", "-Dprintf=iprintf",	
				
				"-I\"" + srcInclude + "\"",
				"-I\"" + srcInclude + "/config\"",
				"-I\"" + srcASFInclude+ "\"",
				
				"-I\"" + srcASFInclude + "/sam/drivers/uart\"",
				"-I\"" + srcASFInclude + "/sam/drivers/usart\"",
				//"-I\"" + srcASFInclude + "/sam/drivers/spi\"",
				"-I\"" + srcASFInclude + "/sam/drivers/pwm\"",
				"-I\"" + srcASFInclude + "/common/utils/stdio/stdio_serial\"",
				"-I\"" + srcASFInclude + "/common/services/serial\"",						
				"-I\"" + srcASFInclude + "/thirdparty/CMSIS/Lib/GCC\"", 
				"-I\"" + srcASFInclude + "/common/utils\"",
				"-I\"" + srcASFInclude + "/sam/boards/same70_xplained\"", 
				"-I\"" + srcASFInclude + "/sam/utils/fpu\"", 				
				"-I\"" + srcASFInclude + "/common/services/clock\"",
				//"-I\"" + srcASFInclude + "/common/services/fifo\"",
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
				ATSAMe70Config.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-gcc.exe",
				"-o", 
				"main.elf", 
				"*.o", 
				//debugSrcASF + "/spi/iha_spi.o", 
				//debugSrcASF + "/serial/iha_serial.o", 
				//debugSrcASF + "/dialog_handler/dialog_handler.o", 
				//debugSrcASF + "/car_driver/mpu_9520.o", 
				//debugSrcASF + "/car_driver/bt_module.o", 						
				debugSrcASF + "/common/services/clock/same70/sysclk.o", 
				//debugSrcASF + "/common/services/fifo/fifo.o", 
				//debugSrcASF + "/common/services/delay/sam/cycle_counter.o",
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
				//debugSrcASF + "/sam/drivers/spi/spi.o",					
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
				"-T" + srcASFInclude + "/sam/utils/linker_scripts/same70/same70q21/gcc/flash.ld" },
		new String[] {
				ATSAMe70Config.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-nm.exe",
				"--print-size", "--size-sort", "main.elf" },
		new String[] {
				ATSAMe70Config.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-size.exe",
				"-d", "-A", "main.elf" },
		new String[] {
				ATSAMe70Config.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-strip.exe",
				"main.elf" },
		new String[] { ATSAMe70Config.ATMEL_STUDIO + File.separator + "atbackend" + File.separator + "atprogram.exe", 
				"-t", "edbg", "-i", "SWD", "-d", "atsame70q21", "program", "-f", "main.elf" }, 
		new String[] { "cmd", "/c", "del", "main.elf" } 
		};		
	}

//	@IcecapCFunc(signature = "void bt_uart_init(void)", 
//			 requiredIncludes = "void bt_uart_init(void);\n")
//	@IcecapInlineNative(functionBody = "" + 
//		"{\n" + 
//		"   pio_configure (PIOD, PIO_PERIPH_A, (PIO_PD28A_URXD3 | PIO_PD30A_UTXD3), PIO_DEFAULT);\n" +
//		"   bt_serial = serial_new_instance (UART3, 115200L, ser_NO_PARITY, 32, NULL);\n" +
//		"   NVIC_ClearPendingIRQ (UART3_IRQn);\n" +
//		"   NVIC_DisableIRQ (UART3_IRQn);\n" +
//		"   NVIC_SetPriority (UART3_IRQn, 1);\n" +
//		"   NVIC_EnableIRQ (UART3_IRQn);\n" + 
//		"}\n",
//		requiredIncludes = "static serial_p bt_serial = NULL;\n")
//	private static native void bt_uart_init();
//	
//	@IcecapCFunc(signature = "void bluetooth_call_back (uint8_t status, uint8_t byte)", 
//			 requiredIncludes = "void bluetooth_call_back (uint8_t status, uint8_t byte);\n")
//	@IcecapInlineNative(functionBody = "" + 
//		"{\n" + 
//		"   if (status == BT_CONNECTED) {\n" +
//	    "   } else if (status == DIALOG_OK_STOP) {\n" +
//		"      bt_initialised = 1;\n" + 
//		"   } else if (status == DIALOG_ERROR_STOP) {\n" +
//		"   }\n" + 
//		"}\n",
//		requiredIncludes = "static serial_p bt_serial = NULL;\n")
//	private static native void bluetooth_call_back();
	
//	@IcecapInlineNative(functionBody = ""
//			+ "{\n"
//			+ "   sysclk_init();\n"
//			+ "   board_init();\n"
//			+ "   mpu_9520_init();\n"
//			+ "   bt_uart_init();\n"
//			+ "   bt_module_init (bluetooth_call_back, bt_serial);\n"
//			+ "   ioport_init();\n"
//			+ "   delay_init(sysclk_get_cpu_hz());\n"
//			+ "   return -1;\n"
//			+ "}\n",
//			requiredIncludes = ""
//					+ "#include \"car_driver/car_driver.h\"\n")
//	protected static native void initNative();
	
	@IcecapInlineNative(functionBody = ""
			+ "{\n"
			+ "   sysclk_init();\n"
			+ "   board_init();\n"
			+ "   configure_console();\n"
			//+ "   front_light_init();\n"
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = ""
					+ "#include \"..\\asf.h\"\n"
					+ "#include \"..\\console_setup.h\"\n"
					//+ "#include \"..\\scalextric\\front_light.h\"\n"
		)
	protected static native void initNative();


//	@IcecapInlineNative(functionBody = ""
//			+ "{\n"
//			+ "  const usart_serial_options_t uart_serial_options = {\n"
//			+ "  .baudrate = CONF_UART_BAUDRATE,\n"
//			+ "  #ifdef CONF_UART_CHAR_LENGTH\n"
//			+ "  .charlength = CONF_UART_CHAR_LENGTH,\n"
//			+ "  #endif\n"
//			+ "  .paritytype = CONF_UART_PARITY,\n"
//			+ "  #ifdef CONF_UART_STOP_BITS\n"
//			+ "  .stopbits = CONF_UART_STOP_BITS,\n"
//			+ "  #endif\n"
//			+ "  };\n"
//			+ "  \n"
//			+ "  ioport_set_pin_peripheral_mode(USART1_RXD_GPIO, USART1_RXD_FLAGS);\n"
//			+ "  MATRIX->CCFG_SYSIO |= CCFG_SYSIO_SYSIO4;\n"
//			+ "  ioport_set_pin_peripheral_mode(USART1_TXD_GPIO, USART1_TXD_FLAGS);\n"
//			+ "  sysclk_enable_peripheral_clock(CONSOLE_UART_ID);\n"
//			+ "  stdio_serial_init(CONF_UART, &uart_serial_options);\n"
//			+ "  return -1;\n"
//			+ "}\n", 
//			requiredIncludes = ""
//			+ "#include <conf_uart_serial.h>\n"
//			+ "#define ioport_set_pin_peripheral_mode(pin, mode) \\\n"
//			+ "do {\\\n"
//			+ "ioport_set_pin_mode(pin, mode);\\\n"
//			+ "ioport_disable_pin(pin);\\\n"
//			+ "} while (0)\n")
//	protected static native void initUart();

	@IcecapCompileMe
	protected static void init()
	{
		if (!initialized)
		{
			initNative();
			
			//initUart();
			//bt_initialised = 0;
			
			systemTick = 0;
			systemClock = 0;
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
		
		set_led_output();

		while (true) {
			toggle_led();
			devices.System.delay(i);
		}
	}

	@IcecapCompileMe
	protected static void blinkPD21(int i) {
		if (!initialized)
		{
			initialized = true;
		}
		
		set_pd21_output();

		while (true) {
			toggle_pd21();
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

	@IcecapInlineNative(functionBody = ""
			+ "{\n"
			+ "   ioport_toggle_port_level(EXAMPLE_PD21_PORT, EXAMPLE_PD21_MASK);\n"
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = "#include \"asf.h\"\n"
			)
	protected static native void toggle_pd21();

	@IcecapInlineNative(functionBody = ""
			+ "{\n"
			+ "   ioport_set_port_dir(EXAMPLE_PD21_PORT, EXAMPLE_PD21_MASK,IOPORT_DIR_OUTPUT);\n"
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = "#define EXAMPLE_PD21_PORT (3)\n#define EXAMPLE_PD21_MASK ((1 << 21))\n"
			)
	protected static native void set_pd21_output();
	
	@IcecapInlineNative(functionBody = "" + "{\n" + "   SysTick_Config(300000);\n" + "   return -1;\n" + "}\n",
			requiredIncludes = "#include \"asf.h\"\n")
	protected static native void initSystemTick();
	
	@IcecapCVar(expression = "systemTick", requiredIncludes = "extern volatile uint8 systemTick;")
	private static byte systemTick;
	
	/* This clock will run for approx 49 days before overrun */
	@IcecapCVar(expression = "systemClock", requiredIncludes = "extern volatile uint32 systemClock;")
	protected static int systemClock;
	
//	@IcecapCVar(expression = "bt_initialised", requiredIncludes = "static volatile uint8 bt_initialised;")
//	protected static byte bt_initialised;

//	@IcecapCFunc(signature = "void SysTick_Handler(void)", 
//				 requiredIncludes = "#include \"car_driver/bt_module.h\"\n")
//	@IcecapInlineNative(functionBody = "" + 
//			"{\n" + 
//			"   static uint8_t ms100_count = 100;\n" +
//			"   if (!bt_initialised) {\n" +
//			"      if (--ms100_count == 0) {\n" +
//			"	      ms100_count = 100;\n" +
//			"		  bt_tick();\n" +
//			"      }\n" +
//		    "   }\n" +
//			"   systemTick++;\n" + 
//			"   systemClock++;\n" + 
//			"}\n",
//	requiredIncludes = "#include \"asf.h\"\n")
//	private static native void handleSystemTick();
	
//	@IcecapInlineNative(functionBody = "" 
//			+ "{\n" 
//			+ "   poll_mpu_9520();\n" 
//			+ "   return -1;\n" 
//			+ "}\n", 
//			requiredIncludes = "#include \"car_driver/car_driver.h\"\n")
//	public static native void poll_mpu_9520();
//	
//	@IcecapInlineNative(functionBody = "" 
//			+ "{\n" 
//			+ "   sp[0] = get_raw_x_accel();\n" 
//			+ "   return -1;\n" 
//			+ "}\n", 
//			requiredIncludes = "#include \"car_driver/car_driver.h\"\n")
//	public static native short get_raw_x_accel();
//	
//	@IcecapInlineNative(functionBody = "" 
//			+ "{\n" 
//			+ "   sp[0] = get_raw_y_accel();\n" 
//			+ "   return -1;\n" 
//			+ "}\n", 
//			requiredIncludes = "#include \"car_driver/car_driver.h\"\n")
//	public static native short get_raw_y_accel();
//	
//	@IcecapInlineNative(functionBody = "" 
//			+ "{\n" 
//			+ "   sp[0] = get_raw_z_accel();\n" 
//			+ "   return -1;\n" 
//			+ "}\n", 
//			requiredIncludes = "#include \"car_driver/car_driver.h\"\n")
//	public static native short get_raw_z_accel();
//	
//	public static void bt_send_bytes(String string) {
//		byte[] bytes = StringUtil.getBytes(string, false); 
//		blue_tooth_send(bytes, bytes.length);
//	}
//	
//	@IcecapInlineNative(functionBody = "" 
//			+ "{\n" 
//			+ "   char* str = (char*)(pointer)sp[0]\n;"
//			+ "   bt_send_bytes((uint8_t*)(str + sizeof(Object) + 2), sp[1]);\n"
//			+ "   return -1;\n" 
//			+ "}\n", 
//			requiredIncludes = "#include \"car_driver/bt_module.h\"\n")
//	private static native void blue_tooth_send(byte[] bytes, int length);

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
	
//	protected String getASFLocation() {
//		return "../ASF";
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
