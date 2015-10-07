package javax.microedition.io;

public interface OutputConnection extends Connection {
	
	public java.io.DataOutputStream openDataOutputStream( );
	
	public java.io.OutputStream openOutputStream( );

}


