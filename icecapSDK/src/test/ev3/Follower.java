package test.ev3;

import com.EV3Support;
import com.UDPCommunication;

import devices.ev3.Motor;
import devices.ev3.MotorPort;
import devices.ev3.MotorPort.MotorPortID;

public class Follower {

	public static void main(String args[]){
		MotorPort port = new MotorPort(MotorPortID.B);
		Motor m = new Motor(port);
		
		MotorPort port1 = new MotorPort(MotorPortID.C);
		Motor m1 = new Motor(port1);
		
		Motor[] motor= {m,m1};
		
		UDPCommunication.createReceiver();
		
		
		for(;;){
			String[] msg = UDPCommunication.receiveMsg();
			devices.Console.println("got message: " + msg[1] + " from: " + msg[0]);
			EV3Support.action(msg[1], motor);
		}
		
	}
}
