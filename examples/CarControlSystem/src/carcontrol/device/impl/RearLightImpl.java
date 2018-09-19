package carcontrol.device.impl;

import carcontrol.device.RearLight;

public class RearLightImpl implements RearLight {

	@Override
	public void turnOn() {
		rearLight_turnOn();
	}

	@Override
	public void turnOff() {
		rearLight_turnOff();
	}
	
	private static native void rearLight_turnOn();
	private static native void rearLight_turnOff();
}
