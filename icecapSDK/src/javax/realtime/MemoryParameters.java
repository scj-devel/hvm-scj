package javax.realtime;

import java.io.Serializable;

public class MemoryParameters implements Cloneable, Serializable {

	private static final long serialVersionUID = 123456789987654321L;
	
	protected long maxMemoryArea;
	protected long maxImmortal;

	public static final long NO_MAX = -1L;

	public MemoryParameters(long maxMemoryArea, long maxImmortal) {
		if (maxMemoryArea < -1L)
			throw new IllegalArgumentException("maxMemoryArea not legal");
		if (maxImmortal < -1L)
			throw new IllegalArgumentException("maxImmortal not legal");

		this.maxMemoryArea = maxMemoryArea;
		this.maxImmortal = maxImmortal;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	//used in JML annotation only (not public)
	static long getNO_MAX() {
		return MemoryParameters.NO_MAX;
	}

	// used for JML annotation only (not public)
	long getMaxMemoryArea() {
		return maxMemoryArea;
	}

	// used for JML annotation only (not public)
	long getMaxImmortal() {
		return maxImmortal;
	}
}
