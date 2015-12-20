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

public class CommConnectionFactoryPosix extends ConnectionFactory {

	public CommConnectionFactoryPosix(String name) {
		super(name);
	}

	private static class SerialConnection
	{
		protected int handle;
		
		SerialConnection(int handle)
		{
			this.handle = handle;
		}
		
	}
	private static class SerialInputConnection extends SerialConnection implements InputConnection
	{
		
		public SerialInputConnection(int handle) {
			super(handle);
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public DataInputStream openDataInputStream() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public InputStream openInputStream() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	private static class SerialOutputConnection extends SerialConnection implements OutputConnection
	{

		public SerialOutputConnection(int handle) {
			super(handle);
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public DataOutputStream openDataOutputStream() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OutputStream openOutputStream() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	@Override
	public Connection create(String url, int mode) throws IOException, ConnectionNotFoundException {
		try {
			URL uri = new URL(url);
			String port = uri.getTarget();
			String baudrateString = uri.getParameter("baudrate");
			System.out.println("baudrateString = " + baudrateString);
			int baudrate = StringUtil.parseInt(baudrateString);
			
			if (mode == Connector.READ)
			{
				int handle = openSerialInput(port, baudrate);
				return new SerialInputConnection(handle);
				
			}
			else if (mode == Connector.WRITE)
			{
				int handle = openSerialOutput(port, baudrate);
				return new SerialOutputConnection(handle);
			}
			else
			{
				throw new IOException();
			}
		} catch (URLSyntaxException e) {
			throw new ConnectionNotFoundException();
		}
	}

	private static native int openSerialOutput(String port, int baudrate);

	private static native int openSerialInput(String port, int baudrate);
}
