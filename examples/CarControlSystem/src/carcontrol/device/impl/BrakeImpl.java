package carcontrol.device.impl;

import carcontrol.device.Brake;
import carcontrol.device.BrakeLight;
import carcontrol.device.Engine;

public class BrakeImpl implements Brake {

	Engine engine;
	BrakeLight brakeLight;
	
	public BrakeImpl (Engine engine, BrakeLight brakeLight) {
		this.engine = engine;
		this.brakeLight = brakeLight;
	}
	
	@Override
	public void brake() {
		// brake lights on
		brakeLight.turnOn();
		
		// braking: decrease motor speed; 
		// in this version the native function set_brake() is not used
		byte speed = engine.getEngineSpeed();  // speed is in percent; 0% <= speed <= 100%
		
		byte delta = (byte)(speed * decrease); 
		
		engine.setEngineBrake((byte)(speed - delta));	
		
		// brake lights off
		brakeLight.turnOff();		
	}
}
