package test.ev3.leaderShipElection;

import javax.safetycritical.ManagedMemory;

import com.EV3Support;
import com.UDPCommunication;

import devices.ev3.EV3;
import devices.ev3.Motor;
import devices.ev3.Motor.Direction;
import leadershipElection.LeaderShipElection;

public class LeaderShipRobotActor {

	private static final String broadcast_addr = "10.42.0.255";
	private int commandCount = 0;

	private Motor[] motors;
	private EV3Support actor;
	private Executor executor;
	private LeaderShipElection elector;

	public LeaderShipRobotActor(Motor[] motors, LeaderShipElection elector, boolean isUDPRequired) {
		this.motors = motors;
		this.elector = elector;

		actor = new EV3Support(this.motors);
		executor = new Executor();

		if (isUDPRequired) {
			UDPCommunication.createSender(broadcast_addr);
			UDPCommunication.createReceiver();
		}
	}

	public void standardLeaderAction() {
		for (int i = 0; i < motors.length; i++) {
			motors[i].setPower((byte) 50);
			motors[i].setDirection(Direction.FORWARD);
			motors[i].start();

			EV3.sleep(1000);

			motors[i].stop();
		}
	}

	public void standardFollowAction() {
		for (int i = 0; i < motors.length; i++) {
			motors[i].setPower((byte) 50);
			motors[i].setDirection(Direction.BACKWARD);
			motors[i].start();

			EV3.sleep(1000);

			motors[i].stop();
		}
	}

	private void commandRoutine(int count) {
		switch (count) {
		case 1:
			devices.Console.println("forward");
			UDPCommunication.sendBroadcastMsg(actor.generateCommand('F', 50, 1));
			break;
		case 2:
			devices.Console.println("backward");
			UDPCommunication.sendBroadcastMsg(actor.generateCommand('B', 50, 1));
			break;
		case 3:
			devices.Console.println("forward");
			UDPCommunication.sendBroadcastMsg(actor.generateCommand('F', 50, 1));
			break;
		case 4:
			devices.Console.println("move faster");
			UDPCommunication.sendBroadcastMsg(actor.generateCommand('C', 100, 1));
			break;
		case 5:
			devices.Console.println("start");
			UDPCommunication.sendBroadcastMsg(actor.generateCommand('S', 1));
			break;
		case 6:
			devices.Console.println("move slower");
			UDPCommunication.sendBroadcastMsg(actor.generateCommand('C', 50, 1));
			break;
		case 7:
			devices.Console.println("turn left");
			UDPCommunication.sendBroadcastMsg(actor.generateCommand('L', 50, 1, 1));
			break;
		case 8:
			devices.Console.println("turn right");
			UDPCommunication.sendBroadcastMsg(actor.generateCommand('R', 50, 1, 1));
			break;
		case 9:
			devices.Console.println("forward");
			UDPCommunication.sendBroadcastMsg(actor.generateCommand('F', 50, 1));
			break;
		default:
			;
		}

	}

	public void communicationBasedLeaderActor(int sender_fd) {
		commandCount++;
		commandRoutine(commandCount);
		if (commandCount == 12)
			commandCount = 0;
	}

	public void communicationBasedFollowerActor() {
		ManagedMemory.enterPrivateMemory(5000, executor);
		if (elector.getState() == LeaderShipElection.Claim.FOLLOWER)
			actor.action();
	}

	private class Executor implements Runnable {
		@Override
		public void run() {
			String[] msg = null;
			if (elector.getState() == LeaderShipElection.Claim.FOLLOWER)
				msg = UDPCommunication.receiveMsg();
			//devices.Console.println("received: " + msg[1]);
			
			if (elector.getState() == LeaderShipElection.Claim.FOLLOWER)
				actor.getCommand(msg[1]);
		}

	}

}
