package test.same70.examples;

import test.same70.configuration.MinimalTargetConfigurationSAME;;


public class HelloAtSAME extends MinimalTargetConfigurationSAME {

	public static void main(String[] args) {
		init();  // initialize the board

		devices.Console.println("HelloAtSAME started"); // output to HTerm
		
		turnOnFrontLight();   // light diode lights up 
		delay (10000);		  // a short delay
		turnOffFrontLight();  // light diode goes out
		
		devices.Console.println("HelloAtSAME end"); // output to HTerm
	}
}
