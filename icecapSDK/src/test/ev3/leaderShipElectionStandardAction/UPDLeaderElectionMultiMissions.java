package test.ev3.leaderShipElectionStandardAction;

import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.LaunchMulticore;
import javax.safetycritical.ManagedMemory;
import javax.safetycritical.ManagedThread;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import com.Network;
import com.UDPCommunication;

import devices.ev3.Button;
import devices.ev3.EV3;
import devices.ev3.Motor;
import devices.ev3.Motor.Direction;
import devices.ev3.MotorPort;
import devices.ev3.MotorPort.MotorPortID;
import icecaptools.IcecapCompileMe;
import leadershipElection.LeaderShipElection;

public class UPDLeaderElectionMultiMissions {
	static String[] ips = { "10.42.0.1", "10.42.0.22", "10.42.0.55", "10.42.0.84" };
	static String networkName = "wlan0";
	static String broadcast_addr = "10.42.0.255";

	static Motor m1;
	static Motor m2;
	static Motor m3;

	static MissionSequencer<Mission> ms;
	static LeaderShipElection leaderElector;
	static int[] ids;
	static ReceiverExecutor receiver_executor;
	
	static String host_ip = null;
	static boolean isLeaderActorStated = false;
	static int orentionControl = 0;
	
	static StorageParameters storageParameters_Sequencer;
	static StorageParameters storageParameters_Handlers;
	static StorageParameters storageParameters_Inner_Sequencer;

	private static class ReceiverExecutor implements Runnable {
		Mission m;

		public ReceiverExecutor(Mission m) {
			this.m = m;
		}

		@Override
		public void run() {
			String[] msg = UDPCommunication.receiveMsg();
			if (!m.terminationPending()) {
				leaderElector.collect(msg[1]);
			}
		}

	}

	private static class Receiver extends ManagedThread {
		Mission m;

		public Receiver(PriorityParameters priority, StorageParameters storage, Mission m) {
			super(priority, storage);
			this.m = m;
			UDPCommunication.createReceiver();
		}

		@Override
		@IcecapCompileMe
		public void run() {
			while (!m.terminationPending()) {
				ManagedMemory.enterPrivateMemory(4000, receiver_executor);
			}

			UDPCommunication.closeReceiver();
			devices.Console.println("receiver closed");
		}
	}

	private static class SenderAndElector extends PeriodicEventHandler {
		int count = -1;
		Mission m;
		Button b;

		public SenderAndElector(PriorityParameters priority, PeriodicParameters release, StorageParameters storage,
				Mission m) {
			super(priority, release, storage);
			this.m = m;
			b = new Button(Button.ButtonID.BACK);
			UDPCommunication.createSender(broadcast_addr);
		}

		@Override
		@IcecapCompileMe
		public void handleAsyncEvent() {
			if (count == 4 || count == -1) {
				leaderElector.electLeader();
				count = 0;
			}

			UDPCommunication.sendBroadcastMsg(leaderElector.StateToNeighbors());
			count++;

			if (b.isPressed()) {
				m.requestTermination();
				UDPCommunication.sendPinPointMessage(Network.getIPAddress(networkName), "mission finished");
				UDPCommunication.closeBroadcastSender();
				devices.Console.println("sender closed");
			}
		}

	}
	
	private static class RobotActor extends MissionSequencer<Mission> {
		Mission m;

		public RobotActor(PriorityParameters priority, StorageParameters storage, Mission m) {
			super(priority, storage, "InnerSeq 3nd");
			this.m = m;
		}

		@Override
		protected Mission getNextMission() {
			if (m.terminationPending()) {
				if(isLeaderActorStated){
					isLeaderActorStated = false;
					m3.stop();
					
					m1.setPower((byte) 50);
					m2.setPower((byte) 50);

					if(host_ip.equals("10.42.0.55")){
						m1.setDirection(Direction.FORWARD);
						m2.setDirection(Direction.FORWARD);
					}else{
						m1.setDirection(Direction.BACKWARD);
						m2.setDirection(Direction.BACKWARD);
					}

					m1.start();
					m2.start();
					
					EV3.sleep(1000);
					
					m1.stop();
					m2.stop();
				}
				devices.Console.println("robot stoped");
				return null;
			}

			if (leaderElector.getState() == LeaderShipElection.Claim.LEADER)
				return new LeaderActor();
			else
				return new FollowerActor();
		}
		
		private static class LeaderActor extends Mission {
			public void initialize() {
				Actor actor = new Actor(new PriorityParameters(10),
						new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()),
								new RelativeTime(1000, 0, Clock.getRealtimeClock())),
						storageParameters_Handlers, this);
				actor.register();
			}

			public long missionMemorySize() {
				return Const.MISSION_MEM;
			}

			private static class Actor extends PeriodicEventHandler {
				LeaderActor m;

				public Actor(PriorityParameters priority, PeriodicParameters release, StorageParameters storage,
						LeaderActor m) {
					super(priority, release, storage);
					this.m = m;
				}

