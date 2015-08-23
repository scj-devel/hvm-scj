package leadershipElection;

import com.Network;

public class LeaderShipElection {
	public static int MAX_ROBOTS = 255;

	public interface Claim {
		public final static byte LEADER = 0;
		public final static byte FOLLOWER = 1;
		public final static byte UNDECIDED = 2;
	}

	private byte state = Claim.UNDECIDED;
	private int petition = 0;
	private int id = 0;
	private int time = 0;

	private Robot[] robots;
	private int[] robot_ids;

	public LeaderShipElection(String networkName, int[] ids) {
		this.id = generateID(Network.getIPAddress(networkName));
		petition = id;
		devices.Console.println("ID: " + id);

		robots = new Robot[MAX_ROBOTS];
		robot_ids = ids;

		for (int i = 0; i < robot_ids.length; i++) {
			robots[robot_ids[i]] = new Robot(state, robot_ids[i], robot_ids[i], time);
		}

	}

	public static int generateID(String ip) {
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
		return convert(lastDigit);
	}

	public void electLeader() {
		if (time != 0) {
			printMyNeighbors();
			setState();
		}
		increaseTime();
	}
	
	public void reset(){
		state = Claim.UNDECIDED;
		petition--;
		time = 0;
		for (int i = 0; i < robot_ids.length; i++) {
			robots[robot_ids[i]] = new Robot(state, robot_ids[i], robot_ids[i], time);
		}
		
		
	}

	synchronized public void collect(String msg) {
		int countStar = 0;
		for (int i = msg.length() - 1; i >= 0; i--) {
			if (msg.charAt(i) == '*') {
				countStar++;
			}
		}
		if (msg.charAt(1) != '*' || countStar != 2) {
			devices.Console.println("received: " + msg);
			int star = 0;
			for (int i = msg.length() - 1; i >= 0; i--) {
				if (msg.charAt(i) == '*') {
					star++;
					if (star == 2) {
						star = i;
						break;
					}

				}
			}
			msg = msg.substring(star - 1);
			devices.Console.println("pharsed: " + msg);
		}

		int state = msg.charAt(0) - '0';
		int id = -1;
		int petition = -1;

		int i = 0;
		int first_index = -1, second_index = -1;
		for (; i < msg.length(); i++) {
			if (msg.charAt(i) == '*') {
				first_index = i;
				break;
			}
		}
		i++;
		for (; i < msg.length(); i++) {
			if (msg.charAt(i) == '*') {
				second_index = i;
				break;
			}
		}

		id = convert(msg.substring(first_index + 1, second_index));
		petition = convert(msg.substring(second_index + 1));

		robots[id].state = state;
		robots[id].id = id;
		robots[id].petition = petition;
		robots[id].time = time;
	}

	synchronized Robot getRobot(int index) {
		return robots[index];
	}

	void increaseTime() {
		time++;
	}

	public String StateToNeighbors() {
		String msg = state + "*" + id + "*" + petition;
		return msg;
	}

	class Robot {
		int state;
		int id;
		int petition;
		int time;

		public Robot(int state, int id, int petition, int time) {
			this.state = state;
			this.id = id;
			this.petition = petition;
			this.time = time;
		}

		@Override
		public String toString() {
			return state + "*" + id + "*" + petition + "*" + time;
		}

	}

	private void setState() {
		switch (state) {
		case Claim.UNDECIDED:
			if (numberOfLeaders() == 1) {
				state = Claim.FOLLOWER;
			} else if (numberOfLeaders() == 0 && hasHighestPetition() == 1) {
				state = Claim.LEADER;
			} else if (numberOfLeaders() == 0 && hasHighestPetition() == 0 && hasHighestID()) {
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

	private int hasHighestPetition() {
		int hasHighestPetition = 0;
		int equal = -1;

		for (int i = 0; i < robot_ids.length; i++) {
			if (robot_ids[i] == id || getRobot(robot_ids[i]).time < time) {
				continue;
			}

			if (getRobot(robot_ids[i]).petition > petition) {
				hasHighestPetition = -1;
				break;
			} else if (getRobot(robot_ids[i]).petition == petition) {
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

		for (int i = 0; i < robot_ids.length; i++) {
			if (robot_ids[i] == id || getRobot(robot_ids[i]).time < time) {
				continue;
			}
			if (getRobot(robot_ids[i]).id > id) {
				hasHighestID = false;
				break;
			}
		}

		return hasHighestID;
	}

	int numberOfLeaders() {
		int number_of_leaders = 0;

		for (int i = 0; i < robot_ids.length; i++) {
			if (robot_ids[i] == id || getRobot(robot_ids[i]).time < time) {
				continue;
			}
			int state = getRobot(robot_ids[i]).state;
			if (state == Claim.LEADER)
				number_of_leaders++;
		}

		return number_of_leaders;
	}

	static int convert(String s) {
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

	public int getState() {
		return state;
	}

	public void printMyNeighbors() {
		devices.Console.println("---------------" + time);
		for (int i = 0; i < robot_ids.length; i++) {
			devices.Console.println(">" + getRobot(robot_ids[i]).toString());
		}
		devices.Console.println("---------------");
	}

}
