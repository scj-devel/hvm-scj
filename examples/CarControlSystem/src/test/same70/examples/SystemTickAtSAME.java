package test.same70.examples;


import test.same70.configuration.TargetConfigurationSAME;
import vm.Machine;
import vm.MachineFactory;

public class SystemTickAtSAME extends TargetConfigurationSAME {
	
	public static void main(String[] args) {
		init();

		devices.Console.println("Hello system tick started");
		
		MachineFactory mFac = Machine.getMachineFactory();		
		mFac.startSystemTick();
		
		blink (2000);
		
		devices.Console.println("Hello system tick end");
	}
	
}
