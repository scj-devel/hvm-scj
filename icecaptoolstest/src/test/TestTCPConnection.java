package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.microedition.io.Connector;
import javax.safetycritical.io.ConnectionFactory;

import util.TCPConnectionFactoryPosix;

public class TestTCPConnection {
	
	public static void main(String[] args) throws MalformedURLException {
		ConnectionFactory tcpConnectionFactory = new TCPConnectionFactoryPosix(
				"tcp");
		ConnectionFactory.register(tcpConnectionFactory);

		String outputLocation = "tcp:localhost;port=3000";

		DataOutputStream outputStream = null;

		try {
			outputStream = Connector.openDataOutputStream(outputLocation);
		} catch (IOException e1) {
			devices.Console.println("Could not open [" + outputLocation + "]");
			args = null;
			return;
		}

		try {
			outputStream.writeInt(42);
		} catch (IOException e1) {
			devices.Console.println("failed to write to [" + outputLocation
					+ "]");
		}	

		String inputLocation = "tcp:localhost;port=3000";

		DataInputStream inputStream = null;

		try {
			inputStream = Connector.openDataInputStream(inputLocation);
		} catch (IOException e1) {
			devices.Console.println("Could not open [" + inputLocation + "]");
			return;
		}

		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
			}
		}
		
		try {
			int res = inputStream.readInt();
			devices.Console.println("received: " + res);
			if (res == 42)
			{
				args = null;
			}
		} catch (IOException e) {
			devices.Console.println("failed to read from [" + inputLocation
					+ "]");
		}

		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
	}
}
