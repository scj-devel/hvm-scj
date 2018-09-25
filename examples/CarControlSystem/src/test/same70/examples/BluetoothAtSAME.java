package test.same70.examples;

import java.io.IOException;

import carcontrol.io.BluetoothCommunicationDeviceImpl;
import carcontrol.io.Port;
import test.same70.configuration.BluetoothTargetConfigurationSAME;

public class BluetoothAtSAME extends BluetoothTargetConfigurationSAME {

	public static void main(String[] args) {
		init(); // initialize the board

		devices.Console.println("BluetoothAtSAME started"); // output to HTerm
		delay(1000);

		try {
			devices.Console.println("bluetooth starting");
			Port port = new Port(new BluetoothCommunicationDeviceImpl());

			delay(1000);
			devices.Console.println("sending byte");
			port.send((byte) 42);

			delay(1000);
			devices.Console.println("receiving byte");
			byte data = port.receive();
			devices.Console.println("byte: " + data);

		} catch (IOException e) {
			devices.Console.println(e.getStackTrace().toString());
		}

		delay(1000);
		devices.Console.println("BluetoothAtSAME end");
	}
}
