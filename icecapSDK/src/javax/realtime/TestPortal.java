package javax.realtime;

import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;
import javax.scj.util.Priorities;

/**
 * The <code>TestPortal</code> contains the test probes needed by the tests 
 * in the Technology Compatibility Kit (TCK).
 * It must be implemented for an implementation under test.
 * 
 * @version 1.0; - December 2015
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@via.dk">hso@via.dk</A>
 */
@SCJAllowed(Level.SUPPORT)
public final class TestPortal {
	
	/**
	 * Used by test programs to create a <code>HighResolutionTime</code> stub object.	 
	 */
	public static class HighResolutionTimeStub extends HighResolutionTime {
		
		/**
		 * Constructs an <code>HighResolutionTimeStub</code> object. <br>
		 *
		 * @param millis is the desired value for the millisecond component.
		 * 
		 * @param nanos is the desired value for the nanosecond component.
		 * 
		 * @param clock is the desired value for the clock.
		 */
		public HighResolutionTimeStub(long millis, int nanos, Clock clock) {
			super(millis, nanos, clock);
		}
	}
	
	/**
	 * Used by test programs to get the maximum priority.
	 * 
	 * @return the maximum priority.
	 */
	public static final int getMaxPr() {
		return Priorities.MAX_PRIORITY;
	}
	
	/**
	 * Used by test programs to get the minimum priority.
	 * 
	 * @return the minimum priority.
	 */
	public static final int getMinPr() {
		return Priorities.MIN_PRIORITY;
	}
	
}


