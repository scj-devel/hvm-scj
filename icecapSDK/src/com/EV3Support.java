package com;

import devices.ev3.EV3;
import devices.ev3.Motor;
import devices.ev3.Motor.Direction;

public class EV3Support {
	
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
		int duration = -1;
		int speed = -1;
		int left_side_motor_num = -1;
		
		if(command == Command.START || command == Command.STOP){
			;
		}else{
			int[] generated = getCommandInfo(msg.substring(2));
			duration = generated[0];
			speed = generated[1];
			left_side_motor_num = generated[2];
		}
		
//		if(command != Command.START && command != Command.STOP && speed == -1){
//			devices.Console.println("wrong command, do not specify speed");
//			return;
//		}
		
		if((command == Command.TURNLEFT || command == Command.TURNRIGHT ) && left_side_motor_num == -1){
			devices.Console.println("wrong command, do not specify the number of left side motors");
			return;
		}
		
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
			devices.Console.println("undefined command!");
			break;
		}
		
		if(duration > 0){
			EV3.sleep(duration * 1000);
			for(int i=0; i<m.length; i++){
				m[i].stop();
			}
		}
	}
	
	public static String generateCommand(char command, int speed, int numOfLeftMotors, int duration){
		String com = command + "|" + speed + "|" + numOfLeftMotors + "|" + duration;
		return com;
	}
	
	public static String generateCommand(char command, int speed, int duration){
		String com = command + "|" + speed + "|" + duration;
		return com;
	}
	
	public static String generateCommand(char command, int duration){
		String com = command + "|" + duration;
		return com;
	}
	
	private static int[] getCommandInfo(String info){
		int[] commandInfo = new int[3];
		String speed = "", letfMotor = "", duration = "";
		
		int i=0;
		for(; i<info.length(); i++){
			if(info.charAt(i) == '|')
				break;
			duration  = duration + info.charAt(i) + "";
			
		}
		
		i++;
		for(; i<info.length(); i++){
			if(info.charAt(i) == '|')
				break;
			speed  = speed + info.charAt(i) + "";
			
		}
		
		i++;
		for(; i<info.length(); i++){
			letfMotor  = letfMotor + info.charAt(i) + "";
		}
		
		commandInfo[0] = convert(duration);
		commandInfo[1] = convert(speed);
		commandInfo[2] = convert(letfMotor);
		return commandInfo;
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
