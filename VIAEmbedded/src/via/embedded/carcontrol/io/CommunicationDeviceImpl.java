package via.embedded.carcontrol.io;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class CommunicationDeviceImpl implements CommunicationDevice {

	static final public int MESSAGE_DEFAULT_CAPACITY = 1024;
	
	private InputStream istream;
	private OutputStream ostream;
	
	private int command = 0;  // for test only; later deleted
	
	/**
	 * Constructs Communication Device to send and receive data.
	 * 
	 * @param host a host.
	 * @param port a port.
	 * 
	 * @throws IOException if an I/O error occurs.
	 */
	public CommunicationDeviceImpl(String host, int port) throws IOException {
		
		String URLConnection = "socket://" + host + ":" + port;  // ??
		String connectorAPI = System.getProperty("connector.api");
		
		StreamConnection socket = (StreamConnection) Connector.open(URLConnection, Connector.READ_WRITE);
		
		istream = socket.openInputStream();
		ostream = socket.openOutputStream();
		//instance = this;
	}
	
	@Override
	public int receive(byte[] data, int offset, int length) throws IOException {
		if(istream.available() != 0){
			return istream.read(data, offset, length);
		}
		return 0;
	}
	
	@Override
	public int receive() {
		System.out.println("CommunicationDeviceImpl.receive: " + command);
		
		byte[] data = new byte[1];
		
		//try {
		
			//result = receive(data, 0, 1);
//		}
//		catch (IOException e) {
//			return -1;  // unsuccesful reading
//		}
//		if (result < 0 || result > 3)
//			return -2;  // result is not a legal command
					
		return command--;  // command in range 0..3	
	}

	@Override
	public void send(byte[] data, int offset, int length) throws IOException {		
		ostream.write(data, offset, length);
		ostream.flush();	
	}

}


