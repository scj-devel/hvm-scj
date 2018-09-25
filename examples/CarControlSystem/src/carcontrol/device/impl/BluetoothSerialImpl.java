package carcontrol.device.impl;

import carcontrol.device.BluetoothSerial;
import icecaptools.IcecapInlineNative;

public class BluetoothSerialImpl implements BluetoothSerial {

	@Override
	public void openSerial() {
		bluetooth_start();

	}

	@Override
	public void close() {
		// TODO figure out what to do...

	}

	@Override
	public void write(int data) {
		bluetooth_send_byte(data);
	}

	@Override
	public int read() {
		return bluetooth_read_byte();
	}
	
	
	// --- native methods ------------------------------
		
	@IcecapInlineNative(
			functionBody = ""
			+ "{\n"
			+ "   bluetooth_start();\n"
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = ""
				+ "#include \"..\\scalextric\\bluetooth.h\"\n"
			)
			native void bluetooth_start();
	
	@IcecapInlineNative(
			functionBody = ""
			+ "{\n"
			+ "   bluetooth_send_byte(sp[1]);\n"
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = ""
				+ "#include \"..\\scalextric\\bluetooth.h\"\n"
			) native void bluetooth_send_byte(int data);
			
			
	@IcecapInlineNative(
			functionBody = ""
			+ "{\n"
			+ "   int value = bluetooth_read_byte();\n"
			+ "   sp[0] = value;\n"
			+ "   return -1;\n"
			+ "}\n",
			requiredIncludes = ""
				+ "#include \"..\\scalextric\\bluetooth.h\"\n"
			)
			native int bluetooth_read_byte();
}
