package util;

import java.io.IOException;

import javax.microedition.io.Connection;
import javax.microedition.io.ConnectionNotFoundException;
import javax.safetycritical.io.ConnectionFactory;

public class CommConnectionFactoryPosix extends ConnectionFactory {

	public CommConnectionFactoryPosix(String name) {
		super(name);
	}

	@Override
	public Connection create(String url) throws IOException, ConnectionNotFoundException {
		try {
			URL uri = new URL(url);
			String port = uri.getTarget();
			
		} catch (URLSyntaxException e) {
		}

		return null;
	}

}
