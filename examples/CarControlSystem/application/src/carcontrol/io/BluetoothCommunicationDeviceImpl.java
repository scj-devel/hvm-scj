package carcontrol.io;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.safetycritical.io.ConnectionFactory;

public class BluetoothCommunicationDeviceImpl implements CommunicationDevice {
	
	String name = "bluetooth";
	
	private InputStream istream;
	private OutputStream ostream;
	
	/**
	 * Constructs Communication Device to send and receive data.
	 * 
	 * @param target
	 * @param property 
	 * 
	 * @throws IOException if an I/O error occurs.
	 */
	public BluetoothCommunicationDeviceImpl() throws IOException {
		
		ConnectionFactory.register(new BluetoothConnectionFactory(name));
		ostream = Connector.openOutputStream(name);
		istream = Connector.openInputStream(name);
	}
		
	@Override
	public int receive(byte[] data, int offset, int length) throws IOException {
		if(istream.available() != 0){
			return istream.read(data, offset, length);
		}
		return -99;
	}
	
	@Override
	public int receive() {
		
		byte[] data = new byte[1];
		
		try {
			receive(data, 0, 1);
			return data[0];
		}
		catch (IOException e) {
			return -99;  // Unsuccessful reading
		}
	}
	
	@Override
	public void send(int value) throws IOException {
		ostream.write(value);
		ostream.flush();
	}

	@Override
	public void send(byte[] data, int offset, int length) throws IOException {		
		ostream.write(data, offset, length);
		ostream.flush();	
	}
}
