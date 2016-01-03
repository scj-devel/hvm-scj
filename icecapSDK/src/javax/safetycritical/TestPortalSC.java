package javax.safetycritical;

//import javax.realtime.ImmortalMemory;
import javax.realtime.PriorityParameters;
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
		Launcher.initSingleCoreBehaviour();
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

}



