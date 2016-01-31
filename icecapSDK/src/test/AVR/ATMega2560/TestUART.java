package test.AVR.ATMega2560;

import devices.AVR.ATMega2560.ATMega2560TargetConfiguration;
import icecaptools.IcecapCompileMe;

public class TestUART extends ATMega2560TargetConfiguration {

	@IcecapCompileMe
	public static void main(String[] args) {
		char toggle = 0;
		DDRA = (byte) 0xFF;

		while (true) {
			if (toggle == 0) {
				PORTA = (byte) 0xaa;
				toggle = 1;
			} else {
				PORTA = 0x55;
				toggle = 0;
			}
			devices.Console.println("Hello Lisbeth");
			devices.System.delay(8000);
		}
	}

	@Override
	public String getOutputFolder() {
		return "/home/skr/hvmsrc";
	}
}
