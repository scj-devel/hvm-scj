package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.microedition.io.Connector;
import javax.safetycritical.io.ConnectionFactory;

import util.TCPConnectionFactoryPosix;
import vm.VMTest;

public class TestTCPConnection {
	private static boolean success;

	private static class ServerLogic implements Runnable {
		@Override
		public void run() {
			String inputLocation = "tcp:localhost;port=3000;server=1";

			DataInputStream inputStream = null;

			try {
				inputStream = Connector.openDataInputStream(inputLocation);
			} catch (IOException e1) {
				devices.Console.println("Could not open [" + inputLocation + "]");
				return;
			}

			try {
				int res = inputStream.readInt();
				devices.Console.println("received: " + res);
				if (res == 42) {
					res = inputStream.readInt();
					devices.Console.println("received: " + res);
					if (res == 43) {
						success = true;
					}
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

	private static class ClientLogic implements Runnable {
		@Override
		public void run() {
			DataOutputStream outputStream = null;

			String outputLocation = "tcp:localhost;port=3000";

			try {
				outputStream = Connector.openDataOutputStream(outputLocation);
			} catch (IOException e1) {
				devices.Console.println("Could not open [" + outputLocation + "]");
				return;
			}

			try {
				outputStream.writeInt(42);
				outputStream.writeInt(43);
			} catch (IOException e1) {
				devices.Console.println("failed to write to [" + outputLocation + "]");
			}

			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		ConnectionFactory tcpConnectionFactory = new TCPConnectionFactoryPosix("tcp");
		ConnectionFactory.register(tcpConnectionFactory);

		Thread serverThread = new Thread(new ServerLogic());
		Thread clientThread = new Thread(new ClientLogic());

		success = false;

		serverThread.start();
		clientThread.start();

		serverThread.join();
		clientThread.join();

		if (success) {
			VMTest.markResult(false);
		}
	}
}
