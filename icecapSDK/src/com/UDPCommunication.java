package com;

public class UDPCommunication extends Network{
	private static int[] ReceiveMsg = new int [IP_ADDRESS_LENGTH + MESSAGE_LENGTH_DEFAULT];
	private static String[] ipAndMsg = new String[2];

	public static void createSender(String ip){
		createBroadcastSender(ip.length(), phraseMsg(ip));
		devices.Console.println("connected to: " + ip);
	}
	
	public static void sendBroadcastMsg(String msg){
		sendBroadcastMsg(msg.length(), phraseMsg(msg));
	}
	
	public static native void closeBroadcastSender();
	
	public static native void createReceiver();
	
	public static String[] receiveMsg(){
		receiveMsg(ReceiveMsg);
		processMsg();
		return ipAndMsg;
	}
	
	public static native void closeReceiver();
	
	public static void sendPinPointMessage(String ip, String msg){
		String copy = ip + msg + "";
		sendOneMessage(ip.length(), phraseMsg(copy),msg.length());
	}
	
	private static native void createBroadcastSender(int len, int[] ip);
	
	private static native void sendBroadcastMsg(int len, int[] msg); 
	
	private static void processMsg(){
		String message = "";
		String ip = "";
		
		int i=0;
		for(; i<ReceiveMsg.length;i++){
			char a = (char) ReceiveMsg[i];
			if(a == '\0'){
				break;
			}
			message = message + a;
		}
		
		
		i++;
		for(int j=i; j<ReceiveMsg.length;j++){
			char a = (char) ReceiveMsg[j];
			if(a == '\0'){
				break;
			}
			ip = ip + a;
		}
		
		ipAndMsg[0] = ip;
		ipAndMsg[1] = message;
		
	}
	
	private static native void receiveMsg(int[] msg);
	
	private static native void sendOneMessage(int iplen, int[] ip, int msglen);

	
}
