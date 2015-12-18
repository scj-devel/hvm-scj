package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import javax.microedition.io.Connector;
import javax.safetycritical.io.ConnectionFactory;

import util.InMemConnectionFactory;

public class TestIOConnection {

	public static class SimpleOutputStream extends OutputStream {
		LinkedList<Integer> integers;
		
		SimpleOutputStream()
		{
			integers = new LinkedList<Integer>();
		}
		
		@Override
		public void write(int b) throws IOException {
			integers.add(b);
		}
	}
	
	public static class SimpleInputStream extends InputStream {

		private SimpleOutputStream output;

		SimpleInputStream(SimpleOutputStream output)
		{
			this.output = output;
		}
		
		@Override
		public int read() throws IOException {
			return output.integers.removeFirst();
		}
	}

	public static void main(String[] args) {
		String outputLocation = "inmem:output";
		String inputLocation = "inmem:input";

		SimpleOutputStream simpleOutput = new SimpleOutputStream();
		
		DataOutputStream outputStream = new DataOutputStream(simpleOutput);
		
		DataInputStream inputStream = new DataInputStream(new SimpleInputStream(simpleOutput));

		InMemConnectionFactory inmemConnectionFactory = new InMemConnectionFactory("inmem", outputStream, inputStream);
		
		ConnectionFactory.register(inmemConnectionFactory);
		
		try {
			outputStream = Connector.openDataOutputStream(outputLocation);
			
			inputStream = Connector.openDataInputStream(inputLocation);
		
			outputStream.writeInt(42);
			int res = inputStream.readInt();
			if (res == 42)
			{
				args = null;
			}
		} catch (IOException e) {
			devices.Console.println("Could not open [" + outputLocation + "]");
		}
	}
}
