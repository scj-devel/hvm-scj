package javax.safetycritical;

//import javax.realtime.ImmortalMemory;
import javax.realtime.MemoryArea;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.ReleaseParameters;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;

/**
 * The <code>TestPortalSC</code> contains the test probes needed in package
 * <code>javax.safetycritical</code>by the tests 
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
public final class TestPortalSC {

	/**
	 * Used by test programs to get  .
	 * 
	 * @param totalBackingStore is 
	 */
//	public static void ManagedMemory_allocateBackingStore(int totalBackingStore) {
//		ManagedMemory.allocateBackingStore(totalBackingStore);		
//	}
	
	/**
	 * Used by test programs to initialize and set up the behaviour of a
	 * single core test, including allocating backing store memory.
	 * 
	 * @param totalBackingStore is the size of the backing store allocated for this TCK test.
	 */
	public static void singleCoreSetup (int totalBackingStore) {
		LaunchSingleCore.initSingleCoreBehaviour();
		ManagedMemory.allocateBackingStore(totalBackingStore);		
	}

	/**
	 * Used by test programs to get  .
	 * 
	 * @param immortalSize is 
	 *  
	 * @return the
	 */
//	public static ManagedMemory ManagedMemory_allocateImmortalMemory(int immortalSize) {
//		return new ImmortalMemory(immortalSize);
//	}
	
	/**
	 * Used by test programs to get the remaining memory area available to
	 * the current Managedmemory area.
	 *
	 * @return the available remaining memory area.  
	 */
	public static long getCurrentRemainingArea()  {
		return ManagedMemory.getCurrentRemainingArea();
	}
	
	/**
	 * Used by test programs to get the current memory area.
	 *
	 * @return the current memory area.
	 */
	public static MemoryArea getCurrentAllocationArea() {
		return ManagedMemory.getCurrentAllocationArea();
	}
	
	/**
	 * Used by test programs to get the topmost memory area.
	 *
	 * @return the topmost memory area.
	 */
	public static MemoryArea getTopMostArea() {
		return ManagedMemory.getTopMostArea();
	}

	/**
	 * Used by test programs to get the outer memory of a <code>ManagedMemory</code> area.
	 * 
	 * @param mm is the area of interest.
	 * 
	 * @return the outer memory area of <code>mm</code>.
	 */
	public static ManagedMemory ManagedMemory_getOuterMemory(ManagedMemory mm) {
		return ManagedMemory.getOuterMemory(mm);
	}

//	public static void executeInAreaOf(ManagedMemory mem, Runnable logic) {
//		ManagedMemory.flag = true;
//		mem.executeInArea(logic);
//	}
	
	/**
	 * Used by test programs to get the priority parameter of a periodic event handler.
	 * 
	 * @param pevh is a periodic event handler.
	 *  
	 * @return the priority parameter of <code>pevh</code>.
	 */
	public static PriorityParameters getPriorityParam (PeriodicEventHandler pevh) {
		return pevh.priority;
	}
	
	/**
	 * Used by test programs to get the release parameter of a periodic event handler.
	 * 
	 * @param pevh is a periodic event handler.
	 *  
	 * @return the release parameter of <code>pevh</code>.
	 */
	public static ReleaseParameters getReleaseParam (PeriodicEventHandler pevh) {
		return pevh.release;
	}
	
	/**
	 * Used by test programs to get the storage parameter of a periodic event handler.
	 * 
	 * @param pevh is a periodic event handler.
	 *  
	 * @return the storage parameter of <code>pevh</code>.
	 */
	public static StorageParameters getStorageParam (PeriodicEventHandler pevh) {
		return pevh.storage;
	}

	/**
	 * Used by test programs to get the frame array of a cyclic scheduler.
	 * 
	 * @param cs is a cyclic scheduler.
	 *  
	 * @return the frame array of <code>cs</code>.
	 */
	public static Frame[] getFrames(CyclicSchedule cs) {
		return cs.getFrames();
	}
	
	/**
	 * Used by test programs to get the periodic event handler array of a frame.
	 * 
	 * @param f is a frame.
	 *  
	 * @return The periodic event handler array of frame <code>f</code>.
	 */
	public static PeriodicEventHandler[] getHandlers(Frame f) {
		return f.getHandlers();
	}

	/**
	 * Used by test programs to get the duration of a frame.
	 * 
	 * @param f is a frame.
	 * 
	 * @return The duration of frame <code>f</code>.
	 */
	public static RelativeTime getDuration(Frame f) {
		return f.getDuration();
	}
}



