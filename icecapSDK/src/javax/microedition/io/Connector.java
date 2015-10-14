package javax.microedition.io;

import java.io.IOException;

import javax.safetycritical.annotate.SCJAllowed;

public class Connector {

	@SCJAllowed
	public static final int READ = 1;
	
	@SCJAllowed
	public static final int WRITE = 2;
	
	@SCJAllowed
	public static final int READ_WRITE = 3;
	
	// No instantiation of class Connector
	private Connector() {
	}
	
	public static javax.microedition.io.Connection open(String name)
			throws java.io.IOException {
		return open(name, READ_WRITE);
	}
	
	public static javax.microedition.io.Connection open(String name, int mode)
			throws java.io.IOException {
		return null;
	}
	
	public static java.io.DataInputStream openDataInputStream(String name)
			throws java.io.IOException {
		InputConnection con = null;
        try {
            con = (InputConnection)Connector.open(name, Connector.READ);
        } catch (ClassCastException e) {
            throw new IOException(e.toString());
        }

        try {
            return con.openDataInputStream();
        } finally {
            con.close();
        }
	}
	
	public static java.io.DataOutputStream openDataOutputStream(String name)
			throws java.io.IOException {
		
		OutputConnection con = null;
        try {
            con = (OutputConnection)Connector.open(name, Connector.WRITE);
        } catch (ClassCastException e) {
            throw new IOException(e.toString());
        }

        try {
            return con.openDataOutputStream();
        } finally {
            con.close();
        }
	}
	
	public static java.io.InputStream openInputStream(String name)
			throws java.io.IOException {
		
		return openDataInputStream(name);
	}
	
	public static java.io.OutputStream openOutputStream(String name)
			throws java.io.IOException {
		
		return openDataOutputStream(name);
	}
}



