package util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connection;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.io.InputConnection;
import javax.microedition.io.OutputConnection;
import javax.safetycritical.io.ConnectionFactory;

public class InMemConnectionFactory extends ConnectionFactory {
	private DataOutputStream outputStream;
	private DataInputStream inputStream;

	private class InMemDataConnection implements OutputConnection, InputConnection {

		@Override
		public void close() {
			try {
				outputStream.close();
				inputStream.close();
			} catch (IOException e) {
			}
		}

		@Override
		public DataOutputStream openDataOutputStream() {
			return outputStream;
		}

		@Override
		public OutputStream openOutputStream() {
			return null;
		}

		@Override
		public DataInputStream openDataInputStream() {
			return inputStream;
		}

		@Override
		public InputStream openInputStream() {
			return null;
		}
	}

	public InMemConnectionFactory(String name, DataOutputStream outputStream, DataInputStream inputStream) {
		super(name);
		this.outputStream = outputStream;
		this.inputStream = inputStream;
	}

	@Override
	public Connection create(String url) throws IOException, ConnectionNotFoundException {
		return new InMemDataConnection();
	}
}
