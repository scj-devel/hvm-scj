package carcontrol.device;

public interface BluetoothSerial {
	
	void openSerial();
	void close();
	void write(int data);
	int read();
}
