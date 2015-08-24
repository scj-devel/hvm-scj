package test.ev3.leaderShipElection;

import devices.ev3.EV3;
import devices.ev3.Motor;
import devices.ev3.Motor.Direction;

public class LeaderShipRobotActor {
	
	private Motor motor_1;
	private Motor motor_2;
	private Motor motor_3;
	private String specialRobot;
	private String robotIp;
	
	public LeaderShipRobotActor(Motor motor_1, Motor motor_2, Motor motor_3, String robotIp, String specialRobot){
		this.motor_1 = motor_1;
		this.motor_2 = motor_2;
		this.motor_3 = motor_3;
		this.specialRobot = specialRobot;
		this.robotIp = robotIp;
	}
	
	public void leaderAction(){
		motor_1.setPower((byte) 50);
		motor_2.setPower((byte) 50);
		motor_3.setPower((byte) 100);

		if (robotIp.equals(specialRobot)) {
			motor_1.setDirection(Direction.BACKWARD);
			motor_2.setDirection(Direction.BACKWARD);
		} else {
			motor_1.setDirection(Direction.FORWARD);
			motor_2.setDirection(Direction.FORWARD);
		}

		motor_1.start();
		motor_2.start();

		EV3.sleep(1000);

		motor_1.stop();
		motor_2.stop();

		motor_3.setDirection(Direction.FORWARD);
		motor_3.start();
	}
	
	public void followAction(){
		motor_3.stop();

		motor_1.setPower((byte) 50);
		motor_2.setPower((byte) 50);

		if (robotIp.equals(specialRobot)) {
			motor_1.setDirection(Direction.FORWARD);
			motor_2.setDirection(Direction.FORWARD);
		} else {
			motor_1.setDirection(Direction.BACKWARD);
			motor_2.setDirection(Direction.BACKWARD);
		}

		motor_1.start();
		motor_2.start();

		EV3.sleep(1000);

		motor_1.stop();
		motor_2.stop();
	}

}
