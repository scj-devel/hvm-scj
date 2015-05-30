package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connection;
import javax.microedition.io.InputConnection;
import javax.microedition.io.OutputConnection;

public class TCPConnectionFactoryPosix extends HVMConnectionFactory {

	public TCPConnectionFactoryPosix(String name) {
		super(name);
	}

	private class TCPConnection extends FullDuplexConnection implements InputConnection, OutputConnection {
		private byte[] host;
		private int port;
		private boolean isServer;

		private class TCPInputStream extends InputStream {

			TCPInputStream() {
				if (handle == -1) {
					handle = openTCP(host, port, isServer);
				}
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

		@Override
		public void close() {
			super.close();
			connections.remove(url);
		}

		private class TCPOutputStream extends OutputStream {

			@Override
			public void close() throws IOException {
				super.close();
				if (closeTCP(handle) != 0) {
					throw new IOException();
				}
			}

			TCPOutputStream() {
				if (handle == -1) {
					handle = openTCP(host, port, isServer);
				}
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

		public TCPConnection(String url, byte[] host, int port, boolean isServer) {
			super(url);
			this.host = host;
			this.port = port;
			this.isServer = isServer;
		}

		protected OutputStream createOutputStream() {
			return new TCPOutputStream();
		}

		protected InputStream createInputStream() {
			return new TCPInputStream();
		}
	}

	@Override
	protected Connection createConnection(String url) throws URLSyntaxException {
		Connection con = null;
		URL uri = new URL(url);
		byte[] host = uri.getTarget();
		byte[] portString = uri.getParameter("port");
		if (portString != null) {
			int port = StringUtil.parseInt(portString, true);
			byte[] isServerString = uri.getParameter("server");
			boolean isServer = false;
			if (isServerString != null) {
				isServer = StringUtil.parseInt(isServerString, true) == 1;
			}
			con = new TCPConnection(url, host, port, isServer);
			return con;
		} else {
			throw new URLSyntaxException();
		}
	}

	private static native int openTCP(byte[] host, int port, boolean isServer);

	private static native int readTCP(int handle);

	private static native int closeTCP(int handle);

	private static native int writeTCP(int handle, int b);
}
