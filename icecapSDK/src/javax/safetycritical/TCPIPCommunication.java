package javax.safetycritical;

public class TCPIPCommunication {
	
	private static final int DEFAULT_LENGTH = 100;  //256; //128; // HSO: June 2014
	private static int[] info = new int[DEFAULT_LENGTH];
	private static int[] broadcastMsg = new int [121];
	private static int[] pinPointMsg = new int [121];

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
	
	public static String[] receiveMsg(){
		String[] info = new String[2];
		String message = "";
		String ip = "";
		
		receiveMsg(broadcastMsg);
		
		int i=0;
		for(; i<broadcastMsg.length;i++){
			char a = (char) broadcastMsg[i];
			if(a == '\0'){
				break;
			}
			message = message + a + "";
		}
		
		i++;
		for(int j=i; j<broadcastMsg.length;j++){
			char a = (char) broadcastMsg[j];
			//devices.Console.println(a+"");
			if(a == '\0'){
				break;
			}
			ip = ip + a + "";
		}
		
		info[0] = ip;
		info[1] = message;
		
		return info;
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
	
	public static void sendPinpointMessage(String ip, String msg){
		devices.Console.println("send \"" + msg + "\" to " + ip);
		String copy = ip + msg + "";
		sendOneMessage(ip.length(), phraseMsg(copy),msg.length());
	}
	
	private static native void sendOneMessage(int iplen, int[] ip, int msglen);
	
}
