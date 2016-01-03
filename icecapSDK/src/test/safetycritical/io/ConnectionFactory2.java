package test.safetycritical.io;

import java.io.IOException;

import javax.microedition.io.Connection;
import javax.microedition.io.ConnectionNotFoundException;
import javax.safetycritical.io.ConnectionFactory;

public class ConnectionFactory2 extends ConnectionFactory {

	public ConnectionFactory2(String name) {
		super(name);
	}

	@Override
	public Connection create(String url) throws IOException,
			ConnectionNotFoundException {
		return new StreamConnection2(url);
	}

}
