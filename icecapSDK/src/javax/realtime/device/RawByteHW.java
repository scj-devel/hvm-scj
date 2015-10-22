package javax.realtime.device;

import javax.realtime.OffsetOutOfBoundsException;

import vm.Address32Bit;
import vm.HardwareObject;

public class RawByteHW implements RawByte {

	long base;
	int count;
	int stride;

	private static class ByteHWObject extends HardwareObject {

		byte current;

		ByteHWObject(long base, int count, int stride) {

			super(new Address32Bit((int)base));
			
		}

		void add(int i) {
			address.add(i);
		}

		void sub(int i) {
			address.sub(i);
		}

	}

	ByteHWObject byteHWObj;

	public RawByteHW(long base, int count, int stride) {

		this.base = base;
		this.count = count;
		this.stride = stride;

		this.byteHWObj = new ByteHWObject(base, count, stride);
	}

	@Override
	public int get(int offset, byte[] values)
			throws OffsetOutOfBoundsException, NullPointerException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get(int offset, byte[] values, int start, int count)
			throws OffsetOutOfBoundsException, ArrayIndexOutOfBoundsException,
			NullPointerException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getByte(int offset) throws OffsetOutOfBoundsException {
		byteHWObj.add(offset * stride);
		byte b = byteHWObj.current;
		byteHWObj.sub(offset * stride);
		return b;
	}

	@Override
	public byte getByte() {
		return byteHWObj.current;
	}

	@Override
	public long getAddress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return count * stride;
	}

	public int getStride() {

		return stride;
	}

	@Override
	public int set(int offset, byte[] values)
			throws OffsetOutOfBoundsException, NullPointerException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int set(int offset, byte[] values, int start, int count)
			throws OffsetOutOfBoundsException, ArrayIndexOutOfBoundsException,
			NullPointerException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setByte(int offset, byte value)
			throws OffsetOutOfBoundsException {
		byteHWObj.add(offset * stride);
		byteHWObj.current = value;
		byteHWObj.sub(offset * stride);
	}

	@Override
	public void setByte(byte value) {
		byteHWObj.current = value;
	}

}
