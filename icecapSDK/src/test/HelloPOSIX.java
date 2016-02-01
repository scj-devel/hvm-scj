package test;

import devices.POSIXTargetConfiguration;

public class HelloPOSIX extends POSIXTargetConfiguration {

	public static void main(String[] args) {
		devices.Console.println("HelloWorld");
	}

	@Override
	public String getOutputFolder() {
		return "/home/skr/hvmsrc";
	}
}
