package carcontrol.device;

public interface Accelerometer {

	void pollMPU_9520();
	
	float getXAcceleration();
	float getYAcceleration();
	float getZAcceleration();
	
	short getRawXAcceleration();
	short getRawYAcceleration();
	short getRawZAcceleration();
}
