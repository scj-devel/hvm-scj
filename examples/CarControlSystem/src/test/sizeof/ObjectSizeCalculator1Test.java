package test.sizeof;

import carcontrol.util.ObjectSizeCalculator;

public class ObjectSizeCalculator1Test {

	public static void main(String[] args) {
		try {
			System.out.println("main: " + ObjectSizeCalculator.sizeOf(new String("main")));
			System.out.println("main: " + ObjectSizeCalculator.sizeOf("main"));
		}
		catch (IllegalAccessException e) {
			
		}

	}

}
