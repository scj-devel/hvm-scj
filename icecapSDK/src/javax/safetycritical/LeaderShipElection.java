package javax.safetycritical;

public class LeaderShipElection {

	public interface Claim {
		public final static byte LEADER = 0;
		public final static byte FOLLOWER = 1;
		public final static byte UNDECIDED = 2;
	}

	byte state = Claim.UNDECIDED;
	int petition = 0;
	int id = 0;

	Neighbor neighbor;
	RobotInfoStore robotStore;

	private static String[] neighbors = new String[4];
	private static int time = 0;
	protected int motorCount = 0;

	PrivateMemory mem1;
	PrivateMemory mem22;
	PrivateMemory mem55;
	PrivateMemory mem84;

	String robotInfo = null;

	public LeaderShipElection(Mission mission, String networkName, StorageParameters storage) {
		initLeadershipElection(mission, networkName, storage);
		neighbors[0] = "2*1*1*0";
		neighbors[1] = "2*22*22*0";
		neighbors[2] = "2*55*55*0";
		neighbors[3] = "2*84*84*0";

		neighbor = new Neighbor();
		robotStore = new RobotInfoStore();
	}

	void initLeadershipElection(Mission mission, String networkName, StorageParameters storage) {
		String ip = UDPCommunication.getIPAddress(null, networkName);

		String lastDigit = "";
		int countDot = 0;
		int i = 0;
		for (; i < ip.length(); i++) {
			if (ip.charAt(i) == '.') {
				countDot++;
			}
			if (countDot == 3) {
				i++;
				break;
			}
		}

		for (int j = i; j < ip.length(); j++) {
			lastDigit = lastDigit + ip.charAt(j);
		}

		id = convert(lastDigit);
		devices.Console.println("ID: " + id);
		petition = id;

		mem1 = new PrivateMemory((int) storage.getMaximalMemoryArea(), (int) storage.totalBackingStore,
				mission.currMissSeq.missionMemory, "mem_robot1");
		mem22 = new PrivateMemory((int) storage.getMaximalMemoryArea(), (int) storage.totalBackingStore,
				mission.currMissSeq.missionMemory, "mem_robot22");
		mem55 = new PrivateMemory((int) storage.getMaximalMemoryArea(), (int) storage.totalBackingStore,
				mission.currMissSeq.missionMemory, "mem_robot55");
		mem84 = new PrivateMemory((int) storage.getMaximalMemoryArea(), (int) storage.totalBackingStore,
				mission.currMissSeq.missionMemory, "mem_robot84");

	}

	public void electLeader() {
		if (time != 0) {
			printMyNeighbors();
			setState();
		}
		neighbor.increaseTime();
	}

	public void collect(ManagedSchedulable ms, String state) {
		ManagedMemory memory = null;

		if (ms != null) {
			if (ms instanceof ManagedEventHandler) {
				memory = ((ManagedEventHandler) ms).privateMemory;
			} else {
				memory = ((ManagedThread) ms).privateMemory;
			}
		}

		if (memory != null)
			memory.resetArea(0);

		neighbor.setNeighbor(state);
	}

	public void sendStateToNeighbors() {
		String msg = state + "*" + id + "*" + petition;
		UDPCommunication.sendBroadcastMsg(msg);
	}

	public void clearLeaderElection() {
		mem1.removeArea();
		mem22.removeArea();
		mem55.removeArea();
		mem84.removeArea();
	}

	private class Neighbor {

		Neighbor() {
			Services.setCeiling(this, 20);
		}

		synchronized void setNeighbor(String state) {
			robotInfo = state;
			switch (state.charAt(2)) {
			case '1':
				mem1.resetArea();
				mem1.executeInArea(robotStore);
				break;
			case '2':
				mem22.resetArea();
				mem22.executeInArea(robotStore);
				break;
			case '5':
				mem55.resetArea();
				mem55.executeInArea(robotStore);
				break;
			case '8':
				mem84.resetArea();
				mem84.executeInArea(robotStore);
				break;
			default:
				devices.Console.println("unidentified device:" + state.charAt(2) + "." + " state: " + state);
				break;
			}
		}

		synchronized String getNeighbor(int index) {
			return neighbors[index];
		}

		synchronized void increaseTime() {
			time++;
		}
	}

	class RobotInfoStore implements Runnable {

		@Override
		public void run() {
			String state = robotInfo + "*" + time;

			switch (state.charAt(2)) {
			case '1':
				neighbors[0] = state;
				break;
			case '2':
				neighbors[1] = state;
				break;
			case '5':
				neighbors[2] = state;
				break;
			case '8':
				neighbors[3] = state;
				break;
			default:
				devices.Console.println("unidentified device: " + id);
				throw new IllegalArgumentException();
			}

		}
	}

