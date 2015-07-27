package javax.safetycritical;

public class LeaderShipElection {

	public interface Claim {
		public final static byte LEADER = 0;
		public final static byte FOLLOWER = 1;
		public final static byte UNDECIDED = 2;
	}

	private static byte state = Claim.UNDECIDED;
	private static int petition = 0;
	private static int id = 0;

	private static int NUMBER_OF_ROBOT = 100;
	private static String[] neighbors = new String[NUMBER_OF_ROBOT];
	private static int count = -1;

	public static void electLeader() {
		if (count != -1) {
			printMyNeighbors();
			setState();
		}
		resetBuffer();

		TCPIPCommunication.sendBroadcastMsg(LeaderShipElection.sendStateToNeighbors());
		devices.Console.println("ask");

	}
	
	public static void collect(ManagedSchedulable ms, String ip, String state) {

		ManagedMemory memory = null;

		if (ms != null) {
			if (ms instanceof ManagedEventHandler) {
				memory = ((ManagedEventHandler) ms).privateMemory;
			} else {
				memory = ((ManagedThread) ms).privateMemory;
			}
		}

		if (memory != null && count == 0)
			memory.resetArea(0);

		if (state.charAt(0) == '*' && state.charAt(1) == 'R') {
			String temp = state.substring(7);
			if (getNeighborId(temp) != id) {

				neighbors[count] = temp;
				increaseCount();
				// devices.Console.println("got one message");
			}
		} else if (state.charAt(0) == '*' && state.charAt(1) == 'A') {
			if (getNeighborId(state.substring(5)) != id) {
				TCPIPCommunication.sendPinpointMessage(ip, replyStateToNeighbors());
				devices.Console.println("----reply:  " + replyStateToNeighbors() + " to: " + ip);
			}
		}

	}

	private static String sendStateToNeighbors() {
		String msg = "*" + "ASK" + "*" + state + "*" + id + "*" + petition;
		return msg;
	}

	private static String replyStateToNeighbors() {
		String msg = "*" + "REPLY" + "*" + state + "*" + id + "*" + petition;
		return msg;
	}

	private static void setState() {
		// devices.Console.println("State: " + state + " leaders: " +
		// numberOfLeaders() /*+ " high: " + hasHighestPetition()*/);
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
			devices.Console.println("State is undecide: " + count);
			break;
		case Claim.FOLLOWER:
			devices.Console.println("State is follower: " + count);
			break;
		case Claim.LEADER:
			devices.Console.println("State is leader: " + count);
			break;
		}

	}

	synchronized private static void resetBuffer() {
		count = 0;
	}

	synchronized private static void increaseCount() {
		count++;
	}

	private static int hasHighestPetition() {
		int hasHighestPetition = 0;
		int equal = -1;

		for (int i = 0; i < count; i++) {
			if (getNeighborPetition(neighbors[i]) > petition) {
				hasHighestPetition = -1;
				break;
			} else if (getNeighborPetition(neighbors[i]) == petition) {
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

	private static boolean hasHighestID() {
		boolean hasHighestID = true;

		for (int i = 0; i < count; i++) {
			if (getNeighborId(neighbors[i]) > petition) {
				hasHighestID = false;
				break;
			}
		}

		return hasHighestID;
	}

	private static int getNeighborId(String info) {
		// devices.Console.println("info: " + info);
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
		// devices.Console.println("id:" + info.substring(first_index + 1,
		// second_index) + ".");
		id = convert(info.substring(first_index + 1, second_index));
		// devices.Console.println("neighor id: " + id + ";");

		// int id = convert(info.substring(2));
		// devices.Console.println("N_id: " + id);
		return id;
	}

	private static int getNeighborPetition(String info) {
		int petition = 0;

		int i = 0;
		for (; i < info.length(); i++) {
			if (info.charAt(i) == '*') {
				petition++;
				if (petition == 2) {
					break;
				}
			}
		}
		i++;
		// devices.Console.println("petition: " + info.substring(i));
		petition = convert(info.substring(i));
		// devices.Console.println("petition: " + petition);
		return petition;
	}

	private static int numberOfLeaders() {
		int number_of_leaders = 0;

		for (int i = 0; i < count; i++) {
			String state = neighbors[i].charAt(0) + "";
			if (convert(state) == Claim.LEADER)
				number_of_leaders++;
		}

		return number_of_leaders;
	}

	private static int convert(String s) {
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

	public static int getId() {
		return id;
	}

	public static int getState() {
		return state;
	}

	public static void generateIDAndPetition(String networkName) {
		String ip = TCPIPCommunication.getIPAddress(null, networkName);

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

		LeaderShipElection.id = convert(lastDigit);
		devices.Console.println("ID: " + id);
		LeaderShipElection.petition = LeaderShipElection.id;
	}

	public static void printMyNeighbors() {
		devices.Console.println("--------------------------" + count);
		for (int i = 0; i < count; i++) {
			devices.Console.println(">>>" + neighbors[i]);
		}
	}

}
