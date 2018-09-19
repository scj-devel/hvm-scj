package carcontrol.device.impl;

import carcontrol.device.BrakeLight;

public class BrakeLightImpl implements BrakeLight {

	@Override
	public void turnOn() {
		brakeLight_turnOn();
	}

	@Override
	public void turnOff() {
		brakeLight_turnOff();
	}
	
	private static native void brakeLight_turnOn();
	private static native void brakeLight_turnOff();

}
