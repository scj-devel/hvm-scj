package test.same70.examples;


import javax.safetycritical.LaunchLevel0;
import javax.safetycritical.LauncherAtSame70;
import javax.safetycritical.annotate.Level;
import javax.scj.util.Const;

import test.same70.configuration.SCJTargetConfigurationSAME;
import vm.Machine;
import vm.MachineFactory;
import vm.Memory;

public class SCJLevel0AtSAME extends SCJTargetConfigurationSAME {  

	@Override
	public int getJavaHeapSize() {
		return 256000;  // HSO: remember to set this heap size in 
                        // sameProject -> Toolchain ->ARM/GNU C compiler -> Symbols
	}
	
	public static void main(String[] args) {
		
		init();
		devices.Console.println("SCJLevel0AtSAME started");
		
		MachineFactory mFac = Machine.getMachineFactory();
		
		Const.OVERALL_BACKING_STORE = 200 * 1000;
		Const.IMMORTAL_MEM = 40*1000;
		Const.MISSION_MEM = 10*1000;
		Const.PRIVATE_MEM = 1000;
		
		Const.HANDLER_STACK_SIZE = 1024; // 420; // 2048;
		
		Const.MEMORY_TRACKER_AREA_SIZE = 10*1000;
		Const.CYCLIC_SCHEDULER_STACK_SIZE = 1024; // 420; // 2048; 
		
		Memory.startMemoryAreaTracking();
		vm.Process.enableStackAnalysis();

		//SCJLevel0 app = new SCJLevel0();

		devices.Console.println("Setup complete - launching");
		
		//new LaunchLevel0(app, mFac);
		
		new LauncherAtSame70 (Level.LEVEL_0, SCJLevel0.class, mFac);  // works, uses MachineFactorySAME()
		
		vm.Process.reportStackUsage();
		Memory.reportMemoryUsage();
		
		blink (2000);
		devices.Console.println("SCJLevel0AtSAME end");
	}
	
	
}
