package test.AVR.ATMega2560;

import devices.AVR.ATMega2560.ATMega2560TargetConfiguration;
import icecaptools.IcecapCompileMe;

public class HelloATMega2560 extends ATMega2560TargetConfiguration {

	@IcecapCompileMe
	public static void main(String[] args) {
		DDRG |= 0x3;

		while (true) {
			PORTG ^= 0x3;
			delay(1000);
		}
	}

	private static void delay(int i) {
		for (short s = 0; s < i; s++) {

		}
	}

	@Override
	public String getOutputFolder() {
		return "/home/skr/hvmsrc";
	}
}
