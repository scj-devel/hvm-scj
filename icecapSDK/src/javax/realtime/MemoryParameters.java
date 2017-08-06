package javax.realtime;

import java.io.Serializable;

import javax.safetycritical.annotate.SCJAllowed;

public class MemoryParameters implements Cloneable, Serializable {

	private static final long serialVersionUID = 123456789987654100L;
	
	protected long maxInitialArea;  // maximum amount of memory in the per-release private memory area.
	protected long maxImmortal;

	public static final long NO_MAX = -1L;
	
	@SCJAllowed
	public static final long UNLIMITED = -2L;
	@SCJAllowed
	public static final long UNREFERENCED = -3L;

	public MemoryParameters(long maxInitialArea, long maxImmortal) {
		if (maxInitialArea <= -1L)
			throw new IllegalArgumentException("maxInitialArea not legal");
		if (maxImmortal <= -1L)
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

	// used for JML annotation only in TestPortalRT (not public)
	long getMaxInitialArea() {
		return maxInitialArea;
	}

	// used for JML annotation only (not public)
	long getMaxImmortal() {
		return maxImmortal;
	}
}
