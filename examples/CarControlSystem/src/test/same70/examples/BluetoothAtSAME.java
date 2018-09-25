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
			// delaying so that bluetooth have time to initialize
			delay(5000);
			
			byte data = 0;
			
			while (data != 4) { // 4 = EOT
				
				devices.Console.println("receiving byte");
				data = port.receive();
				devices.Console.println("byte: " + (char)data);
				delay(10000);
				
				if (data > '!' && data < '~') {
					devices.Console.println("sending byte");
					port.send(data);
					delay(10000);
				}
			}

		} catch (IOException e) {
			devices.Console.println(e.getStackTrace().toString());
		}

		delay(1000);
		devices.Console.println("BluetoothAtSAME end");
	}
}
