package javax.realtime.device;

import javax.realtime.OffsetOutOfBoundsException;

import vm.Address32Bit;
import vm.HardwareObject;

/**
 * RawByte for Port-Mapped I/O on AT Mega
 * 
 * @author hso
 *
 */
public class RawByteATMega implements RawByte {

	long base;
	int count;
	int stride;
	
	private static class BytePortHWO extends HardwareObject {

		public byte PINx;   // PINA  has address 0x00	
		public byte DDRx;   // DDRA  has address 0x01
		public byte PORTx;  // PORTA has address 0x02

		BytePortHWO(long base, int count, int stride) {

			super(new Address32Bit((int)base));			
		}

		void add(int i) {
			address.add(i);
		}

		void sub(int i) {
			address.sub(i);
		}
	}

	BytePortHWO port;

	public RawByteATMega(long base, int count, int stride) {

		this.base = base;
		this.count = count;
		this.stride = stride;

		this.port = new BytePortHWO(base, count, stride);
	}
	
	@Override
	public byte getByte(int offset) throws OffsetOutOfBoundsException {
		if (offset < 0 || offset >= count)
			throw new OffsetOutOfBoundsException ("error in offset");
		if (offset == 0)
			return port.PINx;
		if (offset == 1)
			return port.DDRx;
		return port.PORTx;
	}

	@Override
	public byte getByte() {
		return port.PINx;
	}
	
	@Override
	public void setByte(int offset, byte value)
			throws OffsetOutOfBoundsException {
		
		if (offset < 0 || offset >= count)
			throw new OffsetOutOfBoundsException ("error in offset");
		
		//port.setByte(offset, (byte) (value | 0x1));
	}

	@Override
	public void setByte(byte value) {
		//port.setByte(0, (byte) (value | 0x1));
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

	

	private int min (int a, int b) {
		if (a < b)
			return a;
		return b;
	}
	
	private int min (int a, int b, int c) {
		return min (min(a, b), c);
	}
}


