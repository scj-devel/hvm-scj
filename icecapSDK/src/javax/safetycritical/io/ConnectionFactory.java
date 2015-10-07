package javax.safetycritical.io;

public abstract class ConnectionFactory {
	
	protected ConnectionFactory(String name) {
		
	}

	public abstract javax.microedition.io.Connection create(String url)
			throws java.io.IOException,
			javax.microedition.io.ConnectionNotFoundException;
	
	public boolean equals(Object other) {
		return false;
	}
	
	public static javax.safetycritical.io.ConnectionFactory getRegistered(
			String name) {
		return null;
	}
	
	public final java.lang.String getServiceName( ) {
		return null;
	}
	
	public static void register(ConnectionFactory factory) {
		
	}
	
}



