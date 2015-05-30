package test.safetycritical.io;


import javax.microedition.io.Connection;
import javax.microedition.io.Connector;
import javax.safetycritical.io.ConnectionFactory;

public class ConnectorExample1 {

	public static void main(String[] args) {
		
		String name = "abc";
		int port    = 123;
		
		String url = "stream://" + name + ":" + port;
		
		StreamConnection1 sc1;
		
		try {	
		
			ConnectionFactory cf1 = new ConnectionFactory1(url);
			ConnectionFactory.register(cf1);
			
			@SuppressWarnings("unused")
			Connection con = cf1.create(url);
			
			// ...
			
			sc1 = (StreamConnection1)Connector.open(url);
			
			// ...
			
			sc1.close();
		
		}
		catch(Exception e) {
			System.out.println("...");
		}
		
	}
	
	
}
