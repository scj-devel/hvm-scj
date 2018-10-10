package carcontrol.data;

public class RunData {
	
	int speed;
	int distance;
		
	public void setSpeed (int actualSpeed) {
		this .speed = actualSpeed;
	}

	public void setDistance (int actualDistance) {
		this.distance = actualDistance;
	}
	
	/**
	 * Returns the data as an array of bytes
	 * 
	 * @return the data as a byte array
	 */
	public byte[] getData() {
		return null;
	}
	
}