				@Override
				@IcecapCompileMe
				public void handleAsyncEvent() {
					if (leaderElector.getState() != LeaderShipElection.Claim.LEADER) {
						m.requestTermination();
						return;
					}

					if (!isLeaderActorStated) {
						isLeaderActorStated = true;

						m1.setPower((byte) 50);
						m2.setPower((byte) 50);
						
						if(host_ip.equals("10.42.0.55")){
							m1.setDirection(Direction.BACKWARD);
							m2.setDirection(Direction.BACKWARD);
						}else{
							m1.setDirection(Direction.FORWARD);
							m2.setDirection(Direction.FORWARD);
						}

						m1.start();
						m2.start();
						
						EV3.sleep(1000);
						
						m1.stop();
						m2.stop();

					}
					else{
						if(host_ip.equals("10.42.0.84")){
							m3.setPower((byte) 10);
						}else{
							m3.setPower((byte) 100);
						}
						
						if(orentionControl % 2 == 0)
							m3.setDirection(Direction.FORWARD);
						else
							m3.setDirection(Direction.BACKWARD);
						orentionControl++;
						m3.start();
					}
				}
			}
		}
		
		private static class FollowerActor extends Mission {
			public void initialize() {
				Actor actor = new Actor(new PriorityParameters(10),
						new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()),
								new RelativeTime(1000, 0, Clock.getRealtimeClock())),
						storageParameters_Handlers, this);
				actor.register();

			}

			public long missionMemorySize() {
				return Const.MISSION_MEM;
			}

			private static class Actor extends PeriodicEventHandler {
				FollowerActor m;

				public Actor(PriorityParameters priority, PeriodicParameters release, StorageParameters storage,
						FollowerActor m) {
					super(priority, release, storage);
					this.m = m;
				}

				@Override
				@IcecapCompileMe
				public void handleAsyncEvent() {
					if (leaderElector.getState() == LeaderShipElection.Claim.LEADER) {
						m.requestTermination();
						return;
					}

					if (isLeaderActorStated) {
						isLeaderActorStated = false;
						m3.stop();
						
						m1.setPower((byte) 50);
						m2.setPower((byte) 50);

						if(host_ip.equals("10.42.0.55")){
							m1.setDirection(Direction.FORWARD);
							m2.setDirection(Direction.FORWARD);
						}else{
							m1.setDirection(Direction.BACKWARD);
							m2.setDirection(Direction.BACKWARD);
						}

						m1.start();
						m2.start();
						
						EV3.sleep(1000);
						
						m1.stop();
						m2.stop();
						
					}
				}
			}
		}
	}

	private static class MyMission extends Mission {

		@Override
		protected void initialize() {
			MotorPort port = new MotorPort(MotorPortID.B);
			m1 = new Motor(port);

			MotorPort port1 = new MotorPort(MotorPortID.C);
			m2 = new Motor(port1);

			MotorPort port2 = new MotorPort(MotorPortID.A);
			m3 = new Motor(port2);
			
			host_ip = Network.getIPAddress(networkName);
			
			RobotActor actor = new RobotActor(new PriorityParameters(7), storageParameters_Inner_Sequencer, this);
			actor.register();

			ids = new int[ips.length];
			for (int i = 0; i < ips.length; i++) {
				ids[i] = LeaderShipElection.generateID(ips[i]);
			}

			Receiver receiver = new Receiver(new PriorityParameters(6), storageParameters_Handlers, this);
			receiver.register();

			receiver_executor = new ReceiverExecutor(this);

			SenderAndElector sender = new SenderAndElector(new PriorityParameters(6),
					new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()),
							new RelativeTime(1250, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, this);
			sender.register();

			leaderElector = new LeaderShipElection("wlan0", ids);
		}

		@Override
		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

	}

	private static class MySequencer extends MissionSequencer<Mission> {
		private Mission mission;
		private int count = 0;

		public MySequencer(PriorityParameters priority, StorageParameters storage) {
			super(priority, storage);
			mission = new MyMission();
		}

		@Override
		protected Mission getNextMission() {
			if (count == 1) {
				m1.stop();
				m2.stop();
				devices.Console.println("robot stoped");
				UDPCommunication.closeReceiver();
				devices.Console.println("MySeq.count: " + count + "; null");
				return null;
			} else {
				count++;
				return mission;
			}
		}
	}

	private static class MyApp implements Safelet<Mission> {

		@Override
		public MissionSequencer<Mission> getSequencer() {
			ms = new MySequencer(new PriorityParameters(Priorities.SEQUENCER_PRIORITY), storageParameters_Sequencer);
			return ms;
		}

		@Override
		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}

		@Override
		public void initializeApplication() {
		}
	}

	public static void main(String[] args) {
		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, Const.IMMORTAL_MEM, Const.MISSION_MEM);

		storageParameters_Handlers = new StorageParameters(Const.PRIVATE_BACKING_STORE + 4000,
				new long[] { Const.HANDLER_STACK_SIZE }, 4000, 0, 0);
		
		storageParameters_Inner_Sequencer = new StorageParameters(150 * 1000, new long[] { Const.HANDLER_STACK_SIZE },
				Const.PRIVATE_MEM, Const.IMMORTAL_MEM, 30 * 1000);

		devices.Console.println("\n***** test leadership demo .begin *****");
		new LaunchMulticore(new MyApp(), 2);

		devices.Console.println("***** test leadership demo.end *****");

	}
}
