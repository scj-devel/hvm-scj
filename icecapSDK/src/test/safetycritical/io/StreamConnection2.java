package test.safetycritical.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.StreamConnection;

public class StreamConnection2 implements StreamConnection {

	String name;
	
	public StreamConnection2(String name) {
		this.name = name;
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		return null;
	}

}
