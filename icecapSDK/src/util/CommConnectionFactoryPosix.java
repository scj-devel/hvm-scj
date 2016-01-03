package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connection;
import javax.microedition.io.InputConnection;
import javax.microedition.io.OutputConnection;

public class CommConnectionFactoryPosix extends HVMConnectionFactory {

	public CommConnectionFactoryPosix(String name) {
		super(name);
	}

	private class SerialConnection extends FullDuplexConnection implements InputConnection, OutputConnection {
		private byte[] port;
		private int baudrate;

		private class SerialInputStream extends InputStream {

			SerialInputStream() {
				if (handle == -1) {
					handle = openSerial(port, baudrate);
				}
			}

			@Override
			public int read() throws IOException {
				if (handle == -1) {
					throw new IOException();
				}
				return readSerial(handle);
			}

			@Override
			public void close() throws IOException {
				super.close();
				if (closeSerial(handle) != 0) {
					throw new IOException();
				}
			}
		}

		@Override
		public void close() {
			super.close();
			connections.remove(url);
		}

		private class SerialOutputStream extends OutputStream {

			@Override
			public void close() throws IOException {
				super.close();
				if (closeSerial(handle) != 0) {
					throw new IOException();
				}
			}

			SerialOutputStream() {
				if (handle == -1) {
					handle = openSerial(port, baudrate);
				}
			}

			@Override
			public void write(int b) throws IOException {
				if (handle == -1) {
					throw new IOException();
				}
				if (writeSerial(handle, b) != 1) {
					throw new IOException();
				}
			}
		}

		SerialConnection(String url, byte[] port, int baudrate) {
			super(url);
			this.port = port;
			this.baudrate = baudrate;
		}

		protected OutputStream createOutputStream() {
			return new SerialOutputStream();
		}

		protected InputStream createInputStream() {
			return new SerialInputStream();
		}
	}

	protected Connection createConnection(String url) throws URLSyntaxException {
		Connection con;
		URL uri = new URL(url);
		byte[] port = uri.getTarget();
		byte[] baudrateString = uri.getParameter("baudrate");
		int baudrate = StringUtil.parseInt(baudrateString, true);
		con = new SerialConnection(url, port, baudrate);
		return con;
	}

	private static native int openSerial(byte[] port, int baudrate);

	private static native int closeSerial(int fd);

	private static native int writeSerial(int fd, int b);

	private static native int readSerial(int fd);
}
