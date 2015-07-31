package javax.safetycritical;

public class UDPCommunication {
	
	private static final int DEFAULT_LENGTH = 100;  //256; //128; // HSO: June 2014
	private static int[] info = new int[DEFAULT_LENGTH];
	private static int[] broadcastMsg = new int [121];
	private static int[] pinPointMsg = new int [121];
	private static int[] ip_address = new int[21];
	private static int[] network_name = new int[20];
	private static String[] msgReceived = new String[2];

	public static void createSender(String ip){
		createBroadcastSender(ip.length(), phraseMsg(ip));
		devices.Console.println("connected to: " + ip);
	}
	
	private static native void createBroadcastSender(int len, int[] ip);
	
	
	public static void sendBroadcastMsg(String msg){
		sendBroadcastMsg(msg.length(), phraseMsg(msg));
	}
	
	private static native void sendBroadcastMsg(int len, int[] msg); 
	
	public static native void closeBroadcastSender();
	
	
	public static native void createReceiver();
	
	public static String[] receiveMsg(ManagedSchedulable ms){
		ManagedMemory memory = null;
		long free = 0;
		
		if(ms != null){
			if(ms instanceof ManagedEventHandler){
				memory = ((ManagedEventHandler)ms).privateMemory;
			}
			else{
				memory = ((ManagedThread)ms).privateMemory;
			}
		}
		
		receiveMsg(broadcastMsg);
		
		if(memory != null)
			free = memory.memoryConsumed();
		
		processMsg();
		
		if(memory != null)
			memory.resetArea(free);
		
		return msgReceived;
	}
	
	public static String getIPAddress(ManagedSchedulable ms, String networkName){
		ManagedMemory memory = null;
		long free = 0;
		String ip = "";
	
		if(ms != null){
			if(ms instanceof ManagedEventHandler){
				memory = ((ManagedEventHandler)ms).privateMemory;
			}
			else{
				memory = ((ManagedThread)ms).privateMemory;
			}
		}
		if(memory != null)
			free = memory.memoryConsumed();
	
		getIPAddress(ip_address, phraseMsg(networkName), networkName.length());
		
		for(int i=0; i<21;i++){
			char a = (char) ip_address[i];
			if(a == '\0'){
				break;
			}
			ip = ip + a;
		}
		
		devices.Console.println("	ip: " + ip);
		
		if(memory != null)
			memory.resetArea(free);
		
		return ip;
	}
	
	private static void processMsg(){
		String message = "";
		String ip = "";
		
		int i=0;
		for(; i<broadcastMsg.length;i++){
			char a = (char) broadcastMsg[i];
			if(a == '\0'){
				break;
			}
			message = message + a;
		}
		
		
		i++;
		for(int j=i; j<broadcastMsg.length;j++){
			char a = (char) broadcastMsg[j];
			if(a == '\0'){
				break;
			}
			ip = ip + a;
		}
		
		msgReceived[0] = ip;
		msgReceived[1] = message;
		
	}
	
	private static native void receiveMsg(int[] msg);
	
	public static native void closeReceiver();
	
	private static int[] phraseMsg(String string) {
		int index = 0;
		int length = string.length();
	
		while ((index < length) && (index < DEFAULT_LENGTH)) {
			info[index] = (byte) string.charAt(index);
			index++;
		}
		return info;
	}
	
	public static void sendPinPointMessage(String ip, String msg){
//		devices.Console.println("send \"" + msg + "\" to " + ip);
		String copy = ip + msg + "";
		sendOneMessage(ip.length(), phraseMsg(copy),msg.length());
	}
	
	private static native void sendOneMessage(int iplen, int[] ip, int msglen);

	private static native int getIPAddress(int[] ip, int[] name, int namelen);
}
