package test.ev3.leaderShipElection;

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.AperiodicEventHandler;
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
import com.TCPIPCommunication;

import devices.ev3.Button;
import devices.ev3.Motor;
import devices.ev3.MotorPort;
import devices.ev3.MotorPort.MotorPortID;
import icecaptools.IcecapCompileMe;
import leadershipElection.LeaderShipElection;

public class TCPIPLeaderElectionMultiMissions {
	static String[] ips = { "10.42.0.22", "10.42.0.55", "10.42.0.84" };
	static String networkName = "wlan0";

	static Motor motor_1;
	static Motor motor_2;
	static Motor[] motors= new Motor[2];
	static LeaderShipElection leaderElector;

	static String host_ip = null;
	static Receiver[] receivers;
	static int[] ids;
	static int receiver_fd = -1;

	static ListenerExecutor listener_executor;

	static Button back_button;
	static boolean isButtonBackPressed = false;

	private static class Listener extends ManagedThread {
		Mission m;

		public Listener(PriorityParameters priority, StorageParameters storage, Mission m) {
			super(priority, storage);
			this.m = m;
			receiver_fd = TCPIPCommunication.createTCPIPReceiver(0);
		}

		@Override
		@IcecapCompileMe
		public void run() {
			while (!m.terminationPending()) {
				ManagedMemory.enterPrivateMemory(2000, listener_executor);
			}

			TCPIPCommunication.closeReceiver(receiver_fd);
			devices.Console.println("listener closed");
		}
	}

	private static class ListenerExecutor implements Runnable {
		Mission m;

		public ListenerExecutor(Mission m) {
			this.m = m;
		}

		@Override
		public void run() {
			int[] peerInfo;
			peerInfo = TCPIPCommunication.listenForConnection(receiver_fd, 10);
			devices.Console.println("accept connection: " + peerInfo[1]);

			if (!m.terminationPending()) {
				receivers[peerInfo[1]].executor.newfd = peerInfo[0];
				receivers[peerInfo[1]].executor.loseConnection = false;
				receivers[peerInfo[1]].release();
			}
		}

	}

	private static class Receiver extends AperiodicEventHandler {
		Mission m;
		ReceiverExecutor executor;
		int id;

		public Receiver(PriorityParameters priority, AperiodicParameters release, StorageParameters storage, Mission m,
				ReceiverExecutor executor, int id) {
			super(priority, release, storage);
			this.m = m;
			this.executor = executor;
			this.id = id;
		}

		@Override
		public void handleAsyncEvent() {
			while (!m.terminationPending()) {
				ManagedMemory.enterPrivateMemory(2000, executor);
				if (executor.loseConnection)
					break;
			}

			devices.Console.println("receiver " + id + "closed");
		}

	}

	private static class ReceiverExecutor implements Runnable {
		Mission m;
		int newfd;
		boolean loseConnection = false;

		public ReceiverExecutor(Mission m) {
			this.m = m;
		}

		@Override
		public void run() {
			String msg = TCPIPCommunication.receiveMsg(newfd);
			if (msg.length() == 0) {
				loseConnection = true;
			}

			if (!m.terminationPending() && msg.length() != 0)
				leaderElector.collect(msg);
		}

	}

	private static class Sender extends PeriodicEventHandler {
		Mission m;
		String neighbor_ip;
		int fd;
		boolean isConnected = false;

		public Sender(PriorityParameters priority, PeriodicParameters release, StorageParameters storage, Mission m,
				String ip) {
			super(priority, release, storage);
			this.m = m;
			this.neighbor_ip = ip;
			
		}

		@Override
		@IcecapCompileMe
		public void handleAsyncEvent() {
			if (!isConnected) {
				fd = TCPIPCommunication.createTCPIPSender();

				int result = TCPIPCommunication.connectSender(fd, neighbor_ip);
				if (result == 0) {
					isConnected = true;
				} else {
					isConnected = false;
				}
			}

			if (isConnected) {
				int result = TCPIPCommunication.sendMsg(fd, leaderElector.StateToNeighbors());

				if (result == -1) {
					isConnected = false;
					TCPIPCommunication.closeSender(fd);
				}
			} else {
				TCPIPCommunication.closeSender(fd);
			}

			if (back_button.isPressed()) {
				isButtonBackPressed = true;
			}

			if (isButtonBackPressed || m.terminationPending()) {
				m.requestTermination();
				TCPIPCommunication.closeSender(fd);

				int close = TCPIPCommunication.createTCPIPSender();
				TCPIPCommunication.connectSender(close, host_ip);
				TCPIPCommunication.closeSender(close);

				devices.Console.println("sender closed");

			}
		}

	}

	private static class Elector extends PeriodicEventHandler {

		Mission m;
		
		public Elector(PriorityParameters priority, PeriodicParameters release, StorageParameters storage, Mission m) {
			super(priority, release, storage);
			this.m = m;
		}

