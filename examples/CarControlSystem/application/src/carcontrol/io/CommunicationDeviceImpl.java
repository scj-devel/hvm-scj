package carcontrol.io;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connection;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.safetycritical.io.ConnectionFactory;

import carcontrol.constants.Mode;
import util.CommConnectionFactoryPosix;

public class CommunicationDeviceImpl implements CommunicationDevice {

	static final public int MESSAGE_DEFAULT_CAPACITY = 1024;
	
	String scheme = "comm";
//	String target = "/dev/ttyUSB0";
//	String property = "baudrate=19200";
	
	private InputStream istream;
	private OutputStream ostream;
		
	private int cmd = -1; // for test only; later deleted
	private boolean reverse = true;
	private boolean drive = true;
	
	/**
	 * Constructs Communication Device to send and receive data.
	 * 
	 * @param target
	 * @param property 
	 * 
	 * @throws IOException if an I/O error occurs.
	 */
	public CommunicationDeviceImpl(String target, String property) throws IOException {
		
//		ConnectionFactory serialConnectionFactory = new CommConnectionFactoryPosix(scheme);
//		ConnectionFactory.register(serialConnectionFactory);
//		
//		String URLConnection = scheme + ":" + target + ";" + property; 
//		
//		Connection con = serialConnectionFactory.create(URLConnection);
//		
//		ostream = Connector.openOutputStream(URLConnection);
//		istream = Connector.openInputStream(URLConnection);
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
		
		//try {
		
			//return receive(data, 0, 1);
//		}
//		catch (IOException e) {
//			return -99;  // unsuccesful reading
//		}
//		if (result < 0 || result > 3)
//			return -2;  // result is not a legal command
		
		// HSO: for test only
		
		if (cmd == -1) {
			cmd = 0;
		}
		else if (cmd == 0 && reverse && drive) {
			cmd = 1;
		}
		else if (cmd == 1 && reverse) {
			cmd = 2; reverse = false;
		}
		else if (cmd == 1 && drive) {
			cmd = 3; drive = false;
		}
		else if (cmd == 2) {
			cmd = 1; 
		}
		else if (cmd == 3) {
			cmd = 1;
		}
		else if (cmd == 1 && !reverse && !drive) {
			cmd = 0;
		}
		else if (cmd == 0 && !reverse && !drive) {
			cmd = -1;
		}
		
		
		System.out.println("  ==> CommunicationDeviceImpl.receive: new command: " + cmd + "; mode: " + Mode.getMode(cmd));
		return cmd;  
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


