package javax.realtime.device;

import javax.realtime.OffsetOutOfBoundsException;

import vm.Address32Bit;
import vm.HardwareObject;

public class RawByteHW_To_IOPort implements RawByte {

	long base;
	int count;
	int stride;

	private static class ByteHWObjectATMega2560IOPort extends HardwareObject {

		final byte PINA  = 0x00;
		final byte DDRA  = 0x01;
		final byte PORTA = 0x02;
		
		byte register;

		ByteHWObjectATMega2560IOPort(long base) {

			super(new Address32Bit((int)base));			
		}

		void add(int i) {
			address.add(i);
		}

		void sub(int i) {
			address.sub(i);
		}
	}

	ByteHWObjectATMega2560IOPort byteHWObj;

	public RawByteHW_To_IOPort(long base, int count, int stride) {

		this.base = base;
		this.count = count;
		this.stride = stride;

		this.byteHWObj = new ByteHWObjectATMega2560IOPort(base);
	}

	@Override
	public int get(int offset, byte[] values)
			throws OffsetOutOfBoundsException, NullPointerException {
		
		if (offset < 0 || offset >= count)
			throw new OffsetOutOfBoundsException ("error in offset");
		if (values == null)
			throw new NullPointerException ("values are null");		
		
		int number = count - offset;
		int length = min (number, values.length);
		for (int i = 0; i < length; i++) {
			
			values[i] = getByte(offset + i*stride);
		}		
		
		return length;
	}

	@Override
	public int get(int offset, byte[] values, int start, int count)
			throws OffsetOutOfBoundsException, ArrayIndexOutOfBoundsException,
			NullPointerException {
		
		if (offset < 0 || offset >= this.count || offset + count >= this.count)
			throw new OffsetOutOfBoundsException ("error in offset");
		if (start < 0 || start >= this.count || start + count >= this.count)
			throw new ArrayIndexOutOfBoundsException ("index out of bound");
		if (values == null)
			throw new NullPointerException ("values are null");
		if (count < 0)
			throw new NullPointerException ("count is negative");	
		
		int length = min(count, this.count - offset, values.length - start);
		
		for (int i = 0; i < length; i++) {
			
			values[start+i] = getByte(offset + i*stride);
		}		
		
		return length;
	}

	@Override
	public byte getByte(int offset) throws OffsetOutOfBoundsException {
		if (offset < 0 || offset >= count)
			throw new OffsetOutOfBoundsException ("error in offset");
		
		byteHWObj.add(offset * stride);  // stride is one
		byte b = byteHWObj.register;  // ?? if offset == 1, the value in register DDRA should be returned; offset == 2, value in register PORTA
		byteHWObj.sub(offset * stride);
		return b;
	}

	@Override
	public byte getByte() {
		return byteHWObj.PINA;  // get the value in register PINA
	}

	@Override
	public long getAddress() {
		return base;
	}

	@Override
	public int getSize() {
		
		return count * stride;
	}

	public int getStride() {

		return stride;
	}

	@Override
	public int set(int offset, byte[] values)
			throws OffsetOutOfBoundsException, NullPointerException {
		
		if (offset < 0 || offset >= count)
			throw new OffsetOutOfBoundsException ("error in offset");
		if (values == null)
			throw new NullPointerException ("values are null");		
		
		int number = count - offset;
		int length = min (number, values.length);
		for (int i = 0; i < length; i++) {
			
			setByte(offset + i*stride, values[i]);
		}
		
		return length;
	}

	@Override
	public int set(int offset, byte[] values, int start, int count)
			throws OffsetOutOfBoundsException, ArrayIndexOutOfBoundsException,
			NullPointerException {		

		if (offset < 0 || offset >= this.count || offset + count >= this.count)
			throw new OffsetOutOfBoundsException ("error in offset");
		if (start < 0 || start >= this.count || start + count >= this.count)
			throw new ArrayIndexOutOfBoundsException ("index out of bound");
		if (values == null)
			throw new NullPointerException ("values are null");
		if (count < 0)
			throw new NullPointerException ("count is negative");		
		
		int length = min(count, this.count - offset, values.length - start);
		
		for (int i = 0; i < length; i++) {
			
			setByte(offset + i*stride, values[start + i]);
		}
		
		return length;
	}

	@Override
	public void setByte(int offset, byte value)
			throws OffsetOutOfBoundsException {
		
		if (offset < 0 || offset >= count)
			throw new OffsetOutOfBoundsException ("error in offset");
		
		byteHWObj.add(offset * stride); 
		// byteHWObj.register = value;  // if offset == 1, put value in register DDRA; offset == 2, put value in register PORTA 
		byteHWObj.sub(offset * stride);
	}

	@Override
	public void setByte(byte value) {
		//byteHWObj.PINA = value;  // put value on register PINA  
	}

	private int min (int a, int b) {
		if (a < b)
			return a;
		return b;
	}
	
	private int min (int a, int b, int c) {
		return min (min(a, b), c);
	}
}


