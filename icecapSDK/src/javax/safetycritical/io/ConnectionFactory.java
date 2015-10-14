package javax.safetycritical.io;

public abstract class ConnectionFactory {
	
	private String  name;
	
	protected ConnectionFactory(String name) {
		this.name = name;
	}

	public abstract javax.microedition.io.Connection create(String url)
			throws java.io.IOException,
			javax.microedition.io.ConnectionNotFoundException;
	
	public boolean equals(Object other) {
		return other instanceof ConnectionFactory &&
				name.equals(((ConnectionFactory)other).name);
	}
	
	public static javax.safetycritical.io.ConnectionFactory getRegistered(
			String name) {
		return null;
	}
	
	public final String getServiceName( ) {
		return null;
	}
	
	public static void register(ConnectionFactory factory) {
		
	}
	
}



