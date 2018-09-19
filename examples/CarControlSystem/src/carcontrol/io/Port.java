package carcontrol.io;

import java.io.IOException;

public class Port {
	
	private byte[] message;
	
	private CommunicationDevice commDevice;
	
	public Port (CommunicationDevice commDevice) {		
		this.commDevice = commDevice;
		message = new byte[CommunicationDevice.MESSAGE_DEFAULT_CAPACITY];
	}
	
	public synchronized void send(byte msg) throws IOException {
		message[0] = msg;
		commDevice.send(message, 0, 1);				
	}
	
	public synchronized byte receive() throws IOException {	
		message[0] = (byte)commDevice.receive();
		return message[0];	
	}

}
