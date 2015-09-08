package com;

public class Network {

	static final int IP_ADDRESS_LENGTH = 21;
	static final int MESSAGE_LENGTH_DEFAULT = 100;  //256; //128; // HSO: June 2014
	
	/**
	 * get the IP address of the local machine by the given network name
	 * @param networkName the name of the currnet network
	 * @return The ip address.
	 */
	public static String getIPAddress(String networkName){
		String ip = "";
		int[] ip_address = new int[IP_ADDRESS_LENGTH];
	
		getIPAddress(ip_address, phraseMsg(networkName), networkName.length());
		
		for(int i=0; i<IP_ADDRESS_LENGTH;i++){
			char a = (char) ip_address[i];
			if(a == '\0'){
				break;
			}
			ip = ip + a;
		}
		
		//devices.Console.println("	ip: " + ip);
		
		return ip;
	}
	
	private static native int getIPAddress(int[] ip, int[] name, int namelen);
	
	static int[] phraseMsg(String msg) {
		int[] info = new int[msg.length()];
		int index = 0;
	
		while ((index < msg.length()) && (index < MESSAGE_LENGTH_DEFAULT)) {
			info[index] = (byte) msg.charAt(index);
			index++;
		}
		return info;
	}
}
