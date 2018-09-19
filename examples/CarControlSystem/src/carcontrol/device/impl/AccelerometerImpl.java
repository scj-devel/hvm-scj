package carcontrol.device.impl;

import carcontrol.device.Accelerometer;
import icecaptools.IcecapInlineNative;

public class AccelerometerImpl implements Accelerometer {
	
	@Override
	public void pollMPU_9520() {
		poll_mpu_9520();
	}

	@Override
	public float getXAcceleration() {		
		return getXAccel();
	}

	@Override
	public float getYAcceleration() {
		return getYAccel();
	}

	@Override
	public float getZAcceleration() {
		return getZAccel();
	}

	@Override
	public short getRawXAcceleration() {
		return getRawXAccel();
	}

	@Override
	public short getRawYAcceleration() {
		return getRawYAccel();
	}

	@Override
	public short getRawZAcceleration() {
		return getRawZAccel();
	}
	
	// --- native methods ------------------------------
	
	@IcecapInlineNative(functionBody = "" 
		+ "{\n" 
		+ "   poll_mpu_9520();\n" 
		+ "   return -1;\n" 
		+ "}\n", 
		requiredIncludes = "#include \"..\\scalextric\\mpu_9520.h\"\n"
	)
	public native void poll_mpu_9520();
	
	@IcecapInlineNative(functionBody = ""
		+ "{\n"
		
		+ "   union { float f; int i; } u;\n" 
		+ "   u.f = get_x_accel();\n"		
		+ "   sp[0] = u.i;\n" 
		
		+ "   return -1;\n"
		+ "}\n",
		requiredIncludes = 
		  "#include \"..\\scalextric\\mpu_9520.h\"\n"
	)
	private native float getXAccel();
	
	@IcecapInlineNative(functionBody = ""
		+ "{\n"
		
		+ "   union { float f; int i; } u;\n" 
		+ "   u.f = get_y_accel();\n"		
		+ "   sp[0] = u.i;\n" 
		
		+ "   return -1;\n"
		+ "}\n",
		requiredIncludes = 
		  "#include \"..\\scalextric\\mpu_9520.h\"\n"
	)
	private native float getYAccel();
	
	@IcecapInlineNative(functionBody = ""
		+ "{\n"
		
		+ "   union { float f; int i; } u;\n" 
		+ "   u.f = get_z_accel();\n"		
		+ "   sp[0] = u.i;\n" 
		
		+ "   return -1;\n"
		+ "}\n",
		requiredIncludes = 
		  "#include \"..\\scalextric\\mpu_9520.h\"\n"
	)
	private native float getZAccel();
	
	@IcecapInlineNative(functionBody = ""
		+ "{\n"
		+ "   sp[0] = get_raw_x_accel();\n" 
		+ "   return -1;\n"
		+ "}\n",
		requiredIncludes = 
		  "#include <stdint.h>\n" +
		  "#include \"..\\scalextric\\mpu_9520.h\"\n"
	)
	private native short getRawXAccel();
	
	@IcecapInlineNative(functionBody = ""
		+ "{\n"
		+ "   sp[0] = get_raw_y_accel();\n" 
		+ "   return -1;\n"
		+ "}\n",
		requiredIncludes = 
		  "#include <stdint.h>\n" +
		  "#include \"..\\scalextric\\mpu_9520.h\"\n"
	)
	private native short getRawYAccel();	
	
	@IcecapInlineNative(functionBody = ""
		+ "{\n"
		+ "   sp[0] = get_raw_z_accel();\n" 
		+ "   return -1;\n"
		+ "}\n",
		requiredIncludes = 
		  "#include <stdint.h>\n" +
		  "#include \"..\\scalextric\\mpu_9520.h\"\n"
	)
	private native short getRawZAccel();	

}
