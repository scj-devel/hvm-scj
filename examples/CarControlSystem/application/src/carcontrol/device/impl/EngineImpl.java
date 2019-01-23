package carcontrol.device.impl;

import carcontrol.device.Engine;
import carcontrol.device.FrontLight;
import icecaptools.IcecapInlineNative;

public class EngineImpl implements Engine {

	FrontLight frontLight;
	
	int currentSpeedPct;
	
	public EngineImpl (FrontLight frontLight) {
		this.frontLight = frontLight;
	}
	
	@Override
	public void engineOn() {
		System.out.println("** EngineImpl.engineOn");
		// start motor
		motor_init();
		// front lights on
		frontLight.turnOn();
		// tail lights on
		
	}

	@Override
	public void engineOff() {
		System.out.println("** EngineImpl.engineOff");
		// stop motor
		//motor_set_brake (100);
		motor_set_speed (0);
		// front lights off
		frontLight.turnOff();
		// tail lights off
	}
	
	@Override
	public int getEngineSpeed() {
		return currentSpeedPct;
	}
	
	@Override
	public void setEngineSpeed (int speedPercent) {
		System.out.println("** EngineImpl.setEngineSpeed: " + speedPercent);
		motor_set_speed (speedPercent);
		currentSpeedPct = speedPercent;
	}
	
	@Override
	public void setEngineBrake (int brakePercent) {
		System.out.println("** EngineImpl.setEngineBrake: " + brakePercent);
		motor_set_brake (brakePercent);
	}

	@IcecapInlineNative(functionBody = ""
		+ "{\n"
		+ "   motor_init();\n"
		+ "   return -1;\n"
		+ "}\n",
		requiredIncludes = ""
			+ "#include \"..\\scalextric\\motor.h\"\n"  // relative path
		)
	native void motor_init();
	
	@IcecapInlineNative(functionBody = ""
		+ "{\n"
		+ "   motor_set_speed(sp[0]);\n"
		+ "   return -1;\n"
		+ "}\n",
		requiredIncludes = ""
			+ "#include \"..\\scalextric\\motor.h\"\n"  // relative path
		)
	native void motor_set_speed (int speed_percent);
	
	@IcecapInlineNative(functionBody = ""
		+ "{\n"
		+ "   motor_set_brake(sp[0]);\n"
		+ "   return -1;\n"
		+ "}\n",
		requiredIncludes = ""
			+ "#include \"..\\scalextric\\motor.h\"\n"  // relative path
		)
	native void motor_set_brake (int brake_percent);


}
