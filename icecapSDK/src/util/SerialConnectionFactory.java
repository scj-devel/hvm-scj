package util;

import java.io.IOException;

import javax.microedition.io.Connection;
import javax.microedition.io.ConnectionNotFoundException;
import javax.safetycritical.io.ConnectionFactory;

public class SerialConnectionFactory extends ConnectionFactory {

	public SerialConnectionFactory(String name) {
		super(name);
	}

	@Override
	public Connection create(String url) throws IOException, ConnectionNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
