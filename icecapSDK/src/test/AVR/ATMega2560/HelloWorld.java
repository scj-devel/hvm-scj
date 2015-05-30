package test.AVR.ATMega2560;

import devices.AVR.ATMega2560.ATMega2560TargetConfiguration;
import icecaptools.IcecapCompileMe;

public class HelloWorld extends ATMega2560TargetConfiguration {

	@IcecapCompileMe
	public static void main(String[] args) {
		devices.Console.println("Starting...");
		
		blink(1000);
	}

	@Override
	public String getOutputFolder() {
		return "/home/skr/hvmsrc";
	}

}
