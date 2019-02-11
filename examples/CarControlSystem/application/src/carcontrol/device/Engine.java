package carcontrol.device;

public interface Engine {
	
	void engineOn();
	void engineOff();
	
	boolean engineIsOn();
	
	byte getEngineSpeed();
	void setEngineSpeed (byte speedPercent); 
	void setEngineBrake (byte brakePercent);
	
}
