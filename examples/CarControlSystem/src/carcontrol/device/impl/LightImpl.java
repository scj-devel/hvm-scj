package carcontrol.device.impl;

import javax.realtime.device.RawByte;
import javax.realtime.device.RawByteMM;

import carcontrol.device.Light;

public class LightImpl implements Light {

	static final byte ON  = 1;
	static final byte OFF = 0;
	
	private RawByte byteObj;
	
	public LightImpl (long base) {
		byteObj = new RawByteMM (base, 1, 1);
	}
	
	@Override
	public void turnOn() {
		byteObj.setByte(ON);
	}

	@Override
	public void turnOff() {
		byteObj.setByte(OFF);
	}

//	@Override
//	public boolean isOn() {
//		return byteObj.getByte() == ON;
//	}

}
