package vm;

import icecaptools.IcecapCVar;
import icecaptools.IcecapCompileMe;

public class Memory {
	private int base;
	private int size;
	private int free;

	public Memory(int base, int size) {		
		this.base = base;
		this.size = size;
		this.free = 0;
		// devices.Console.println(this.toString());
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ap: ");
		buffer.append(base);
		buffer.append(':');
		buffer.append(free);
		buffer.append(':');
		buffer.append(size);
		return buffer.toString();
	}

	@IcecapCompileMe
	public void switchToArea(Memory newScope) {
		Memory newScopeArea = (Memory) newScope;
		currentMemoryArea = newScopeArea;
	}

	@IcecapCompileMe
	public static int allocateBackingStore(int size) {

		if (heapArea.free + size >= heapArea.size) {
			throw new OutOfMemoryError();
		}

		int startPtr = heapArea.base + heapArea.free;
		heapArea.free += size;
		return startPtr;
	}

	@IcecapCVar
	private static Memory currentMemoryArea;

	@IcecapCVar
	private static Memory heapArea;

	/* Returns the entire heap as a Memory object. This
	 * is used to allocate the backing store inside the heap. 
	 * 
	 * The heap contains more than just the SCJ managed memories. It also
	 * contains everything loaded by the class initializers and all the constants.
	 * */
	@IcecapCompileMe
	static public Memory getHeapArea() {
		return heapArea;
	}

	/**
	 * Resets the memory by setting free to the new free value
	 * newFree = 0 means, that the whole memory is reset
	 * Precondition: 0 <= newFree < size
	 * 
	 * @param newFree the new free value 
	 */
	public void reset(int newFree) {
		free = newFree;
	}

	public int consumedMemory() {
		return free;
	}

	public int getBase() {
		return base;
	}

	public int getSize() {
		return size;
	}
}
