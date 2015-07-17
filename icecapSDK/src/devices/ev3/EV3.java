package devices.ev3;

import devices.ev3.Motor.Direction;

public class EV3 {

	public static native void sleep(int milliseconds);
	
	interface Command {
		public final static char FORWARD = 'F';
		public final static char BACKFORD = 'B';
		public final static char TURNLEFT = 'L';
		public final static char TURNRIGHT = 'R';
		public final static char START = 'S';
		public final static char STOP = 'P';
		public final static char CHANGESPEED = 'C';
	}
	
	public static void action(String msg, Motor[] m){
		char command = msg.charAt(0);
		int speed = -1;
		int left_side_motor_num = -1;
		
		
		if(command == Command.START || command == Command.STOP){
			;
		}else{
			int[] generated = getSpeedAndLeftMotors(msg.substring(2));
			speed = generated[0];
			left_side_motor_num = generated[1];
		}
		
		if(command != Command.START && command != Command.STOP && speed == -1){
			devices.Console.println("wrong command, do not specify speed");
			return;
		}
		
		if((command == Command.TURNLEFT || command == Command.TURNRIGHT ) && left_side_motor_num == -1){
			devices.Console.println("wrong command, do not number of left side motors");
			return;
		}
		
		devices.Console.println("command: " + command + " speed: " + speed + " letfmotors: " + left_side_motor_num);
		
		switch (command){
		case Command.FORWARD:
//			devices.Console.println("get command: forward with speed: " + speed);
			for(int i=0; i<m.length; i++){
				m[i].setPower((byte) speed);
				m[i].setDirection(Direction.FORWARD);
				m[i].start();
			}
			break;
			
		case Command.BACKFORD:
//			devices.Console.println("get command: backforward with speed: " + speed);
			for(int i=0; i<m.length; i++){
				m[i].setPower((byte) speed);
				m[i].setDirection(Direction.BACKWARD);
				m[i].start();
			}
			break;
			
		case Command.START:
//			devices.Console.println("get command: start");
			for(int i=0; i<m.length; i++){
				m[i].start();
			}
			break;
			
		case Command.STOP:
//			devices.Console.println("get command: stop");
			for(int i=0; i<m.length; i++){
				m[i].stop();
			}
			break;
			
		case Command.CHANGESPEED:
//			devices.Console.println("get command: change speed to: " + speed);
			for(int i=0; i<m.length; i++){
				m[i].setPower((byte) speed);
			}
			break;
			
		case Command.TURNLEFT:
//			devices.Console.println("get command: turn left with speed: " + speed);
			for(int i=0; i<m.length; i++){
				m[i].setPower((byte) speed);
				
				if(i<left_side_motor_num){
					m[i].setDirection(Direction.BACKWARD);
				}
				else{
					m[i].setDirection(Direction.FORWARD);
				}
				
				m[i].start();
			}
			break;
			
		case Command.TURNRIGHT:
//			devices.Console.println("get command: turn right with speed: " + speed);
			for(int i=0; i<m.length; i++){
				m[i].setPower((byte) speed);
				
				if(i<left_side_motor_num){
					m[i].setDirection(Direction.FORWARD);
				}
				else{
					m[i].setDirection(Direction.BACKWARD);
				}
				
				m[i].start();
			}
			break;
			
		default:
			devices.Console.println("do not chatting... time is money!");
			break;
		}
	}
	
	public static String generateCommand(char command, int speed, int numOfLeftMotors){
		String com = command + "|" + speed + "|" + numOfLeftMotors;
		return com;
	}
	
	public static String generateCommand(char command, int speed){
		String com = command + "|" + speed;
		return com;
	}
	
	public static String generateCommand(char command){
		String com = command + "";
		return com;
	}
	
	private static int[] getSpeedAndLeftMotors(String info){
		int [] speedAndLeftMotor = new int[2];
		String speed = "", letfMotor = "";
		
		int i=0;
		for(; i<info.length(); i++){
			if(info.charAt(i) == '|')
				break;
			speed  = speed + info.charAt(i) + "";
		}
		
		i++;
		for(; i<info.length(); i++){
			letfMotor  = letfMotor + info.charAt(i) + "";
		}
		
		speedAndLeftMotor[0] = convert(speed);
		speedAndLeftMotor[1] = convert(letfMotor);
		
		return speedAndLeftMotor;
	}
	
	

	
	private static int convert(String s){
	    if(s == null || s.length() == 0){
	    	return -1;
	    }

	    int ret = 0;

	    for(int i=0;i<s.length();i++){
	        char c = s.charAt(i);

	        if( i == 0 && (c == '-')){
	            return -1;
	        }

	        if(c - '0' < 0 || c - '0' > 10){
	        	devices.Console.println("wrong command: should be int here");
	            throw new IllegalArgumentException();
	        }

	        int tmp = c - '0';

	        ret *= 10;
	        ret += tmp;

	    }

	    return ret;
	}
}
