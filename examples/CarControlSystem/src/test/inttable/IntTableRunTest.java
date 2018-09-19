package test.inttable;


import carcontrol.infrastructure.Adapter;

public class IntTableRunTest {
	
	public static void main(String[] args) {
		/**
		 * Executes the test application on the <code>Platform</code>.
		 */	
		Adapter.instance().execute(IntTableTest.class);
	}

}