package carcontrol.device;

public interface Brake {

	float decrease = 0.20f; // when braking the speed is decreased with 20%
	
	void brake();
}
