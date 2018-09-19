package carcontrol.device.impl;

import carcontrol.device.Engine;
import carcontrol.device.FrontLight;

public class EngineImpl implements Engine {

	FrontLight frontLight;
	
	byte currentSpeedPct;
	
	public EngineImpl (FrontLight frontLight) {
		this.frontLight = frontLight;
	}
	
	@Override
	public void engineOn() {
		System.out.println("EngineImpl.engineOn");
		// start motor
		// front lights on
		frontLight.turnOn();
		// tail lights on
		
	}

	@Override
	public void engineOff() {
		System.out.println("EngineImpl.engineOff");
		// stop motor
		// front lights off
		frontLight.turnOff();
		// tail lights off
	}
	
	@Override
	public byte getEngineSpeed() {
		return currentSpeedPct;
	}
	
	@Override
	public void setEngineSpeed (byte speedPercent) {
		setMotorSpeed(speedPercent);
		currentSpeedPct = speedPercent;
	}
	
	@Override
	public void setEngineBrake (byte brakePercent) {
		setBrake(brakePercent);
	}

	private native void setMotorSpeed(byte speedPercent);
	private native void setBrake(byte brakePercent);

}
