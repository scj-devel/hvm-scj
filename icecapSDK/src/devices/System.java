package devices;

import icecaptools.IcecapCompileMe;
import icecaptools.IcecapVolatile;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.realtime.AbsoluteTime;

import vm.Address;
import vm.Address32Bit;
import vm.HardwareObject;
import vm.Machine;
import vm.POSIX64BitMachineFactory;
import vm.RealtimeClock;

public class System {

	static {
		if (Machine.getMachineFactory() == null) {
			Machine.setMachineFactory(new POSIX64BitMachineFactory());
		}
	}

	@IcecapVolatile("i")
	public static void delay(int i) {
		while (i > 0) {
			i--;
		}
	}

	public static native void blink();

	public static native void snapshot();

	public static native void lockROM();

	public static native void resetMemory();

	/* private */ public static class DevicePrintStream extends PrintStream {
		@Override
		public void println(String msg) {
			devices.Console.println(msg);
		}

		@Override
		public void print(String msg) {
			devices.Console.print(msg);
		}

		@Override
		public void print(int i) {
			devices.Console.print(i);
		}

		@Override
		public void println() {
			devices.Console.println("");
		}

		@Override
		public void print(boolean b) {
			if (b)
				devices.Console.print("true");
			else
				devices.Console.print("false");
		}

		@Override
		public void print(char c) {
			devices.Console.print(c + "");
		}

		@Override
		public void print(long l) {
			devices.Console.print(l);
		}

		@Override
		public void print(float f) {
			devices.Console.println("print float f unimplemented");
		}

		@Override
		public void print(double d) {
			devices.Console.println("print double d unimplemented");
		}

		@Override
		public void print(char[] s) {
			for (int i = 0; i < s.length; i++)
				print(s[i]);
		}

		@Override
		public void print(Object obj) {
			if (obj == null)
				devices.Console.print("null");
			else
				devices.Console.print(obj.toString());
		}

		@Override
		public void write(int b) {
			devices.Console.print(b);
		}

		@Override
		public void write(byte[] buf, int off, int len) {
			for (int i = off; i < off + len; i++)
				devices.Console.print(buf[i]);
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

	private static class DummyHWObject extends HardwareObject {
		@SuppressWarnings("unused")
		public int dummyField;

		public DummyHWObject(Address address) {
			super(address);
		}
	}

	public static void includeHWObjectSupport() {
		DummyHWObject dummy = new DummyHWObject(new Address32Bit(0));
		dummy.dummyField = 42;
	}

	static AbsoluteTime now;

	@IcecapCompileMe
	public static long currentTimeMillis() {
		RealtimeClock clock = vm.RealtimeClock.getRealtimeClock();
		if (now == null) {
			now = new AbsoluteTime();
		}
		clock.getCurrentTime(now);
		return now.getMilliseconds();
	}

	@IcecapCompileMe
	public static String getProperty(String key) {
		if (key.equals("line.separator")) {
			return "\n";
		} else if (key.equals("org.jmlspecs.openjml.racexceptions")) {
			return "true";
		} else if (key.equals("org.jmlspecs.openjml.racjavaassert")) {
			return "true";
		} else {
			return null;
		}
	}
}
