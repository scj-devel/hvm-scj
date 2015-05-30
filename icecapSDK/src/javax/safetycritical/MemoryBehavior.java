package javax.safetycritical;

abstract class MemoryBehavior {

	abstract void enter(Runnable logic, ManagedMemory memory) throws IllegalArgumentException;
	
	abstract void executeInArea(Runnable logic, ManagedMemory memory) throws IllegalArgumentException;
	
	abstract void enterPrivateMemory(int size, Runnable logic) throws IllegalStateException;
	
	abstract void executeInAreaOf(Object obj, Runnable logic);
	
	abstract void executeInOuterArea(Runnable logic);	
	
}


