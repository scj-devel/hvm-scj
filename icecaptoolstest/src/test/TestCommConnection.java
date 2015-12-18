package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.microedition.io.Connector;
import javax.safetycritical.io.ConnectionFactory;

import util.CommConnectionFactoryPosix;

public class TestCommConnection {

	public static void main(String[] args) throws MalformedURLException {
		String outputLocation = "comm:/dev/ttyUSB0;baudrate=19200";
		String inputLocation = "comm:/dev/ttyUSB1;baudrate=19200";

		ConnectionFactory serialConnectionFactory = new CommConnectionFactoryPosix("comm");
		ConnectionFactory.register(serialConnectionFactory);
		
		/*try {
			DataOutputStream outputStream = Connector.openDataOutputStream(outputLocation);			
			DataInputStream inputStream = Connector.openDataInputStream(inputLocation);
		
			outputStream.writeInt(42);
			int res = inputStream.read();
			if (res == 42)
			{*/
				args = null;
			/*}
		} catch (IOException e) {
			devices.Console.println("Could not open [" + outputLocation + "]");
		}*/
	}
}
