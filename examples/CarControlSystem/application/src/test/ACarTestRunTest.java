package test;


import carcontrol.infrastructure.Adapter;
import test.ACarTest;

public class ACarTestRunTest {

	public static void main(String[] args) {

		/**
		 * Executes the TCK test application on the <code>Adapter</code>.
		 */
		Adapter.instance().execute(ACarTest.class);

	}
}