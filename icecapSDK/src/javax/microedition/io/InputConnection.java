package javax.microedition.io;

public interface InputConnection extends Connection {
	
	public java.io.DataInputStream openDataInputStream( );
	
	public java.io.InputStream openInputStream( );

}



