package test;

import vm.Memory;
import vm.VMTest;

public class TestAllocationArea3 {

	private static class ReusableString {
		private static final int REUSABLESTRINGSIZE = 1000;
		Memory stringMemory;

		String string;

		public ReusableString() {
			stringMemory = Memory.allocateInHeap(REUSABLESTRINGSIZE);
		}

		public void set(ReusableStringInitializer initializer) {
			Memory currentMemory = Memory.switchToArea(stringMemory);

			stringMemory.reset(0);

			string = initializer.initialize();

			Memory.switchToArea(currentMemory);
		}

		public String get() {
			return string;
		}
	}

	private static class ReusableStringInitializer  {
		public int number;

		public String message;

		public String initialize() {
			return message + "*" + number;
		}
	}

	public static void main(String[] args) {
		devices.Console.println("TestAllocationArea3");
		
		ReusableString array[] = new ReusableString[4];

		ReusableStringInitializer initializer = new ReusableStringInitializer();

		for (int i = 0; i < array.length; i++) {
			array[i] = new ReusableString();
		}

		Memory mainArea = Memory.getHeapArea();

		int start = mainArea.consumedMemory();

		int count = 0;
		
		while (count < 100) {
			initializer.message = "info:";
			initializer.number = count;
			array[0].set(initializer);
			devices.Console.println(array[0].get());
			count++;
		}

		int end = mainArea.consumedMemory();
		int consumed = (end - start);

		devices.Console.println("Used " + consumed + " bytes");
		
		VMTest.markResult(consumed != 0);
	}
}
