package javax.safetycritical;

public class EV3Support {
	
	private static final int DEFAULT_LENGTH = 512;  //256; //128; // HSO: June 2014
	private static int[] info = new int[DEFAULT_LENGTH];
	
	private static int[] phraseMsg(String string) {
		int index = 0;
		int length = string.length();
	
		while ((index < length) && (index < DEFAULT_LENGTH)) {
			info[index] = (byte) string.charAt(index);
			index++;
		}
		return info;
	}

	public static void createUDPBroadcastSocket(String ip){
		createBroadcastSocket(ip.length(), phraseMsg(ip));
	}
	
	private static native void createBroadcastSocket(int len, int[] ip);
	
	
	public static void sendUDPBroadcastMsg(String msg){
		sendBroadcastMsg(msg.length(), phraseMsg(msg));
	}
	
	private static native void sendBroadcastMsg(int len, int[] msg); 
	
	public static native void closeUDPBroadcastSocket();
	
	

	public static native void initReceiverSocket();
	
	public static native void receiveMsg();
	
	public static native void closeReceiverSocket();
	
	
	public static native int createPinPointSocket(int[] ip);
	
	public static native void sendOneMsg(int fd, String ip, String info);
	
	
	
	
}
