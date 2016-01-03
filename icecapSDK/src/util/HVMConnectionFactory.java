package util;

import java.io.IOException;

import javax.microedition.io.Connection;
import javax.microedition.io.ConnectionNotFoundException;
import javax.safetycritical.io.ConnectionFactory;

public abstract class HVMConnectionFactory extends ConnectionFactory {

	protected KeyValueMap connections;
	
	protected HVMConnectionFactory(String name) {
		super(name);
		connections = new KeyValueMap();
	}
	
	@Override
	public Connection create(String url) throws IOException, ConnectionNotFoundException {
		Connection con = (Connection) connections.get(url);
		if (con == null) {
			try {
				con = createConnection(url);
				connections.put(url, con);
				return con;
			} catch (URLSyntaxException e) {
				throw new ConnectionNotFoundException();
			}
		} else {
			return con;
		}
	}

	protected abstract Connection createConnection(String url) throws URLSyntaxException;
}
