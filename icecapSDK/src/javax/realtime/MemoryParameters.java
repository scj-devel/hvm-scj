package javax.realtime;

import java.io.Serializable;

import javax.safetycritical.annotate.SCJAllowed;

public class MemoryParameters implements Cloneable, Serializable {

	private static final long serialVersionUID = 123456789987654100L;
	
	protected long maxInitialArea;  // maximum amount of memory in the per-release private memory area.
	protected long maxImmortal;
	
	@SCJAllowed
	public static final long UNLIMITED = Long.MAX_VALUE;

	public MemoryParameters(long maxInitialArea, long maxImmortal) {
		if (maxInitialArea < 0)
			throw new IllegalArgumentException("maxInitialArea not legal");
		if (maxImmortal < 0)
			throw new IllegalArgumentException("maxImmortal not legal");

		this.maxInitialArea = maxInitialArea;
		this.maxImmortal = maxImmortal;
	}
	
	public Object clone() 
	{ 
		// Implementation: Bloch: Effective Java, p. 46
		try {
			System.out.println("MemoryParameters.clone");
			return super.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new Error("MemoryParameters.clone: Assertion failure");  // can't happen
		}
	}

	// public in Draft, Version 0.113, 1 June 2018
	public long getMaxInitialArea() { 		
		return maxInitialArea;
	}

	// public in Draft, Version 0.113, 1 June 2018
	public long getMaxImmortal() {
		return maxImmortal;
	}
}
