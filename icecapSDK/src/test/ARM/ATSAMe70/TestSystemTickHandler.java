package test.ARM.ATSAMe70;

import devices.arm.ATSAMe70.ATSAMe70TargetConfiguration;
import icecaptools.IcecapCFunc;
import icecaptools.IcecapCompileMe;
import vm.Machine;
import vm.MachineFactory;

public class TestSystemTickHandler extends ATSAMe70TargetConfiguration {
	
	public static void main(String[] args) {
		init();
		
		MachineFactory mFac = Machine.getMachineFactory();
		
		mFac.startSystemTick();
		
		set_led_output();
		
		mainLoop();
	}

	@IcecapCompileMe
	private static void mainLoop() {
		while (true)
		{
			if (systemClock > 100)
			{
				toggle_led();
				systemClock = 0;
			}
		}
	}
	
	@Override
	public String getOutputFolder() {
		return "C:\\home\\icecapout";
	}
}
