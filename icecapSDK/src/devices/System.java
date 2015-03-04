package devices;

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
		public DevicePrintStream(OutputStream out) {
			super(out);
		}

		public DevicePrintStream() {
			this(new DeviceOutputStream());
		}

		private static class DeviceOutputStream extends OutputStream {

			@Override
			public void write(int b) throws IOException {

			}
		}
	}

	public static void initializeSystemClass() {
		java.lang.System.setOut(new DevicePrintStream());
	}
}