		@Override
		@IcecapCompileMe
		public void handleAsyncEvent() {
			if(!m.terminationPending()){
				devices.Console.println("elector exit");
				return;
			}
				
			leaderElector.electLeader();
		}

	}

	private static class RobotActor extends MissionSequencer<Mission> {
		Mission m;
		static boolean isLeaderAlready = false;
		static LeaderShipRobotActor actor; 

		public RobotActor(PriorityParameters priority, StorageParameters storage, Mission m) {
			super(priority, storage, "InnerSeq 3nd");
			this.m = m;
			actor = new LeaderShipRobotActor(motors, leaderElector,false);
		}

		@Override
		protected Mission getNextMission() {
			if (m.terminationPending()) {
				if (isLeaderAlready) {
					isLeaderAlready = false;
					actor.standardFollowAction();
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

					if (!isLeaderAlready) {
						isLeaderAlready = true;
						actor.standardLeaderAction();
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

					if (isLeaderAlready) {
						isLeaderAlready = false;
						actor.standardFollowAction();
					}
				}
			}
		}
	}

	private static class MyMission extends Mission {

		@Override
		protected void initialize() {
			devices.Console.println("Initing");
			
			receivers = new Receiver[LeaderShipElection.MAX_ROBOTS];
			for (int i = 0; i < ips.length; i++) {
				receivers[ids[i]] = new Receiver(new PriorityParameters(6), new AperiodicParameters(null, null),
						storageParameters_Listeners_Receivers, this, new ReceiverExecutor(this), ids[i]);
				receivers[ids[i]].register();
			}

			Listener listener = new Listener(new PriorityParameters(5), storageParameters_Listeners_Receivers, this);
			listener.register();
			listener_executor = new ListenerExecutor(this);

			Sender[] senders = new Sender[ips.length];
			for (int i = 0; i < senders.length; i++) {
				senders[i] = new Sender(new PriorityParameters(6),
						new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()),
								new RelativeTime(1000, 0, Clock.getRealtimeClock())),
						storageParameters_Handlers, this, ips[i]);
				senders[i].register();
			}

			Elector elector = new Elector(new PriorityParameters(8),
					new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()),
							new RelativeTime(5000, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, this);
			elector.register();
			
			RobotActor actor = new RobotActor(new PriorityParameters(4), storageParameters_Actor_Sequencer, this);
			actor.register();

			devices.Console.println("Initized");
		}

		@Override
		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

	}

	private static class MySequencer extends MissionSequencer<Mission> {
		private Mission mission;

		public MySequencer(PriorityParameters priority, StorageParameters storage) {
			super(priority, storage);
			mission = new MyMission();
		}

		@Override
		protected Mission getNextMission() {
			if (isButtonBackPressed) {
				TCPIPCommunication.closeReceiver(receiver_fd);
				return null;
			}

			return mission;
		}
	}

	private static class MyApp implements Safelet<Mission> {

		@Override
		public MissionSequencer<Mission> getSequencer() {
			return new MySequencer(new PriorityParameters(Priorities.SEQUENCER_PRIORITY), storageParameters_Sequencer);
		}

		@Override
		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}

		@Override
		public void initializeApplication() {
			MotorPort port = new MotorPort(MotorPortID.B);
			motor_1 = new Motor(port);

			MotorPort port1 = new MotorPort(MotorPortID.C);
			motor_2 = new Motor(port1);

			motors[0] = motor_1;
			motors[1] = motor_2;
			
			back_button = new Button(Button.ButtonID.BACK);
			
			ids = new int[ips.length];
			for (int i = 0; i < ips.length; i++) {
				ids[i] = LeaderShipElection.generateID(ips[i]);
			}
			host_ip = Network.getIPAddress(networkName);
			
			leaderElector = new LeaderShipElection(networkName, ids);

		}
	}
	
	static StorageParameters storageParameters_Sequencer;
	static StorageParameters storageParameters_Actor_Sequencer;
	static StorageParameters storageParameters_Handlers;
	static StorageParameters storageParameters_Listeners_Receivers;

	public static void main(String[] args) {
		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, Const.IMMORTAL_MEM, Const.MISSION_MEM);

		storageParameters_Actor_Sequencer = new StorageParameters(150 * 1000, new long[] { Const.HANDLER_STACK_SIZE },
				Const.PRIVATE_MEM, Const.IMMORTAL_MEM, 30 * 1000);

		storageParameters_Handlers = new StorageParameters(Const.PRIVATE_BACKING_STORE / 2 - 5 * 1000,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM / 2 - 5 * 1000, 0, 0);

		storageParameters_Listeners_Receivers = new StorageParameters(Const.PRIVATE_BACKING_STORE - 10 * 1000,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM - 5 * 1000, 0, 0);

		devices.Console.println("\n***** test leadership demo .begin *****");
		new LaunchMulticore(new MyApp(), 2);

		devices.Console.println("***** test leadership demo.end *****");

	}
}
