package javax.microedition.io;

import java.io.IOException;

import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.io.ConnectionFactory;
import javax.safetycritical.io.ConsoleConnection;

import util.URL;
import util.URLSyntaxException;

public class Connector {

	@SCJAllowed
	public static final int READ = 1;

	@SCJAllowed
	public static final int WRITE = 2;

	@SCJAllowed
	public static final int READ_WRITE = 3;

	private static final String SerialPort_URI = "HVMSerialPortAnd:PortNumber"; // to be implemented
	private static ConsoleConnection consoleCon; // The default connection

	static {
		try {
			consoleCon = new ConsoleConnection(SerialPort_URI); // The default connection
		} catch (ConnectionNotFoundException e) {
			e.toString();
		}
	}

	// No instantiation of class Connector
	private Connector() {
	}

	public static javax.microedition.io.Connection open(String name) throws java.io.IOException {
		return open(name, READ_WRITE);
	}

	public static javax.microedition.io.Connection open(String name, int mode) throws java.io.IOException {
		/* Test for correct mode value */
		if (mode != READ && mode != WRITE && mode != READ_WRITE) {

			throw new IllegalArgumentException("Wrong mode value");
		}
		/* Test for null argument */
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("URL is null or empty");
		}

		try {
			URL uri = new URL(name);
			ConnectionFactory factory = getFactory(uri.getScheme());
			Connection retval = null;

			if (factory != null) {
				retval = factory.create(name);
			} else {
				// fall back to console connection.
				try {
					retval = consoleCon;
				} catch (Exception e) {
					throw new ConnectionNotFoundException();
				}
			}

			if (retval == null)
				throw new ConnectionNotFoundException();
			else
				return retval;
		} catch (URLSyntaxException e1) {
			throw new IllegalArgumentException(name + " not legal URI");
		}
	}

	public static java.io.DataInputStream openDataInputStream(String name) throws java.io.IOException {
		InputConnection con = null;
		try {
			con = (InputConnection) Connector.open(name, Connector.READ);
		} catch (ClassCastException e) {
			throw new IOException(e.toString());
		}

		return con.openDataInputStream();
	}

	public static java.io.DataOutputStream openDataOutputStream(String name) throws java.io.IOException {

		OutputConnection con = null;
		try {
			con = (OutputConnection) Connector.open(name, Connector.WRITE);
		} catch (ClassCastException e) {
			throw new IOException(e.toString());
		}

		return con.openDataOutputStream();
	}

	public static java.io.InputStream openInputStream(String name) throws java.io.IOException {

		return openDataInputStream(name);
	}

	public static java.io.OutputStream openOutputStream(String name) throws java.io.IOException {

		return openDataOutputStream(name);
	}

	private static ConnectionFactory getFactory(String scheme) {
		return ConnectionFactory.getRegistered(scheme);
	}
}