	void setState() {
		switch (state) {
		case Claim.UNDECIDED:
			if (numberOfLeaders() == 1) {
				state = Claim.FOLLOWER;
			} else if (numberOfLeaders() == 0 && hasHighestPetition() == 1) {
				state = Claim.LEADER;
			} else {
				state = Claim.FOLLOWER;
			}
			break;

		case Claim.FOLLOWER:
			if (numberOfLeaders() == 1) {

			} else if (numberOfLeaders() == 0 && hasHighestPetition() == 1) {
				state = Claim.LEADER;

			} else if (numberOfLeaders() == 0 && hasHighestPetition() == 0 && hasHighestID()) {
				state = Claim.LEADER;
			} else {

			}
			break;

		case Claim.LEADER:
			if (numberOfLeaders() == 0) {
				petition++;
			} else {
				state = Claim.UNDECIDED;
			}
			break;
		}

		switch (state) {
		case Claim.UNDECIDED:
			devices.Console.println("undecide");
			break;
		case Claim.FOLLOWER:
			devices.Console.println("follower");
			break;
		case Claim.LEADER:
			devices.Console.println("leader");
			break;
		}
	}

	int hasHighestPetition() {
		int hasHighestPetition = 0;
		int equal = -1;

		for (int i = 0; i < 4; i++) {
			if (getNeighborTime(neighbor.getNeighbor(i)) < time || getNeighborId(neighbor.getNeighbor(i)) == id) {
				continue;
			}

			if (getNeighborPetition(neighbor.getNeighbor(i)) > petition) {
				hasHighestPetition = -1;
				break;
			} else if (getNeighborPetition(neighbor.getNeighbor(i)) == petition) {
				equal = 0;
			}
		}

		if (hasHighestPetition == -1)
			return -1;
		else if (hasHighestPetition == 0 && equal == -1)
			return 1;
		else if (hasHighestPetition == 0 && equal == 0) {
			return 0;
		} else {
			return -1;
		}
	}

	boolean hasHighestID() {
		boolean hasHighestID = true;

		for (int i = 0; i < 4; i++) {
			if (getNeighborTime(neighbor.getNeighbor(i)) < time || getNeighborId(neighbor.getNeighbor(i)) == id) {
				continue;
			}
			if (getNeighborId(neighbor.getNeighbor(i)) > petition) {
				hasHighestID = false;
				break;
			}
		}

		return hasHighestID;
	}

	int numberOfLeaders() {
		int number_of_leaders = 0;

		for (int i = 0; i < 4; i++) {
			if (getNeighborTime(neighbor.getNeighbor(i)) < time || getNeighborId(neighbor.getNeighbor(i)) == id) {
				continue;
			}
			String state = neighbor.getNeighbor(i).charAt(0) + "";
			if (convert(state) == Claim.LEADER)
				number_of_leaders++;
		}

		return number_of_leaders;
	}

	int getNeighborId(String info) {
		int id = -1;
		int first_index = -1, second_index = -1;

		int i = 0;
		for (; i < info.length(); i++) {
			if (info.charAt(i) == '*') {
				first_index = i;
				break;
			}
		}
		i++;
		for (; i < info.length(); i++) {
			if (info.charAt(i) == '*') {
				second_index = i;
				break;
			}
		}
		id = convert(info.substring(first_index + 1, second_index));
		return id;
	}

	int getNeighborPetition(String info) {
		int petition = -1;
		int count = 0;
		int first_index = -1, second_index = -1;

		int i = 0;
		for (; i < info.length(); i++) {
			if (info.charAt(i) == '*') {
				count++;
				if (count == 2) {
					first_index = i;
					break;
				}
			}
		}
		i++;
		for (; i < info.length(); i++) {
			if (info.charAt(i) == '*') {
				second_index = i;
				break;
			}
		}
		petition = convert(info.substring(first_index + 1, second_index));
		return petition;
	}

	int getNeighborTime(String info) {
		int time = -1;
		int count = 0;

		int i = 0;
		for (; i < info.length(); i++) {
			if (info.charAt(i) == '*') {
				count++;
				if (count == 3) {
					break;
				}
			}
		}
		i++;

		time = convert(info.substring(i));
		return time;
	}

	int convert(String s) {
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
				devices.Console.println("wrong command: should be int here:" + s + ".");
				throw new IllegalArgumentException();
			}

			int tmp = c - '0';

			ret *= 10;
			ret += tmp;

		}

		return ret;
	}

	public int getId() {
		return id;
	}

	public int getState() {
		return state;
	}

	public void printMyNeighbors() {
		devices.Console.println("---------------" + time);
		for (int i = 0; i < 4; i++) {
			devices.Console.println(">" + neighbor.getNeighbor(i));
		}
		devices.Console.println("---------------");
	}

}
