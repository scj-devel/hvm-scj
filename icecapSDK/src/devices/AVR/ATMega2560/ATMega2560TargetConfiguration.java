package devices.AVR.ATMega2560;

import devices.TargetConfiguration;
import icecaptools.IcecapCVar;
import icecaptools.IcecapCompileMe;
import util.BaseTargetConfiguration;
import vm.Machine;
import vm.MachineFactory;

public abstract class ATMega2560TargetConfiguration extends BaseTargetConfiguration implements TargetConfiguration {

	@IcecapCVar(expression = "DDRA", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte DDRA;
	@IcecapCVar(expression = "PORTA", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte PORTA;
	@IcecapCVar(expression = "DDRB", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte DDRB;
	@IcecapCVar(expression = "PORTB", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte PORTB;
	@IcecapCVar(expression = "DDRC", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte DDRC;
	@IcecapCVar(expression = "PORTC", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte PORTC;
	@IcecapCVar(expression = "DDRD", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte DDRD;
	@IcecapCVar(expression = "PORTD", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte PORTD;
	@IcecapCVar(expression = "DDRE", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte DDRE;
	@IcecapCVar(expression = "PORTE", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte PORTE;
	@IcecapCVar(expression = "DDRF", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte DDRF;
	@IcecapCVar(expression = "PORTF", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte PORTF;
	@IcecapCVar(expression = "DDRG", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte DDRG;
	@IcecapCVar(expression = "PORTG", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte PORTG;
	
	
	@IcecapCVar(expression = "TIMSK0", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte TIMSK0;
	@IcecapCVar(expression = "TCCR0B", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte TCCR0B;
	
	@IcecapCVar(expression = "SREG", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte SREG;
	
	@IcecapCVar(expression = "UBRR2", requiredIncludes = "#include \"avr/io.h\"\n")
	public static short UBRR2;
	@IcecapCVar(expression = "UCSR2A", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte UCSR2A;
	@IcecapCVar(expression = "UCSR2B", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte UCSR2B;
	@IcecapCVar(expression = "UCSR2C", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte UCSR2C;
	@IcecapCVar(expression = "UDR2", requiredIncludes = "#include \"avr/io.h\"\n")
	public static byte UDR2;
	
	protected static final byte TOIE0 = 0;
	protected static final byte CS00 = 0;
	protected static final byte CS01 = 1;
	
	protected static final byte RXEN2 = 4;
	protected static final byte TXEN2 = 3;
	protected static final byte UCSZ20 = 1;
	protected static final byte UDRE2 = 5;
	protected static final byte RXC2 = 7;
	
	static {
		Machine.setMachineFactory(new ATMega2560MachineFactory());
	}
	
	@Override
	public abstract String getOutputFolder();

	@Override
	public String[] getBuildCommands() {
		return new String[] {
				"avr-gcc -mmcu=atmega2560 -Wall -gdwarf-2 -Os -DJAVA_STACK_SIZE=128 -DF_CPU=10000000 -std=gnu99 -funsigned-char -funsigned-bitfields -fpack-struct -fshort-enums natives_avr.c",
				"avr-size -C -x main.exe", "avr-strip main.exe",
				"avr-objcopy -O ihex -R .eeprom -R .fuse -R .lock -R .signature  main.exe main.hex",
				"avrdude -p atmega2560 -c stk600 -P usb -v -v -U flash:w:main.hex" };
	}

	@Override
	public String getDeployCommand() {
		return null;
	}

	@Override
	public int getJavaHeapSize() {
		return 2048;
	}
	
	@IcecapCompileMe
	protected static void blink(int i) {
		DDRG |= 0x3;

		while (true) {
			PORTG ^= 0x3;
			devices.System.delay(i);
		}
	}
	
	@IcecapCompileMe
	protected static void sei()
	{
		SREG |= 0x80;
	}
	
	@IcecapCompileMe
	protected static void sdi()
	{
		SREG &= ~0x80;
	}
	
	protected static int getReasonableProcessStackSize() {
		return 256; /* 1 kB */
	}
	
	@Override
	protected EBOOL excludeMethod(String clazz, String targetMethodName, String targetMethodSignature) {
		if (clazz.contains("devices.System"))
		{
			if (targetMethodName.contains("clinit"))
			{
				return EBOOL.YES;
			}
		}
		return EBOOL.DONTCARE;
	}
	
	protected static MachineFactory getConfiguration() {
		return new ATMega2560MachineFactory();
	}
}
