package carcontrol.device.impl;

import carcontrol.device.Speed;

public class SpeedImpl implements Speed {

	int speedPct = 0;    // speedPct setting 0..100 %
	
	@Override
	public int getSpeed() {
		return speedPct;
	}

	@Override
	public void speed(int value) {		
		speedPct += value;
		
		if (speedPct <= 0)
			speedPct = 0;
		else if (speedPct >= 100)
			speedPct = 100;
	}

}
