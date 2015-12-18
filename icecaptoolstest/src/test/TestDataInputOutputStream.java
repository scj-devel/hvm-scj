package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

public class TestDataInputOutputStream {

	public static class SimpleOutputStream extends OutputStream {
		LinkedList<Integer> integers;

		SimpleOutputStream() {
			integers = new LinkedList<Integer>();
		}

		@Override
		public void write(int b) throws IOException {
			integers.add(b);
		}
	}

	public static class SimpleInputStream extends InputStream {

		private SimpleOutputStream output;

		SimpleInputStream(SimpleOutputStream output) {
			this.output = output;
		}

		@Override
		public int read() throws IOException {
			return output.integers.removeFirst();
		}
	}

	public static void main(String[] args) {
		SimpleOutputStream simpleOutput = new SimpleOutputStream();
		SimpleInputStream simpleInput = new SimpleInputStream(simpleOutput);

		try {
			simpleOutput.write(42);
			int x = simpleInput.read();
			if (x == 42) {

				DataOutputStream outputStream = new DataOutputStream(simpleOutput);
				DataInputStream inputStream = new DataInputStream(new SimpleInputStream(simpleOutput));

				outputStream.writeInt(42);
				int res = inputStream.readInt();
				if (res == 42) {
					inputStream.close();
					outputStream.close();
					simpleInput.close();
					simpleOutput.close();
					args = null;
				}
			}
		} catch (IOException e) {
		}
	}
}
