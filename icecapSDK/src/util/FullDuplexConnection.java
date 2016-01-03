package util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

public abstract class FullDuplexConnection {

	protected String url;
	protected int handle;
	protected ObjectList streams;

	public FullDuplexConnection(String url) {
		this.url = url;
		this.handle = -1;
		this.streams = new ObjectList();
	}
	
	public void close() {
		Iterator<Object> streamsIterator = streams.iterator();
		while (streamsIterator.hasNext()) {
			Object nextStream = streamsIterator.next();
			try {
				if (nextStream instanceof DataInputStream) {
					DataInputStream dis = (DataInputStream) nextStream;
					dis.close();
				} else if (nextStream instanceof InputStream) {
					InputStream is = (InputStream) nextStream;
					is.close();
				} else if (nextStream instanceof DataOutputStream) {
					DataOutputStream dos = (DataOutputStream) nextStream;
					dos.close();
				} else if (nextStream instanceof OutputStream) {
					OutputStream os = (OutputStream) nextStream;
					os.close();
				} 
			} catch (IOException ex) {

			}
		}
		handle = -1;
	}

	public DataInputStream openDataInputStream() {
		DataInputStream dis = new DataInputStream(createInputStream());
		streams.add(dis);
		return dis;
	}

	public InputStream openInputStream() {
		InputStream is = createInputStream();
		streams.add(is);
		return is;
	}

	public DataOutputStream openDataOutputStream() {
		DataOutputStream dos = new DataOutputStream(createOutputStream());
		streams.add(dos);
		return dos;
	}
	
	public OutputStream openOutputStream() {
		OutputStream os = createOutputStream();
		streams.add(os);
		return os;
	}
	
	protected abstract InputStream createInputStream();
	
	protected abstract OutputStream createOutputStream();
}
