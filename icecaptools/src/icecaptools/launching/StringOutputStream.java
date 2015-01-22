package icecaptools.launching;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

public class StringOutputStream extends OutputStream {
	StringWriter writer;
	
	public StringOutputStream()
	{
		writer = new StringWriter();
	}
	
	public void close() throws IOException {
		writer.close();
	}

	@Override
	public void flush() throws IOException {
		writer.flush();
	}

	@Override
	public void write(byte[] arg0, int arg1, int arg2)
			throws IOException {
		char[] temp = new char[arg0.length];
		for (int count = 0; count < arg0.length; count++) {
			temp[count] = (char) arg0[count];
		}
		writer.write(temp, arg1, arg2);
	}

	@Override
	public void write(byte[] arg0) throws IOException {
		write(arg0, 0, arg0.length);
	}

	public void write(int arg0) throws IOException {
		writer.write(arg0);
	}

	@Override
	public String toString() {
		return writer.toString();
	}
}
