package com;

public class TCPIPCommunication extends Network {
	private static int[] peerInfo = new int[2];

	/**
	 * create a TCP sender socket and return the socket fd.
	 * @return the socket fd.
	 */
	public static native int createTCPIPSender();
	
	/**
	 * Try to connect to a remote machine and return the connection status . 
	 * @param fd The fd of the sender socket.
	 * @param ip The ip address of the remote machine.
	 *
	 * @return 0 indicates a success connection.
	 */
	public static int connectSender(int fd, String ip){
		return connect(ip.length(), phraseMsg(ip), fd);
	}

	/**
	 * Try to send a message to the connected remote machine and return the send status.
	 * @param fd the fd of the sender socket.
	 * @param msg The message that will be sent.
	 * @return the length that been sent.
	 */
	public static int sendMsg(int fd, String msg){
		return send(msg.length(), phraseMsg(msg), fd);
	}

	/**
	 * Close the sender socket
	 * @param fd The fd of the sender socket
	 */
	public native static void closeSender(int fd);
	
	private native static int connect(int iplen, int[] ip, int fd);
	
	private native static int send(int iplen, int[] ip, int fd);
	
	/**
	 * create a receiver socket.
	 * @param timeout to be implemented 
	 * @return The fd of the receiver socket
	 */
	public static native int createTCPIPReceiver(int timeout);
	
	/**
	 * listen for connections and accept the connection from a remote machine
	 * @param fd the receiver socket fd.
	 * @param queueLen The length of the waiting queue.
	 * @return 0 indicates a success connection.
	 */
	public static int[] listenForConnection(int fd, int queueLen){
		listenForConnection(fd, queueLen, peerInfo);
		return peerInfo;
	}
	
	private static native void listenForConnection(int fd, int queueLen, int[] peerInfo);
	
	/**
	 * Receive a message from a remote machine.
	 * @param fd the fd of the receiver socket.
	 * @return the message that been received. 
	 */
	public static String receiveMsg(int fd){
		int[] ReceiveMsg = new int [MESSAGE_LENGTH_DEFAULT];
		receiveMsg(ReceiveMsg, fd);
		String msg = processMsg(ReceiveMsg);
		return msg;
	}
	
	private static native int receiveMsg(int[] msg, int fd);
	
	/**
	 * close a receiver socket
	 * @param fd the fd of the receiver socket
	 */
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
}
