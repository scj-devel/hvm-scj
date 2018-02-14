package test.ARM.ATSAMe70;

import devices.arm.ATSAMe70.ATSAMe70TargetConfiguration;
import vm.Memory;

public class HelloScalecTrix extends ATSAMe70TargetConfiguration {

	public static void main(String[] args) {
		init();

		set_led_output();
		
		Memory mainArea = Memory.getHeapArea();

//		devices.Console.println(
//				"x-raw: " + get_raw_x_accel() + ", y-raw: " + get_raw_y_accel() + ", z-raw: " + get_raw_z_accel());
		
        int waterMark = mainArea.consumedMemory();
        
		devices.Console.println("ZAPPA!");

//		while (true) {
//			poll_mpu_9520();
//			devices.System.delay(20000);
//			devices.Console.println(
//					"x-raw: " + get_raw_x_accel() + ", y-raw: " + get_raw_y_accel() + ", z-raw: " + get_raw_z_accel());
//			mainArea.reset(waterMark);
//			toggle_led();
//		}
	}

	@Override
	public String getOutputFolder() {
		return "C:\\home\\icecapout";
	}
}
