package via.embedded.carcontrol.device;

public interface FlashLight extends Light {
	
	void setColor(Color color);
	Color getColor();
	void turnOnFlashing();
	void turnOffFlashing();
	boolean isFlashing();

}
