package carcontrol.io;

import java.io.IOException;

public interface CommunicationDevice {
	
	public static final int MESSAGE_DEFAULT_CAPACITY = 1024;
	
	/**
	 * Incoming communication. 
	 * @return a command which is an integer in range -1..3  ; ??
	 */
	public int receive();
	
	/**
	 * Incoming communication with is data array of of type byte
	 */
	public int receive(byte[] data, int offset, int length) throws IOException;
	
	/**
	 * Outgoing communication.
	 * Sends a value of type int.
	 */
	public void send(int value) throws IOException;
	
	/**
	 * Outgoing communication with data array of of type byte
	 */
	public void send(byte[] data, int offset, int length) throws IOException;

}
