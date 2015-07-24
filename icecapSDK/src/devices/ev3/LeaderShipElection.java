package devices.ev3;

import javax.safetycritical.TCPIPCommunication;

public class LeaderShipElection {
	
	interface Claim {
		public final static byte LEADER = 0;
		public final static byte FOLLOWER = 1;
		public final static byte UNDECIDED = 2;
	}
	
	private static byte state = Claim.UNDECIDED;	
	private static double petition = 0;
	private static int id = 0;
	
	private static int NUMBER_OF_ROBOT = 100;	
	private static String[] neighbors = new String[NUMBER_OF_ROBOT];	
	private static int count = 0;
	
	
	public static String sendStateToNeighbors(){
		String msg = "*"+ "ASK" + "*" + state + "*" + id;
		return  msg;
	}
	
	public static String replyStateToNeighbors(){
		String msg = "*"+ "REPLY" + "*" + state + "*" + id;
		return  msg;
	}
	
	public static void collect(String state){
		
		if(state.charAt(0) == '*' && state.charAt(1) == 'R'){
			String neighbor  = state.substring(7);
			
			if(getNeighborId(neighbor) != id){
				neighbors[count] = neighbor;
				count++;
			}
		}
		else if(state.charAt(0) == '*' && state.charAt(1) == 'A'){
			if(getNeighborId(state.substring(5)) != id){
				TCPIPCommunication.sendBroadcastMsg(replyStateToNeighbors());
			}
		}
	}
	
	private static int getNeighborId(String info){
//		devices.Console.println("neighbor: " + info);
//		int id = -1;
//		int first_index = -1, second_index = -1;
//		
//		int i=0;
//		for(;i<info.length();i++){
//			if(info.charAt(i) == '*'){
//				first_index = i;
//				break;
//			}
//		}
//		i++;
//		for(;i<info.length();i++){
//			if(info.charAt(i) == '*'){
//				second_index = i;
//				break;
//			}
//		}
//		devices.Console.println("1: " + first_index + " 2: " + second_index);
//		id = convert(info.substring(first_index+1, second_index));
//		devices.Console.println("neighor id: " + id);
		
		int id = convert(info.substring(2));
//		devices.Console.println("N_id: " + id);
		return id;
	}
	
	public static int numberOfLeaders(){
		int number_of_leaders = 0;
		
		for(int i=0; i<count; i++){
			String state = neighbors[i].charAt(0) + "";
			if (convert(state) == Claim.LEADER)
				number_of_leaders++;
		}
		
		return number_of_leaders;
	}
	
	private static int convert(String s){
	    if(s == null || s.length() == 0){
	    	return -1;
	    }

	    int ret = 0;

	    for(int i=0;i<s.length();i++){
	        char c = s.charAt(i);

	        if( i == 0 && (c == '-')){
	            return -1;
	        }

	        if(c - '0' < 0 || c - '0' > 10){
	        	devices.Console.println("wrong command: should be int here");
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

	public static void setId(int id) {
		LeaderShipElection.id = id;
	}
	
	public static void printMyNeighbors(){
		for(int i=0;i<count;i++){
			devices.Console.println(">>>" + neighbors[i]);
		}
	}
	
}
