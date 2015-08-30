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
import com.TCPIPCommunication;

import devices.ev3.Button;
import devices.ev3.Motor;
import devices.ev3.MotorPort;
import devices.ev3.MotorPort.MotorPortID;
import icecaptools.IcecapCompileMe;
import leadershipElection.LeaderShipElection;

public class TCPIPLeaderElectionSequential {
	static String[] ips = { "10.42.0.22", "10.42.0.55", "10.42.0.84" };
	static String networkName = "wlan0";

	static Motor motor_1;
	static Motor motor_2;
	static Motor[] motors= new Motor[2];
	static Button button_back;

	static String host_ip = null;
	static LeaderShipElection leaderElector;
	static int orentionControl = 0;

	static int[] ids;
	static Connector[] connectors = new Connector[LeaderShipElection.MAX_ROBOTS];
	static Receiver[] receivers = new Receiver[LeaderShipElection.MAX_ROBOTS];
	static int receiver_fd = -1;

	private static class Listener extends ManagedThread {
		Mission m;
		ListenerExecutor listener_executor;

		public Listener(PriorityParameters priority, StorageParameters storage, Mission m) {
			super(priority, storage);
			this.m = m;
			receiver_fd = TCPIPCommunication.createTCPIPReceiver(0);
			this.listener_executor = new ListenerExecutor(receiver_fd);
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
		int receiver_fd = -1;

		public ListenerExecutor(int receiver_fd) {
			this.receiver_fd = receiver_fd;
		}

		@Override
		public void run() {
			int[] peerInfo;
			peerInfo = TCPIPCommunication.listenForConnection(receiver_fd, 10);
			receivers[peerInfo[1]].neighbor_fd = peerInfo[0];
			receivers[peerInfo[1]].isConnected = true;
			devices.Console.println("receiver " + peerInfo[1] + " started");
		}
	}

	private static class Receiver {
		boolean isConnected = false;
		int neighbor_fd = -1;
		Mission m;
		int id = -1;

		public Receiver(int id, Mission m) {
			this.id = id;
			this.m = m;
		}

		void receiveMsg() {
			if (isConnected) {
				String msg = TCPIPCommunication.receiveMsg(neighbor_fd);
				if (msg.length() == 0) {
					isConnected = false;
					devices.Console.println("receiver " + id + " closed");
					return;
				}

				if (!m.terminationPending())
					leaderElector.collect(msg);
			}

		}

	}

	private static class Connector extends PeriodicEventHandler {
		String neighbor_ip;
		int fd;
		boolean isConnected = false;
		Mission m;

		public Connector(PriorityParameters priority, PeriodicParameters release, StorageParameters storage, Mission m,
				String ip) {
			super(priority, release, storage);
			this.neighbor_ip = ip;
			this.m = m;
		}

		@Override
		@IcecapCompileMe
		public void handleAsyncEvent() {
			if (!isConnected) {
				fd = TCPIPCommunication.createTCPIPSender();

				int result = TCPIPCommunication.connectSender(fd, neighbor_ip);
				if (result == 0) {
					devices.Console.println("connect to: " + neighbor_ip);
					isConnected = true;
				} else {
					isConnected = false;
				}
			}

			if (m.terminationPending()) {
				TCPIPCommunication.closeSender(fd);
				return;
			}
		}

		synchronized void sendState() {
			if (isConnected) {
				int result = TCPIPCommunication.sendMsg(fd, leaderElector.StateToNeighbors());
				if (result == -1) {
					isConnected = false;
					TCPIPCommunication.closeSender(fd);
					devices.Console.println(neighbor_ip + " disconnected");
				}
			}
		}

		// synchronized void sendCommand(int commandNo) {
		// if (isConnected) {
		// int result = TCPIPCommunication.sendMsg(fd,
		// leaderElector.StateToNeighbors());
		// if (result == -1) {
		// isConnected = false;
		// TCPIPCommunication.closeSender(fd);
		// devices.Console.println(neighbor_ip + " disconnected");
		// }
		// }
		// }

	}

	private static class Elector extends PeriodicEventHandler {
		// private boolean isElectionStarted = false;
		static boolean isLeaderAlready = false;
		Mission m;
		LeaderShipRobotActor actor;

		public Elector(PriorityParameters priority, PeriodicParameters release, StorageParameters storage, Mission m) {
			super(priority, release, storage);
			this.m = m;
			actor = new LeaderShipRobotActor(motors, leaderElector,false);
		}

		@Override
		@IcecapCompileMe
		public void handleAsyncEvent() {
			if (button_back.isPressed()) {
				m.requestTermination();
				int close = TCPIPCommunication.createTCPIPSender();
				TCPIPCommunication.connectSender(close, Network.getIPAddress(networkName));
				TCPIPCommunication.closeSender(close);

				if (isLeaderAlready) {
					isLeaderAlready = false;
					actor.standardFollowAction();
					devices.Console.println("robot stoped");
				}
				devices.Console.println("elector exit");
				return;
			}

			// if (!isElectionStarted) {
			// leaderElector.electLeader();
			// isElectionStarted = true;
			// } else {
			for (int i = 0; i < ips.length; i++) {
				connectors[ids[i]].sendState();
			}
			for (int i = 0; i < ips.length; i++) {
				receivers[ids[i]].receiveMsg();
			}

			leaderElector.electLeader();
			action();
			// }
		}

		void action() {
			if (leaderElector.getState() == LeaderShipElection.Claim.LEADER) {
				if (!isLeaderAlready) {
					isLeaderAlready = true;
					actor.standardLeaderAction();
				}
			} else {
				if (isLeaderAlready) {
					isLeaderAlready = false;
					actor.standardFollowAction();
				}
			}
		}
	}

	private static class MyMission extends Mission {

		@Override
		protected void initialize() {
			Listener listener = new Listener(new PriorityParameters(5), storageParameters_Listeners, this);
			listener.register();

			for (int i = 0; i < ips.length; i++) {
				connectors[ids[i]] = new Connector(new PriorityParameters(6),
						new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()),
								new RelativeTime(500, 0, Clock.getRealtimeClock())),
						storageParameters_Handlers, this, ips[i]);
				connectors[ids[i]].register();

				receivers[ids[i]] = new Receiver(ids[i], this);
			}

			Elector elector = new Elector(new PriorityParameters(7),
					new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()),
							new RelativeTime(2000, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, this);
			elector.register();

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
				TCPIPCommunication.closeReceiver(receiver_fd);
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

			button_back = new Button(Button.ButtonID.BACK);

			host_ip = Network.getIPAddress(networkName);

			ids = new int[ips.length];
			for (int i = 0; i < ips.length; i++) {
				ids[i] = LeaderShipElection.generateID(ips[i]);
			}

			leaderElector = new LeaderShipElection(networkName, ids);
		}
	}

	static StorageParameters storageParameters_Sequencer;
	static StorageParameters storageParameters_Handlers;
	static StorageParameters storageParameters_Listeners;

	public static void main(String[] args) {
		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, Const.IMMORTAL_MEM, Const.MISSION_MEM);

		storageParameters_Handlers = new StorageParameters(Const.PRIVATE_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, 0, 0);

		storageParameters_Listeners = new StorageParameters(Const.PRIVATE_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, 0, 0);

		devices.Console.println("\n***** test leadership demo TCP Sequential.begin *****");
		new LaunchMulticore(new MyApp(), 2);

		devices.Console.println("***** test leadership demo TCP Sequential.end *****");

	}
}
