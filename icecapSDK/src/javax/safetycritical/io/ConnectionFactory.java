package javax.safetycritical.io;

public abstract class ConnectionFactory {
	
	static ConnectionFactory[] connectionFactorySet = new ConnectionFactory[10];  // a set of ConnectionFactory's
	static int count = 0;
	
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
	
	public static javax.safetycritical.io.ConnectionFactory getRegistered(String name) {
		if (! isRegistered(name))
		  return null;
		else
		  return getConnectionFactory(name);
	}
	
	public final String getServiceName( ) {
		return name;
	}
	
	public static void register(ConnectionFactory factory) {
		
		connectionFactorySet[count++] = factory;
		
	}
	
	private static boolean isRegistered(String connectionFactoryName) {
		// look up in connectionFactorySet to find a ConnectionFactory with the name connectionFactoryName
		return false;
	}
	
	private static ConnectionFactory getConnectionFactory(String connectionFactoryName) {
		// returns the ConnectionFactory in connectionFactorySet with the name connectionFactoryName
		return null;
	}
	
	
}



