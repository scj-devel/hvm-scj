package com;

public class TCPIPCommunication extends Network {
	private static int[] peerInfo = new int[2];
	
	public static native int createTCPIPSender();
	
	public static int connectSender(int fd, String ip){
		return connect(ip.length(), phraseMsg(ip), fd);
	}
	
	public static int sendMsg(int fd, String msg){
		return send(msg.length(), phraseMsg(msg), fd);
	}
	
	
	public native static void closeSender(int fd);
	
	private native static int connect(int iplen, int[] ip, int fd);
	
	private native static int send(int iplen, int[] ip, int fd);
	
	
	
	public static native int createTCPIPReceiver(int timeout);
	
	public static int[] listenForConnection(int fd, int queueLen){
		listenForConnection(fd, queueLen, peerInfo);
		return peerInfo;
	}
	
	private static native void listenForConnection(int fd, int queueLen, int[] peerInfo);
	
	public static String receiveMsg(int fd){
		int[] ReceiveMsg = new int [MESSAGE_LENGTH_DEFAULT];
		receiveMsg(ReceiveMsg, fd);
		String msg = processMsg(ReceiveMsg);
		return msg;
	}
	
	private static native int receiveMsg(int[] msg, int fd);
	
	public static native void closeReceiver(int fd);
	
	private static String processMsg(int[] ReceiveMsg){
		String message = "";
		
		int i=0;
		for(; i<ReceiveMsg.length;i++){
			char a = (char) ReceiveMsg[i];
			if(a == '\0'){
				break;
			}
			message = message + a;
		}

		return message;
	}
	
	public static native void shutdown(int fd);
}
