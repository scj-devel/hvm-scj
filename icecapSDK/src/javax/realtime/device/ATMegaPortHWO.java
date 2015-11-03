package javax.realtime.device;

import javax.realtime.OffsetOutOfBoundsException;

import vm.Address;
import vm.HardwareObject;

public class ATMegaPortHWO extends HardwareObject implements RawByte {

	public ATMegaPortHWO(Address address) {
		super(address);
		// TODO Auto-generated constructor stub
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

	@Override
	public int getStride() {
		// TODO Auto-generated method stub
		return 0;
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
