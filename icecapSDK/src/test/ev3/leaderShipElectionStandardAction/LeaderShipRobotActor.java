package test.ev3.leaderShipElectionStandardAction;

import javax.safetycritical.ManagedMemory;

import com.UDPCommunication;

import devices.ev3.EV3;
import devices.ev3.Motor;
import devices.ev3.MotorPort;
import devices.ev3.Motor.Direction;
import devices.ev3.MotorPort.MotorPortID;
import devices.ev3.support.EV3Support;
import leadershipElection.LeaderShipElection;

public class LeaderShipRobotActor {

	private static final String broadcast_addr = "10.42.0.255";
	private int commandCount = 1;
	private boolean clockwise = false;

	private Motor[] motors;
	private EV3Support actor;
	private FollowerExecutor follower_executor;
	private LeaderShipElection elector;
	String currentCommand = null;

	public LeaderShipRobotActor(Motor[] motors, LeaderShipElection elector, boolean isUDPRequired) {
		this.motors = motors;
		this.elector = elector;

		actor = new EV3Support(this.motors);
		follower_executor = new FollowerExecutor();

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
		}

		EV3.sleep(1000);

		for (int i = 0; i < motors.length; i++) {
			motors[i].stop();
		}
	}

	public void standardFollowAction() {
		for (int i = 0; i < motors.length; i++) {

			motors[i].setPower((byte) 50);
			motors[i].setDirection(Direction.BACKWARD);
			motors[i].start();
		}

		EV3.sleep(1000);

		for (int i = 0; i < motors.length; i++) {
			motors[i].stop();
		}
	}

	private void commandRoutine(int count) {
		switch (count) {
		case 1:
			devices.Console.println("forward");

			ManagedMemory.executeInOuterArea(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < motors.length; i++) {
						motors[i].setPower((byte) 50);
						motors[i].setDirection(Direction.FORWARD);
						motors[i].start();
					}

					EV3.sleep(1000);
					for (int i = 0; i < motors.length; i++) {
						motors[i].stop();
					}
				}
			});

			UDPCommunication.sendBroadcastMsg(actor.generateCommand('F', 50, 1));

			break;
		case 2:
			devices.Console.println("backward");

			ManagedMemory.executeInOuterArea(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < motors.length; i++) {
						motors[i].setPower((byte) 50);
						motors[i].setDirection(Direction.BACKWARD);
						motors[i].start();
					}

					EV3.sleep(1000);
					for (int i = 0; i < motors.length; i++) {
						motors[i].stop();
					}
				}
			});

			UDPCommunication.sendBroadcastMsg(actor.generateCommand('B', 50, 1));

			break;
		case 3:
			devices.Console.println("forward");

			ManagedMemory.executeInOuterArea(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < motors.length; i++) {
						motors[i].setPower((byte) 50);
						motors[i].setDirection(Direction.FORWARD);
						motors[i].start();
					}

					EV3.sleep(1000);
					for (int i = 0; i < motors.length; i++) {
						motors[i].stop();
					}
				}
			});

			UDPCommunication.sendBroadcastMsg(actor.generateCommand('F', 50, 1));

			break;
		case 4:
			devices.Console.println("turn left");

			ManagedMemory.executeInOuterArea(new Runnable() {

				@Override
				public void run() {

					motors[0].setPower((byte) 50);
					motors[0].setDirection(Direction.FORWARD);

					motors[1].setPower((byte) 50);
					motors[1].setDirection(Direction.BACKWARD);

					motors[0].start();
					motors[1].start();

					EV3.sleep(1000);

					for (int i = 0; i < motors.length; i++) {
						motors[i].stop();
					}
				}
			});

			UDPCommunication.sendBroadcastMsg(actor.generateCommand('L', 50, 1, 1));
			break;
		case 5:
			devices.Console.println("turn right");

			ManagedMemory.executeInOuterArea(new Runnable() {

				@Override
				public void run() {

					motors[0].setPower((byte) 50);
					motors[0].setDirection(Direction.BACKWARD);

					motors[1].setPower((byte) 50);
					motors[1].setDirection(Direction.FORWARD);

					motors[0].start();
					motors[1].start();

					EV3.sleep(1000);

					for (int i = 0; i < motors.length; i++) {
						motors[i].stop();
					}
				}
			});

			UDPCommunication.sendBroadcastMsg(actor.generateCommand('R', 50, 1, 1));
			break;
		case 6:
			devices.Console.println("forward");

			ManagedMemory.executeInOuterArea(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < motors.length; i++) {
						motors[i].setPower((byte) 50);
						motors[i].setDirection(Direction.FORWARD);
						motors[i].start();
					}

					EV3.sleep(2000);
					for (int i = 0; i < motors.length; i++) {
						motors[i].stop();
					}
				}
			});

			UDPCommunication.sendBroadcastMsg(actor.generateCommand('F', 50, 2));
			break;
		default:
			;
		}

	}

	public void communicationBasedLeaderActor(int commandsCount) {
		MotorPort port = new MotorPort(MotorPortID.B);
		Motor motor_1 = new Motor(port);

		MotorPort port1 = new MotorPort(MotorPortID.C);
		Motor motor_2 = new Motor(port1);

		motors[0] = motor_1;
		motors[1] = motor_2;

		commandRoutine(commandCount);

		if (!clockwise)
			commandCount++;
		else
			commandCount--;

		if (commandCount == commandsCount) {
			clockwise = true;
			commandCount--;
		}

		if (commandCount == 0) {
			clockwise = false;
			commandCount++;
		}

	}

	public void communicationBasedFollowerActor() {
		ManagedMemory.enterPrivateMemory(5000, follower_executor);
		if (elector.getState() == LeaderShipElection.Claim.FOLLOWER || !follower_executor.msg[1].equals("finish")
				|| !follower_executor.msg[1].equals("leader"))
			actor.action();
	}

	private class FollowerExecutor implements Runnable {
		String[] msg = null;

		@Override
		public void run() {
			if (elector.getState() == LeaderShipElection.Claim.FOLLOWER)
				msg = UDPCommunication.receiveMsg();
			devices.Console.println("received: " + msg[1]);
			if (msg[1].equals("finish") || msg[1].equals("leader"))
				return;

			if (elector.getState() == LeaderShipElection.Claim.FOLLOWER)
				actor.getCommand(msg[1]);
		}
	}

}
