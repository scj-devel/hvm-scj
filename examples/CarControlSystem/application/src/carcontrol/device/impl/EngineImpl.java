package carcontrol.device.impl;

import carcontrol.device.Engine;
import carcontrol.device.FrontLight;
import icecaptools.IcecapInlineNative;

public class EngineImpl implements Engine {
	
	byte currentSpeedPct;
	
	boolean engineIsOn = false;
	
	public EngineImpl () {
		// init motor
		motor_init();	
	}
	
	@Override
	public void engineOn() {
		byte speed = 0;  // later: better speed = 0;
		System.out.println("** EngineImpl.engineOn; speed: " + speed);
		
		motor_set_speed (speed);
	}

	@Override
	public void engineOff() {
		byte speed = 0;
		System.out.println("** EngineImpl.engineOff; speed: " + speed);
		// stop motor
		//motor_set_brake (100);
		motor_set_speed (speed);
	}
	
	@Override
	public boolean engineIsOn() {
		return engineIsOn;
	}
	
	@Override
	public byte getEngineSpeed() {
		return currentSpeedPct;
	}
	
	@Override
	public void setEngineSpeed (byte speedPercent) {
		System.out.println("** EngineImpl.setEngineSpeed: " + speedPercent);
		motor_set_speed (speedPercent);
		currentSpeedPct = speedPercent;
	}
	
	@Override
	public void setEngineBrake (byte brakePercent) {
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
		+ "   motor_set_speed(sp[1]);\n"  // sp[1] works, why not sp[0] ?
		                                  // Answar: sp[0] has the this pointer, when native method is not static
		+ "   return -1;\n"
		+ "}\n",
		requiredIncludes = ""
			+ "#include \"..\\scalextric\\motor.h\"\n"  // relative path
		)
	native void motor_set_speed (byte speed_percent);
	
	@IcecapInlineNative(functionBody = ""
		+ "{\n"
		+ "   motor_set_brake(sp[1]);\n"
		+ "   return -1;\n"
		+ "}\n",
		requiredIncludes = ""
			+ "#include \"..\\scalextric\\motor.h\"\n"  // relative path
		)
	native void motor_set_brake (byte brake_percent);


}
