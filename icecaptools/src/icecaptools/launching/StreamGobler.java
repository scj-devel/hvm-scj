package icecaptools.launching;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamGobler extends Thread {
	private InputStream inputStream;

	private OutputStream outputStream;

	private boolean keepRunning;

	public StreamGobler(InputStream inputStream, OutputStream outputStream) {
		this.inputStream = inputStream;
		this.outputStream = outputStream;
		keepRunning = true;
	}

	@Override
	public void run() {
		int nextByte;
		try {
			int count = 0;
			while (((nextByte = inputStream.read()) != -1) && keepRunning) {
				outputStream.write(nextByte);
				if (count++ % 100 == 0)
				{
					outputStream.flush();
				}
			}
			outputStream.flush();
		} catch (IOException e) {
		}
	}
	
	public void stopStreamGobler()
	{
		keepRunning = false;	
	}
}
