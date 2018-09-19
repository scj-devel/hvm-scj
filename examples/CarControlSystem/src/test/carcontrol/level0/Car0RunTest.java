package test.carcontrol.level0;


import carcontrol.infrastructure.Adapter;

public class Car0RunTest {
	
	public static void main(String[] args) {
		/**
		 * Executes the test application on the <code>Platform</code>.
		 */	
		Adapter.instance().execute(Car0Test.class);
	}

}