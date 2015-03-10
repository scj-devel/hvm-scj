package devices;

import icecaptools.IcecapCompileMe;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class System {
	
	public static void delay(int i) {
		while (i > 0) {
			i--;
		}
	}

	public static native void blink();

	public static native void snapshot();

	public static native void lockROM();

	public static native void resetMemory();

	private static class DevicePrintStream extends PrintStream {
		@Override
		public void println(String x) {
			devices.Console.println(x);
		}

		private static class DummyOutputStream extends OutputStream {

			@Override
			public void write(int b) throws IOException {
				throw new IOException();
			}
		}

		public DevicePrintStream() {
			super(new DummyOutputStream(), true);
		}
	}

	public static void initializeSystemClass() {
		java.lang.System.setOut(new DevicePrintStream());
	}
	
	@IcecapCompileMe
	public static String getProperty(String key)
	{
		if (key.equals("line.separator"))
		{
			return "\n"; 
		}
		else
		{
			return null;
		}
	}
}
