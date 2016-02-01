package devices;

public class DefaultWriter implements Writer {

	private static native void nwrite(byte[] bytes, int length);
	
	@Override
	public void write(byte[] bytes, short length) {
		nwrite(bytes, length);
	}

	@Override
	public short getMaxLineLength() {
		return 512;
	}
}
