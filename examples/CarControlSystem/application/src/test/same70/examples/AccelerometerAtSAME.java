package test.same70.examples;


import carcontrol.device.Accelerometer;
import carcontrol.device.impl.AccelerometerImpl;
import test.same70.configuration.MachineFactorySAME;
import test.same70.configuration.TargetConfigurationSAME;
import vm.MachineFactory;

public class AccelerometerAtSAME extends TargetConfigurationSAME {

	public static void main(String[] args) {
		init();
		
		devices.Console.println("AccelerometerAtSAME started");
		
		MachineFactory mFac = new MachineFactorySAME();
		mFac.startSystemTick();
		
		Accelerometer accel = new AccelerometerImpl();

		final int ACC_2G_DIVIDER = 16384;  // see mpu_9520.c, where it is defined
		
		devices.Console.println("Raw acc in binary values and decimal values (acc unit is g):");
		for (int i = 0; i < 3; i++) {	
			accel.pollMPU_9520();
			delay(500);
			
			int xRaw = accel.getRawXAcceleration(); float x = accel.getXAcceleration();
			int yRaw = accel.getRawYAcceleration(); float y = accel.getYAcceleration();
			int zRaw = accel.getRawZAcceleration(); float z = accel.getZAcceleration();
			
			float xRawToFloat = (float)xRaw/(float)16384; float dx = Math.abs(xRawToFloat - x);
			float yRawToFloat = (float)yRaw/(float)16384; float dy = Math.abs(yRawToFloat - y);
			float zRawToFloat = (float)zRaw/(float)16384; float dz = Math.abs(zRawToFloat - z);
			
			devices.Console.println("raw:   x: " + xRaw + ", y: " + yRaw + ", z: " + zRaw );
			devices.Console.println("float: x: " + devices.Console.floatToString(x) +
					", y: " + devices.Console.floatToString(y) +
					", z: " + devices.Console.floatToString(z));
			
			devices.Console.println("Comparing acc: RawToFloat   float   difference");
			
			if (dx > 0.0001f) {			
				devices.Console.println("x:             " + devices.Console.floatToString(xRawToFloat) + 
						                "         " + devices.Console.floatToString(x) + 
						                "    " + devices.Console.floatToString(xRawToFloat - x));
			}
			else if (dy > 0.0001f) {
				devices.Console.println("y:             " + devices.Console.floatToString(yRawToFloat) + 
		                "         " + devices.Console.floatToString(y) + 
		                "    " + devices.Console.floatToString(yRawToFloat - y));
			}
			else if (dz > 0.0001f) {
				devices.Console.println("z:             " + devices.Console.floatToString(zRawToFloat) + 
		                "         " + devices.Console.floatToString(z) + 
		                "    " + devices.Console.floatToString(zRawToFloat - z));
			}
			else
				devices.Console.println("   No difference :-)" );
		}		
		
		blink (2000);
		
//		float xx = 19.98f;
//		devices.Console.println("Print float: " + devices.Console.floatToString(xx));		
//		xx = -19.98f;
//		devices.Console.println("Print float: "+ devices.Console.floatToString(xx));		
//		xx = 0.0f;
//		devices.Console.println("Print float: "+ devices.Console.floatToString(xx));

		devices.Console.println("AccelerometerAtSAME end");
	}
}
