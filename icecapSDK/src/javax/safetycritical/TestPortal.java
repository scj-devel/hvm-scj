package javax.safetycritical;

import javax.realtime.PriorityParameters;
import javax.realtime.ReleaseParameters;

public final class TestPortal {

	public static void ManagedMemory_allocateBackingStore(int totalBackingStore) {
		ManagedMemory.allocateBackingStore(totalBackingStore);		
	}
	
	public static void singleCoreSetup (int totalBackingStore) {
		Launcher.initSingleCoreBehaviour();
		ManagedMemory.allocateBackingStore(totalBackingStore);		
	}

	public static ManagedMemory ManagedMemory_allocateImmortalMemory(int immortalSize) {
		return new ImmortalMemory(immortalSize);
	}

	public static ManagedMemory ManagedMemory_getOuterMemory(ManagedMemory m) {
		return ManagedMemory.getOuterMemory(m);
	}

	public static void executeInAreaOf(ManagedMemory mem, Runnable logic) {
		ManagedMemory.flag = true;
		mem.executeInArea(logic);
	}
	
	public static PriorityParameters getPriorityParam (PeriodicEventHandler pevh) {
		return pevh.priority;
	}
	
	public static ReleaseParameters getReleaseParam (PeriodicEventHandler pevh) {
		return pevh.release;
	}
	
}



