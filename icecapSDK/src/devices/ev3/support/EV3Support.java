package devices.ev3.support;

import devices.ev3.EV3;
import devices.ev3.Motor;
import devices.ev3.Motor.Direction;

public class EV3Support {
	static int[] commandInfo;
	static Motor[] motors;
	
	char command = 0;
	int speed = -1;
	int left_side_motor_num = -1;
	int duration = -1;

	interface Command {
		public final static char FORWARD = 'F';
		public final static char BACKFORD = 'B';
		public final static char TURNLEFT = 'L';
		public final static char TURNRIGHT = 'R';
		public final static char START = 'S';
		public final static char STOP = 'P';
		public final static char CHANGESPEED = 'C';
	}
	
	/**
	 * Create the EV3 commander
	 * @param m The motors of the robot
	 */
	public EV3Support(Motor[] m){
		commandInfo = new int[3];
		motors = m;
	}
	
	/**
	 * get a command form the leader
	 * @param msg the command that sent from the leader
	 */
	public void getCommand(String msg){
		command = msg.charAt(0);

		if (command == Command.START || command == Command.STOP) {
			duration = getCommandInfo(msg.substring(2), 1)[0];

		} else if (command == Command.FORWARD || command == Command.BACKFORD || command == Command.CHANGESPEED) {
			int[] generated = getCommandInfo(msg.substring(2), 2);
			speed = generated[0];
			duration = generated[1];
		} else {
			int[] generated = getCommandInfo(msg.substring(2), 3);
			speed = generated[0];
			left_side_motor_num = generated[1];
			duration = generated[2];
		}

		if ((command == Command.TURNLEFT || command == Command.TURNRIGHT) && left_side_motor_num == -1) {
			devices.Console.println("wrong command, do not specify the number of left side motors");
			return;
		}

		if ((command == Command.FORWARD || command == Command.BACKFORD || command == Command.CHANGESPEED)
				&& speed == -1) {
			devices.Console.println("wrong command, do not specify the number of left side motors");
			return;
		}
	}

	/**
	 * take the action based on the command that just got
	 */
	public void action() {

		switch (command) {
		case Command.FORWARD:
//			 devices.Console.println("get command: forward with speed: " +
//			 speed);
			for (int i = 0; i < motors.length; i++) {
				motors[i].setPower((byte) speed);
				motors[i].setDirection(Direction.FORWARD);
				motors[i].start();
			}
			break;

		case Command.BACKFORD:
//			 devices.Console.println("get command: backforward with speed: " +
//			 speed);
			for (int i = 0; i < motors.length; i++) {
				motors[i].setPower((byte) speed);
				motors[i].setDirection(Direction.BACKWARD);
				motors[i].start();
			}
			break;

		case Command.START:
//			 devices.Console.println("get command: start");
			for (int i = 0; i < motors.length; i++) {
				motors[i].start();
			}
			break;

		case Command.STOP:
//			 devices.Console.println("get command: stop");
			for (int i = 0; i < motors.length; i++) {
				motors[i].stop();
			}
			break;

		case Command.CHANGESPEED:
//			 devices.Console.println("get command: change speed to: " +
//			 speed);
			for (int i = 0; i < motors.length; i++) {
				motors[i].setPower((byte) speed);
			}
			break;

		case Command.TURNLEFT:
//			 devices.Console.println("get command: turn left with speed: " +
//			 speed);
			for (int i = 0; i < motors.length; i++) {
				motors[i].setPower((byte) speed);

				if (i < left_side_motor_num) {
					motors[i].setDirection(Direction.FORWARD);
				} else {
					motors[i].setDirection(Direction.BACKWARD);
				}

				motors[i].start();
			}
			break;

		case Command.TURNRIGHT:
//			 devices.Console.println("get command: turn right with speed: " +
//			 speed);
			for (int i = 0; i < motors.length; i++) {
				motors[i].setPower((byte) speed);

				if (i < left_side_motor_num) {
					motors[i].setDirection(Direction.BACKWARD);
				} else {
					motors[i].setDirection(Direction.FORWARD);
				}

				motors[i].start();
			}
			break;

		default:
			devices.Console.println("undefined command!: " + command);
			break;
		}

		if (duration > 0) {
			EV3.sleep(duration * 1000);
			for (int i = 0; i < motors.length; i++) {
				motors[i].stop();
			}
		}
	}

	/**
	 * Generate a EV3 command 
	 * @param command the action of the robot
	 * @param speed the speed of the robot
	 * @param numOfLeftMotors the number of left motors of the robot
	 * @param duration the duration of this command
	 * @return a String that carries all the information of the command
	 */
	public String generateCommand(char command, int speed, int numOfLeftMotors, int duration) {
		String com = command + "|" + speed + "|" + numOfLeftMotors + "|" + duration;
		return com;
	}
	
	/**
	 * Generate a EV3 command 
	 * @param command the action of the robot
	 * @param speed the speed of the robot
	 * @param duration the duration of this command
	 * @return a String that carries all the information of the command
	 */
	public String generateCommand(char command, int speed, int duration) {
		String com = command + "|" + speed + "|" + duration;
		return com;
	}

	/**
	 * Generate a EV3 command 
	 * @param command the action of the robot
	 * @param duration the duration of this command
	 * @return a String that carries all the information of the command
	 */
	public String generateCommand(char command, int duration) {
		String com = command + "|" + duration;
		return com;
	}

	private int[] getCommandInfo(String info, int count) {
		String speed = "", letfMotor = "", duration = "";
		int i = 0;

		switch (count) {
		case 1:
			for (; i < info.length(); i++) {
				duration = duration + info.charAt(i) + "";
			}
			commandInfo[0] = convert(duration);
			break;

		case 2:
			for (; i < info.length(); i++) {
				if (info.charAt(i) == '|')
					break;
				speed = speed + info.charAt(i) + "";
			}

			i++;
			for (; i < info.length(); i++) {
				duration = duration + info.charAt(i) + "";
			}

			commandInfo[0] = convert(speed);
			commandInfo[1] = convert(duration);
			break;

		case 3:
			for (; i < info.length(); i++) {
				if (info.charAt(i) == '|')
					break;
				speed = speed + info.charAt(i) + "";
			}

			i++;
			for (; i < info.length(); i++) {
				if (info.charAt(i) == '|')
					break;
				letfMotor = letfMotor + info.charAt(i) + "";
			}

			i++;
			for (; i < info.length(); i++) {
				duration = duration + info.charAt(i) + "";
			}

			commandInfo[0] = convert(speed);
			commandInfo[1] = convert(letfMotor);
			commandInfo[2] = convert(duration);
			break;
		}

		return commandInfo;
	}

	private int convert(String s) {
		if (s == null || s.length() == 0) {
			return -1;
		}

		int ret = 0;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (i == 0 && (c == '-')) {
				return -1;
			}

			if (c - '0' < 0 || c - '0' > 10) {
				devices.Console.println("wrong command: should be int here: " +s);
				throw new IllegalArgumentException();
			}

			int tmp = c - '0';

			ret *= 10;
			ret += tmp;

		}

		return ret;
	}
}
