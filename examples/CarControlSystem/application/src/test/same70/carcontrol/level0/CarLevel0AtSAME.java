package test.same70.carcontrol.level0;


import javax.safetycritical.LauncherAtSame70;
import javax.safetycritical.annotate.Level;
import javax.scj.util.Const;

import carcontrol.scjlevel0.Car;
import test.same70.configuration.SCJTargetConfigurationSAME;
import vm.Machine;
import vm.MachineFactory;
import vm.Memory;

public class CarLevel0AtSAME extends SCJTargetConfigurationSAME {  

	@Override
	public int getJavaHeapSize() {
		return 300000;  // HSO: remember to set this heap size in 
                        // sameProject -> Toolchain ->ARM/GNU C compiler -> Symbols
	}
	
	public static void main(String[] args) {
		
		init();
		devices.Console.println("CarLevel0AtSAME started");
		
		MachineFactory mFac = Machine.getMachineFactory();
		mFac.startSystemTick();
		
		Const.OVERALL_BACKING_STORE = 250 * 1000;
		Const.IMMORTAL_MEM = 16 * 1000; //60*1000; // 40*1000;
		Const.MISSION_MEM = 2*1000; //5 * 1000; //10*1000;
		Const.PRIVATE_MEM = 4*1000; // 5*1000;
		
		Const.HANDLER_STACK_SIZE = 1024; // 420; // 2048;
		
		Const.MEMORY_TRACKER_AREA_SIZE = 15*1000;
		Const.CYCLIC_SCHEDULER_STACK_SIZE = 1024; // 420; // 2048; 
		
		Memory.startMemoryAreaTracking();
		vm.Process.enableStackAnalysis();		

		devices.Console.println("Setup complete - launching");
		
		new LauncherAtSame70 (Level.LEVEL_0, Car.class, mFac);  // works, uses MachineFactorySAME()
		
		vm.Process.reportStackUsage();
		Memory.reportMemoryUsage();
		
		delay (2000);
		blink (2000);
		devices.Console.println("CarLevel0AtSAME end");
	}
	
	
}
