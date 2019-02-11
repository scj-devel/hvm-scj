package carcontrol.device.impl;

import carcontrol.device.Accelerator;
import carcontrol.device.Engine;

public class AcceleratorImpl implements Accelerator {

	Engine engine;
	
	public AcceleratorImpl (Engine engine) {
		this.engine = engine;
	}
	
	@Override
	public void accelerate() {
		int speed = engine.getEngineSpeed();  // speed is in percent; 0% <= speed <= 100%
		
		int delta = (int) ((100 - speed) * increase) ; 
		
		engine.setEngineSpeed((byte)(speed + delta));	
	}
}
