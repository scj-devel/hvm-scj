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

public class TCPConnectionFactoryPosix extends ConnectionFactory {

	public TCPConnectionFactoryPosix(String name) {
		super(name);
	}

	private static class TCPConnection implements InputConnection, OutputConnection {

		private byte[] host;
		private int port;
		private boolean isServer;

		private class TCPInputStream extends InputStream {
			private int handle;

			TCPInputStream() {
				handle = openTCP(host, port, isServer);
			}

			@Override
			public int read() throws IOException {
				if (handle == -1) {
					throw new IOException();
				}
				return readTCP(handle);
			}
			
			@Override
			public void close() throws IOException {
				super.close();
				if (closeTCP(handle) != 0) {
					throw new IOException();
				}
			}
		}

		private class TCPOutputStream extends OutputStream
		{
			private int handle;

			@Override
			public void close() throws IOException {
				super.close();
				if (closeTCP(handle) != 0) {
					throw new IOException();
				}
			}

			TCPOutputStream() {
				handle = openTCP(host, port, isServer);
			}

			@Override
			public void write(int b) throws IOException {
				if (handle == -1) {
					throw new IOException();
				}
				if (writeTCP(handle, b) != 1) {
					throw new IOException();
				}
			}
		}
		
		public TCPConnection(byte[] host, int port, boolean isServer) {
			this.host = host;
			this.port = port;
			this.isServer = isServer;
		}

		@Override
		public DataOutputStream openDataOutputStream() {
			return new DataOutputStream(new TCPOutputStream());
		}

		@Override
		public OutputStream openOutputStream() {
			return new TCPOutputStream();
		}

		@Override
		public DataInputStream openDataInputStream() {
			return new DataInputStream(new TCPInputStream());
		}

		@Override
		public InputStream openInputStream() {
			return new TCPInputStream();
		}

		@Override
		public void close() {
			;
		}
	}

	@Override
	public Connection create(String url) throws IOException, ConnectionNotFoundException {
		URL uri = new URL(url);
		try {
			byte[] host = uri.getTarget();
			byte[] portString = uri.getParameter("port");
			if (portString != null) {
				int port = StringUtil.parseInt(portString, true);
				byte[] isServerString = uri.getParameter("server");
				boolean isServer = false;
				if (isServerString != null)
				{
					 isServer = StringUtil.parseInt(isServerString, true) == 1;
				}
				return new TCPConnection(host, port, isServer);
			}
		} catch (URLSyntaxException e) {
			throw new IOException();
		}
		throw new ConnectionNotFoundException();
	}

	private static native int openTCP(byte[] host, int port, boolean isServer);

	private static native int readTCP(int handle);
	
	private static native int closeTCP(int handle);
	
	private static native int writeTCP(int handle, int b);
}
