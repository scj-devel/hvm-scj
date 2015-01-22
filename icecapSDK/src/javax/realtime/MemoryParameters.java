package javax.realtime;

public class MemoryParameters implements Cloneable {

	public static final long NO_MAX = -1L;
	long maxMemoryArea;
	long maxImmortal;

	public MemoryParameters(long maxMemoryArea, long maxImmortal) {
		if (maxMemoryArea == -1L || maxMemoryArea <= 0)
			throw new IllegalArgumentException("maxMemoryArea not legal");
		if (maxImmortal == -1L || maxImmortal <= 0)
			throw new IllegalArgumentException("maxImmortal not legal");
		this.maxMemoryArea = maxMemoryArea;
		this.maxImmortal = maxImmortal;
	}

	public long getMaxMemoryArea() {
		return maxMemoryArea;
	}

	public long getMaxImmortal() {
		return maxImmortal;
	}
}
