package main;

import devices.ev3.Button;
import devices.ev3.Button.ButtonID;
import devices.ev3.EV3;
import devices.ev3.Motor;
import devices.ev3.SensorPort;
import devices.ev3.UltraSonicSensor;
import devices.ev3.Motor.Direction;
import devices.ev3.MotorPort;
import devices.ev3.MotorPort.MotorPortID;
import devices.ev3.SensorPort.SensorPortID;

public class StopAtWall {

	private static byte motorPower;

	public static void main(String args[]) {
		MotorPort portA = new MotorPort(MotorPortID.A);
		MotorPort portD = new MotorPort(MotorPortID.D);
		Motor motorA = new Motor(portA);
		Motor motorD = new Motor(portD);

		motorPower = 0;

		motorA.setDirection(Direction.FORWARD);
		motorD.setDirection(Direction.FORWARD);

		motorA.setPower((byte) 1);
		motorD.setPower((byte) 1);

		motorA.start();
		motorD.start();

		speedUp(motorA, motorD);

		SensorPort port = new SensorPort(SensorPortID.Port1);
		UltraSonicSensor ultraSonicSensor = new UltraSonicSensor(port);

		Button backButton = new Button(ButtonID.BACK);

		while (!backButton.isPressed()) {
			short value = ultraSonicSensor.getSensorValue();

			if (value < 250) {
				decreaseMotorPower(motorA, motorD);
			} else {
				increaseMotorPower(motorA, motorD);
			}
			EV3.sleep(5);
		}

		slowDown(motorA, motorD);

		motorA.stop();
		motorD.stop();
	}

	private static void increaseMotorPower(Motor motorA, Motor motorD) {
		motorPower+=2;
		if (motorPower > 100) {
			motorPower = 100;
		} else {
			motorA.setPower(motorPower);
			motorD.setPower(motorPower);
		}
	}

	private static void decreaseMotorPower(Motor motorA, Motor motorD) {
		motorPower-=2;
		if (motorPower < 0) {
			motorPower = 0;
		} else {
			motorA.setPower(motorPower);
			motorD.setPower(motorPower);
		}
	}

	private static void slowDown(Motor motorA, Motor motorD) {
		for (byte b = 99; b > 0; b--) {
			motorA.setPower(b);
			motorD.setPower(b);
			motorPower = b;
			EV3.sleep(10);
		}

	}

	private static void speedUp(Motor motorA, Motor motorD) {
		for (byte b = 2; b < 100; b++) {
			motorA.setPower(b);
			motorD.setPower(b);
			motorPower = b;
			EV3.sleep(10);
		}
	}
}
