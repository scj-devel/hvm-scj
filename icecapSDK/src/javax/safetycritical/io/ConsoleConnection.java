package javax.safetycritical.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.StreamConnection;

import devices.System;

public class ConsoleConnection implements StreamConnection {
	
	String name;
	
	public ConsoleConnection(String name)
			throws javax.microedition.io.ConnectionNotFoundException {
		
		this.name = name;
		System.initializeSystemClass();
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
		return new System.DevicePrintStream();
	}

}
