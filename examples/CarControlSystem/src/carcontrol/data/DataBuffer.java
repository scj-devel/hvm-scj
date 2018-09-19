package carcontrol.data;

public class DataBuffer {
	
	RunData[] buffer;  // this buffer is implemented as a circular array;
	                   // not used at Level 0.
	
	public DataBuffer (int size) {
		
		this.buffer = new RunData[size];
		
	}
	
	public void put (RunData data) {
		
	}
	
	public RunData take() {
		return null;
	}

}
