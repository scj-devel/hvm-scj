package test;

import devices.POSIXTargetConfiguration;
import vm.VMTest;

public class TestWrite1 extends POSIXTargetConfiguration {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    public static boolean test() {
        devices.Console.println("hello");
        return false;
    }

	@Override
	public String getOutputFolder() {
		return "/home/skr/hvmsrc";
	}
}
