package util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connection;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.io.Connector;
import javax.microedition.io.InputConnection;
import javax.microedition.io.OutputConnection;
import javax.safetycritical.io.ConnectionFactory;

public class InMemConnectionFactory extends ConnectionFactory {
	private DataOutputStream outputStream;
	private DataInputStream inputStream;

	private class DataOutputConnection implements OutputConnection {

		@Override
		public void close() {
			try {
				outputStream.close();
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
	}

	private class DataInputConnection implements InputConnection {

		@Override
		public void close() {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
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
	public Connection create(String url, int mode) throws IOException, ConnectionNotFoundException {
		if (mode == Connector.WRITE) {
			return new DataOutputConnection();
		} else if (mode == Connector.READ) {
			return new DataInputConnection();
		}
		return null;
	}

}
