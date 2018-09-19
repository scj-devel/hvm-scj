package carcontrol.device;

public interface Engine {
	
	void engineOn();
	void engineOff();
	
	byte getEngineSpeed();
	void setEngineSpeed (byte speedPercent); 
	void setEngineBrake (byte brakePercent);
	
}
