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

	private class DataOutputConnection implements OutputConnection
	{

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

	private class DataInputConnection implements InputConnection
	{

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
	public Connection create(String url) throws IOException, ConnectionNotFoundException {
		try {
			URL uri = new URL(url);
			if ("output".equals(uri.getSchemeSpecificPart()))
			{
				return new DataOutputConnection();
			}
			else if ("input".equals(uri.getSchemeSpecificPart()))
			{
				return new DataInputConnection();
			}
		} catch (URLSyntaxException e) {
			throw new ConnectionNotFoundException();
		}
		
		return null;
	}

}
