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

public class CommConnectionFactoryPosix extends ConnectionFactory {

	public CommConnectionFactoryPosix(String name) {
		super(name);
	}

	private static class SerialConnection implements InputConnection,
			OutputConnection {
		private byte[] port;
		private int baudrate;

		private class SerialInputStream extends InputStream {
			private int handle;

			SerialInputStream() {
				handle = openSerialInput(port, baudrate);
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

		private class SerialOutputStream extends OutputStream {
			private int handle;

			@Override
			public void close() throws IOException {
				super.close();
				if (closeSerial(handle) != 0) {
					throw new IOException();
				}
			}

			SerialOutputStream() {
				handle = openSerialOutput(port, baudrate);
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

		SerialConnection(byte[] port, int baudrate) {
			this.port = port;
			this.baudrate = baudrate;
		}

		public void close() {
			;
		}

		@Override
		public DataInputStream openDataInputStream() {
			return new DataInputStream(new SerialInputStream());
		}

		@Override
		public InputStream openInputStream() {
			return new SerialInputStream();
		}

		@Override
		public DataOutputStream openDataOutputStream() {
			return new DataOutputStream(new SerialOutputStream());
		}

		@Override
		public OutputStream openOutputStream() {
			return new SerialOutputStream();
		}
	}

	@Override
	public Connection create(String url) throws IOException,
			ConnectionNotFoundException {
		try {
			URL uri = new URL(url);
			byte[] port = uri.getTarget();
			byte[] baudrateString = uri.getParameter("baudrate");
			int baudrate = StringUtil.parseInt(baudrateString, true);
			return new SerialConnection(port, baudrate);
		} catch (URLSyntaxException e) {
			throw new ConnectionNotFoundException();
		}
	}

	private static native int openSerialOutput(byte[] port, int baudrate);

	private static native int openSerialInput(byte[] port, int baudrate);

	private static native int closeSerial(int fd);

	private static native int writeSerial(int fd, int b);

	private static native int readSerial(int fd);
}
