package com;

public class UDPCommunication extends Network{
	private static int[] ReceiveMsg = new int [IP_ADDRESS_LENGTH + MESSAGE_LENGTH_DEFAULT];
	private static String[] ipAndMsg = new String[2];
	private static boolean isInitized = false;

	/**
	 * create the broadcast sender.
	 * @param ip the broadcast address
	 */
	public static void createSender(String ip){
		if(!isInitized){
			createBroadcastSender(ip.length(), phraseMsg(ip));
			devices.Console.println("connected to: " + ip);
			isInitized = true;
		}
		else{
			devices.Console.println("Broadcast sender has already been initized");
		}
	}
	
	/**
	 * Send a broadcast message
	 * @param msg The message that will be sent
	 */
	public static void sendBroadcastMsg(String msg){
		sendBroadcastMsg(msg.length(), phraseMsg(msg));
	}
	
	/**
	 * Close the broadcast sender socket.
	 */
	public static native void closeBroadcastSender();
	
	/**
	 * Create the broadcast receiver.
	 */
	public static native void createReceiver();
	
	/**
	 * Receive a broadcast message
	 * @return The message that been received.
	 */
	public static String[] receiveMsg(){
		receiveMsg(ReceiveMsg);
		processMsg();
		return ipAndMsg;
	}
	
	/**
	 * Close the broadcast receiver socket
	 */
	public static native void closeReceiver();
	
	/**
	 * Send a point to point message
	 * @param ip The ip address of the remote machine
	 * @param msg The message that will be sent
	 */
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
