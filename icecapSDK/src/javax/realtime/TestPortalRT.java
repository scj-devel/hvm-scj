package javax.realtime;

import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;
import javax.scj.util.Priorities;

import vm.Machine;
import vm.Memory;
import vm.POSIX64BitMachineFactory;

/**
 * The <code>TestPortalRT</code> contains the test probes needed in package
 * <code>javax.realtime</code> by the tests 
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
public final class TestPortalRT {
	
	private static final int TCKTESTMEMORYSIZE = 131070;

	private static Memory tckTestMemory;
	
	private static Memory mainMemory;
	/**
	 * Used by test programs to set up the virtual machine.
	 */	
	public static void setupVM() {
		// VM set for 64 bits
		Machine.setMachineFactory(new POSIX64BitMachineFactory()); 
	}
	
	/**
	 * Used by test programs to allocate some memory for local test objects
	 * because HVM has no garbage collector.
	 * PS: It is not for use when testing a full SCJ program.
	 */	
	private static void allocateMemoryForTckTest() {
		int start = Memory.allocateInHeap(TCKTESTMEMORYSIZE).getBase();

        tckTestMemory = new Memory(start, TCKTESTMEMORYSIZE, "tckTestMemory");
	}
	
	public static void enterTckMem() {
		if (tckTestMemory == null)
		{
			allocateMemoryForTckTest();
		}
		mainMemory = Memory.switchToArea(tckTestMemory);
	}
	
    public static void resetTckMem() {
    	Memory.switchToArea(mainMemory);
    	tckTestMemory.reset(0);
	}
	
	/**
	 * Used by test programs to create a <code>HighResolutionTime</code> stub object.	 
	 */
	public static class HighResolutionTimeStub extends HighResolutionTime<HighResolutionTimeStub> {
		
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
	
	public static final Clock getClck(HighResolutionTime<?> ht) {
		return ht.getClck();
	}
	
	public static final long getMillis(HighResolutionTime<?> ht) {
		return ht.getMillis();
	}
	
	public static final int getNanos(HighResolutionTime<?> ht) {
		return ht.getNanos();
	}
		
	public static final RelativeTime getResol(Clock clck) {
		return clck.getResol();
	}
	
	public static final boolean getAct(Clock clck) {
		return clck.getAct();
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
	
	/**
	 * Used by test programs to get the priority of a priority parameter.
	 * 
	 * @param release is a priority parameter.
	 * 
	 * @return the priority of the parameter <code>pp</code>.
	 */
	public static final int getPr(PriorityParameters pp) {
		return pp.priority;
	}
	
	
	/**
	 * Used by test programs to get the deadline of a release parameter.
	 *
	 * @param release is a release parameter.
	 * 
	 * @return the deadline of the parameter <code>release</code>.
	 */	
	public static RelativeTime getDeadline(ReleaseParameters release) {
		return release.deadline;
	}

	/**
	 * Used by test programs to get the miss handler of a release parameter.
	 *
	 * @param release is a release parameter.
	 * 
	 * @return the missHandler of the parameter <code>release</code>.
	 */	
	public static AsyncEventHandler getMissHandler(ReleaseParameters release) 
	{
		return release.missHandler;
	}
	
	
	public static	RelativeTime period(PeriodicParameters pp) {
		return pp.period;
	}
		
	public static	RelativeTime start(PeriodicParameters pp) {
		return pp.start;
	}
	
	/**
	 * Used by test programs to get the configuration parameter's message length.
	 *
	 * @param configParam is a configuration parameter.
	 * 
	 * @return the message length of the parameter <code>configParam</code>.
	 */	
	public static int getMessageLength(ConfigurationParameters configParam) {
		return configParam.getMessageLength();
	}
	
	/**
	 * Used by test programs to get the configuration parameter's stack trace length.
	 *
	 * @param configParam is a configuration parameter.
	 * 
	 * @return the stack trace length of the parameter <code>configParam</code>.
	 */	
	public static int getStackTraceLength(ConfigurationParameters configParam) {
		return configParam.getStackTraceLength();
	}
	
	/**
	 * Used by test programs to get <code>NO_MAX</code>.
	 * 
	 * @return the <code>NO_MAX</code> defined in <code>MemoryParameters</code>.
	 */	
	public static long getNO_MAX() {
		return MemoryParameters.NO_MAX;
	}

	/**
	 * Used by test programs to get the memory parameter's maximum memory area.
	 *
	 * @param mp is a memory parameter.
	 * 
	 * @return the maximum memory area of the parameter <code>mp</code>.
	 */	
	public static long getMaxMemoryArea(MemoryParameters mp) {
		return mp.getMaxMemoryArea();
	}

	/**
	 * Used by test programs to get the memory parameter's maximum immortal memory size.
	 *
	 * @param mp is a memory parameter.
	 * 
	 * @return the maximum immortal area of the parameter <code>mp</code>.
	 */	
	public static long getMaxImmortal(MemoryParameters mp) {
		return mp.getMaxImmortal();
	}
}




