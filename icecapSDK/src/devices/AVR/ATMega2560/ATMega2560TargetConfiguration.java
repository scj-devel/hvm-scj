package devices.AVR.ATMega2560;

import devices.TargetConfiguration;
import util.BaseTargetConfiguration;

public abstract class ATMega2560TargetConfiguration extends BaseTargetConfiguration implements TargetConfiguration {

	@Override
	public abstract String getOutputFolder();

	@Override
	public String[] getBuildCommands() {
		return new String[] {
				"avr-gcc -mmcu=atmega2560 -Wall -gdwarf-2 -Os -DF_CPU=10000000 -std=gnu99 -funsigned-char -funsigned-bitfields -fpack-struct -fshort-enums natives_avr.c",
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
}
