package test;

import devices.POSIXTargetConfiguration;
import vm.VMTest;

public class TestWrite2 extends POSIXTargetConfiguration {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VMTest.markResult(test());
	}

	public static boolean test() {
		String str = "tw2";
		if (str.charAt(0) == 't') {
			if (str.charAt(1) == 'w') {
				devices.Console.println(str);
				return false;
			}
		}
		devices.Console.println("Failed!");
		return true;
	}

	@Override
	public String getOutputFolder() {
		return "/home/skr/hvmsrc";
	}
}
