package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.microedition.io.Connector;
import javax.safetycritical.io.ConnectionFactory;

import util.CommConnectionFactoryPosix;
import vm.VMTest;

public class TestCommConnection {

	private static final boolean READ = true;
	private static final boolean WRITE = true;

	public static void main(String[] args) throws MalformedURLException {
		ConnectionFactory serialConnectionFactory = new CommConnectionFactoryPosix("comm");
		ConnectionFactory.register(serialConnectionFactory);

		String outputLocation = "comm:/dev/ttyUSB0;baudrate=19200";

		DataOutputStream outputStream = null;

		if (WRITE) {
			try {
				outputStream = Connector.openDataOutputStream(outputLocation);
			} catch (IOException e1) {
				devices.Console.println("Could not open [" + outputLocation + "]");
				VMTest.markResult(true);
				return;
			}

			try {
				devices.Console.println("Writing to " + outputLocation);
				outputStream.writeInt(42);
			} catch (IOException e1) {
				devices.Console.println("failed to write to [" + outputLocation + "]");
				VMTest.markResult(false);
				return;
			}
		}

		String inputLocation = "comm:/dev/ttyUSB1;baudrate=19200";
		DataInputStream inputStream = null;

		if (READ) {
			try {
				inputStream = Connector.openDataInputStream(inputLocation);
			} catch (IOException e1) {
				devices.Console.println("Could not open [" + inputLocation + "]");
				VMTest.markResult(true);
				return;
			}
		}

		if (WRITE) {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
			}
		}

		if (READ) {
			try {
				devices.Console.println("Reading from " + inputLocation);
				int res = inputStream.readInt();
				devices.Console.println("received: " + res);
				if (res == 42) {
					VMTest.markResult(false);
				}
			} catch (IOException e) {
				devices.Console.println("failed to read from [" + inputLocation + "]");
			}

			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
