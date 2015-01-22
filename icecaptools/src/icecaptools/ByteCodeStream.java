package icecaptools;

public class ByteCodeStream {
	private long bytecodeLength;
	private byte[] bytecode;
	private long top;
    private ByteCollector byteCollector;

    public ByteCodeStream()
    {
        byteCollector = null;
    }
	
    public int get() {
		int c;

		c = (bytecode[(int) top]) & 0xff;
		top = top + 1;
		if (byteCollector != null)
		{
		    byteCollector.collect((byte) c);
		}
		return c;
	}

	public long length() {
		return bytecodeLength;
	}

	public long pos() {
		return top;
	}

	public void init(byte[] bytecode) {
		this.bytecode = bytecode;
		bytecodeLength = bytecode.length;
		top = 0;
	}

	public boolean hasMore() {
		return top < bytecodeLength;
	}

    public void setByteCollector(ByteCollector byteCollector) {
        this.byteCollector = byteCollector;
        
    }

    public void seek(int address) {
        top = address;
    }
}
