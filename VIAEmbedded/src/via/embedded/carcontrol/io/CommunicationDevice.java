package via.embedded.carcontrol.io;

import java.io.IOException;

public interface CommunicationDevice {
	
	/**
	 * Incoming communication. 
	 * @return a command which is an integer in range 0..6
	 */
	public int receive();
	
	/**
	 * Incoming communication with is data array of of type byte
	 */
	public int receive(byte[] data, int offset, int length) throws IOException;
	
	/**
	 * Outgoing communication with data array of of type byte
	 */
	public void send(byte[] data, int offset, int length) throws IOException;

}
