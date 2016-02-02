package test.AVR.ATMega2560;

import devices.AVR.ATMega2560.ATMega2560TargetConfiguration;
import icecaptools.IcecapCompileMe;

public class HelloWorld extends ATMega2560TargetConfiguration {

	@IcecapCompileMe
	public static void main(String[] args) {
		char toggle = 0;

		// DDRA = (byte) 0xFF;

		devices.Console.println("Starting...");
		
		while (true) {
			
			blink(1000);
		}
	}

	@Override
	public String getOutputFolder() {
		return "/home/skr/hvmsrc";
	}

}
