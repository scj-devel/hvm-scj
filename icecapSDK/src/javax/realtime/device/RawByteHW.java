package javax.realtime.device;

import javax.realtime.OffsetOutOfBoundsException;

import vm.Address32Bit;
import vm.HardwareObject;

class RawByteHW implements RawByte {
	
	long base; 
	int count;
	int stride;
	
	HardwareObject hwObj;

	RawByteHW(long base, int count, int stride) {
		
		this.base = base;
		this.count = count;
		this.stride = stride;	
		
		this.hwObj = new HardwareObject(new Address32Bit((int)base));
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getByte() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getAddress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void setByte(byte value) {
		
		// TODO Auto-generated method stub

	}

}
