package test.ev3.leaderShipElectionCommunicationBasedAction;

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
import com.UDPCommunication;

import devices.ev3.Button;
import devices.ev3.EV3;
import devices.ev3.Motor;
import devices.ev3.MotorPort;
import devices.ev3.MotorPort.MotorPortID;
import icecaptools.IcecapCompileMe;
import leadershipElection.LeaderShipElection;
import test.ev3.leaderShipElectionStandardAction.LeaderShipRobotActor;

public class TCPIPLeaderElectionMultiThreadsCBC {
	static String[] ips = { "10.42.0.22", "10.42.0.55", "10.42.0.84" };
	static String networkName = "wlan0";

	static Motor motor_1;
	static Motor motor_2;
	static Motor[] motors = new Motor[2];

	static LeaderShipElection leaderElector;

	static String host_ip = null;
	static Receiver[] receivers;
	static int[] ids;
	static int receiver_fd = -1;

	static ListenerExecutor listener_executor;

	static Button button_back;
	static LeaderShipRobotActor actor;
	static boolean isUDPRequired = true;

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

			if (button_back.isPressed() || m.terminationPending()) {
				m.requestTermination();
				TCPIPCommunication.closeSender(fd);

				int close = TCPIPCommunication.createTCPIPSender();
				TCPIPCommunication.connectSender(close, host_ip);
				TCPIPCommunication.closeSender(close);

				if (isUDPRequired) {
					UDPCommunication.sendPinPointMessage(host_ip, "finish");
					UDPCommunication.closeBroadcastSender();
					UDPCommunication.closeReceiver();
				}

				devices.Console.println("sender closed");

			}
		}

	}

	private static class Elector extends PeriodicEventHandler {
		Mission m;
		int lastState = LeaderShipElection.Claim.UNDECIDED;

		public Elector(PriorityParameters priority, PeriodicParameters release, StorageParameters storage, Mission m) {
			super(priority, release, storage);
			this.m = m;
		}

		@Override
		@IcecapCompileMe
		public void handleAsyncEvent() {
			if (m.terminationPending()) {
				devices.Console.println("elector exit");
				return;
			}

			lastState = leaderElector.getState();
			leaderElector.electLeader();

			if (isUDPRequired && lastState != LeaderShipElection.Claim.LEADER
					&& leaderElector.getState() == LeaderShipElection.Claim.LEADER)
				UDPCommunication.sendPinPointMessage(host_ip, "leader");
			
		}
	}

	private static class Follower extends ManagedThread {
		Mission m;

		public Follower(PriorityParameters priority, StorageParameters storage, Mission m) {
			super(priority, new StorageParameters(8000,
					new long[] { Const.HANDLER_STACK_SIZE }, 2000, 0, 0));
			this.m = m;
		}

		@Override
		@IcecapCompileMe
		public void run() {
			while (!m.terminationPending()) {
				if (leaderElector.getState() == LeaderShipElection.Claim.FOLLOWER) {
					actor.communicationBasedFollowerActor();
				}else{
					EV3.sleep(1000);
				}
			}

			devices.Console.println("follower actor exit");

		}
	}

	private static class LeaderActor extends PeriodicEventHandler {
		Mission m;

		public LeaderActor(PriorityParameters priority, PeriodicParameters release, StorageParameters storage,
				Mission m) {
			super(priority, release, storage);
			this.m = m;
		}

		@Override
		@IcecapCompileMe
		public void handleAsyncEvent() {
			if (m.terminationPending()) {
				devices.Console.println("leader actor exit");
				return;
			}

			if (leaderElector.getState() == LeaderShipElection.Claim.LEADER) {
				
				actor.communicationBasedLeaderActor(6);
			}

		}
	}

	private static class MyMission extends Mission {

		@Override
		protected void initialize() {
			receivers = new Receiver[LeaderShipElection.MAX_ROBOTS];
			for (int i = 0; i < ips.length; i++) {
				receivers[ids[i]] = new Receiver(new PriorityParameters(5), new AperiodicParameters(null, null),
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

			Elector elector = new Elector(new PriorityParameters(7),
					new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()),
							new RelativeTime(5000, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, this);
			elector.register();
			
			Follower follower = new Follower(new PriorityParameters(20), storageParameters_Handlers,
					this);
			follower.register();

			LeaderActor leaderActor = new LeaderActor(new PriorityParameters(20),
					new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()),
							new RelativeTime(5000, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, this);
			leaderActor.register();
			
			
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
				devices.Console.println("robot stoped");
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

			ids = new int[ips.length];
			for (int i = 0; i < ips.length; i++) {
				ids[i] = LeaderShipElection.generateID(ips[i]);
			}

			host_ip = Network.getIPAddress(networkName);

			leaderElector = new LeaderShipElection(networkName, ids);
			actor = new LeaderShipRobotActor(motors, leaderElector, isUDPRequired);
		}
	}

	static StorageParameters storageParameters_Sequencer;
	static StorageParameters storageParameters_Handlers;
	static StorageParameters storageParameters_Listeners_Receivers;

	public static void main(String[] args) {
		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, Const.IMMORTAL_MEM, Const.MISSION_MEM);

		storageParameters_Handlers = new StorageParameters(Const.PRIVATE_BACKING_STORE / 2,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM / 2, 0, 0);

		storageParameters_Listeners_Receivers = new StorageParameters(Const.PRIVATE_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, 0, 0);

		devices.Console.println("\n***** test leadership demo .begin *****");
		new LaunchMulticore(new MyApp(), 2);

		devices.Console.println("***** test leadership demo.end *****");

	}
}
