package test;

import vm.POSIX64BitMachineFactory;
import vm.VMTest;

public class TestCurrentTimeMillis {

	public static void main(String[] args) {
		new POSIX64BitMachineFactory();

		long start = System.currentTimeMillis();
		long end;

		devices.Console.println("Waiting 1 sec");

		do {
			end = System.currentTimeMillis();
		} while (end - start < 1000);

		devices.Console.println("Done");
		VMTest.markResult(false);
	}
}
