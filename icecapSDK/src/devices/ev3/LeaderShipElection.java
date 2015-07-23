package devices.ev3;

public class LeaderShipElection {
	
	interface Claim {
		public final static byte LEADER = 0;
		public final static byte FOLLOWER = 1;
		public final static byte UNDECIDED = 2;
	}
	
	public static byte state = Claim.UNDECIDED;
	
	public static String[] local_state = new String[3]; // ip, priority and current state.

}
