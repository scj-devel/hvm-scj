package carcontrol.device;

public interface Engine {
	
	void engineOn();
	void engineOff();
	
	int getEngineSpeed();
	void setEngineSpeed (int speedPercent); 
	void setEngineBrake (int brakePercent);
	
}
