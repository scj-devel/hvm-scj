package test.ARM.ATSAMe70;

import devices.arm.ATSAMe70.ATSAMe70TargetConfiguration;
import vm.Memory;

public class TestBluetooth extends ATSAMe70TargetConfiguration {

	public static void main(String[] args) {
		init();

		devices.Console.println("Bluetooth test start.");

		devices.Console.println("UUUU");

		set_led_output();

		Memory mainArea = Memory.getHeapArea();

		/* devices.Console.println(
				"xx-raw: " + get_raw_x_accel() + ", y-raw: " + get_raw_y_accel() + ", z-raw: " + get_raw_z_accel());*/
		devices.Console.println("init: ");

		int waterMark = mainArea.consumedMemory();

		while (true) {
			//poll_mpu_9520();

			/*devices.Console.println(
					"xx-raw: " + get_raw_x_accel() + ", y-raw: " + get_raw_y_accel() + ", z-raw: " + get_raw_z_accel()); */
//			devices.Console.println("init: " + bt_initialised);
//			if (bt_initialised > 0) {
//				bt_send_bytes("UUUU");
//			}
			for (byte i = 0; i < 4; i++) {
				devices.System.delay(20000);
			}
			mainArea.reset(waterMark);
			toggle_led();
		}
	}

	@Override
	public String getOutputFolder() {
		return "C:\\home\\icecapout";
	}
}
