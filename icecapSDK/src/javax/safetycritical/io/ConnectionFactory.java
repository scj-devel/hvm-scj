package javax.safetycritical.io;

import javax.scj.util.Const;

import util.URL;
import util.URLSyntaxException;

public abstract class ConnectionFactory {

	static ConnectionFactory[] connectionFactorySet = new ConnectionFactory[Const.DEFAULT_CONNECTION_FACTORY_NUMBER]; // a set of ConnectionFactory's
	static int count = 0;

	private String name;

	protected ConnectionFactory(String name) {
		try {
			URL uri = new URL(name);
			if (uri.getScheme() != null) {
				this.name = uri.getScheme();
			} else {
				this.name = uri.getSchemeSpecificPart();
			}
		} catch (URLSyntaxException e) {
			this.name = name;
		}
	}

	public abstract javax.microedition.io.Connection create(String url) throws java.io.IOException, javax.microedition.io.ConnectionNotFoundException;

	public boolean equals(Object other) {
		return other instanceof ConnectionFactory && name.equals(((ConnectionFactory) other).name);
	}

	public static javax.safetycritical.io.ConnectionFactory getRegistered(String name) {

		int idx = isRegistered(name);
		if (idx == -1)
			return null;
		else
			return connectionFactorySet[idx];
	}

	public final String getServiceName() {
		return name;
	}

	public static void register(ConnectionFactory factory) {
		int idx = isRegistered(factory.name);

		if (idx == -1) {
			connectionFactorySet[count++] = factory; // register new
		} else {
			connectionFactorySet[idx] = factory; // replace old one
		}
	}

	private static int isRegistered(String connectionFactoryName) {
		// look up in connectionFactorySet to find a ConnectionFactory with the name connectionFactoryName
		for (int i = 0; i < count; i++) {
			if (connectionFactorySet[i].name.equals(connectionFactoryName))
				return i;
		}
		return -1;
	}
}
