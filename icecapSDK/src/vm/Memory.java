package vm;

import icecaptools.IcecapCVar;
import icecaptools.IcecapCompileMe;
import util.StringUtil;

import java.util.ArrayList;

import javax.realtime.MemoryArea;

public class Memory {
	private int base;
	private int size;
	private int free;

	private String name;

	private MemoryInfo memoryInfo;

	private static int backingStoreOffset;
	
	private static class MemoryInfo {
		String name;
		int size;
		int maxUsed;
		private int instanceCount;

		public MemoryInfo(Memory m) {
			this.name = m.name;
			this.size = m.size;
			this.maxUsed = 0;
			instanceCount = 1;
		}

		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			buffer.append(name);
			buffer.append(StringUtil.constructString("[", instanceCount));
			buffer.append("]");
			buffer.append(StringUtil.constructString(": ", size));
			buffer.append(StringUtil.constructString(", used = ", maxUsed));
			return buffer.toString();
		}

		public void increaseInstanceCount() {
			instanceCount++;

		}
	}

	public static boolean memoryAreaTrackingEnabled;
	private static Memory areaToUseForTracking;

	private static ArrayList<MemoryInfo> createdMemories;

	@IcecapCompileMe
	private MemoryInfo addMemoryArea(Memory m) {

		Memory current;
		if (areaToUseForTracking == null) {
			areaToUseForTracking = m;
		}
		current = switchToArea(areaToUseForTracking);
		if (createdMemories == null) {
			createdMemories = new ArrayList<MemoryInfo>();
			heapArea.name = "HEAP";
			MemoryInfo memory = new MemoryInfo(heapArea);
			createdMemories.add(memory);
			heapArea.memoryInfo = memory;
		}
		for (MemoryInfo memory : createdMemories) {
			if (memory.name.compareTo(m.name) == 0) {
				memory.increaseInstanceCount();
				switchToArea(current);
				return memory;
			}
		}
		MemoryInfo memory = new MemoryInfo(m);
		createdMemories.add(memory);
		switchToArea(current);
		return memory;
	}

	@IcecapCompileMe
	public static void reportMemoryUsage() {
		if (memoryAreaTrackingEnabled) {

			if (createdMemories != null) {
				Memory current = switchToArea(areaToUseForTracking);

				devices.Console.print(StringUtil.constructString("\nCreated ", createdMemories.size()));
				devices.Console.println(" memory area types:");
				for (MemoryInfo memory : createdMemories) {
					devices.Console.println(memory.toString());
				}
				devices.Console.println(StringUtil.constructString("BS used = ", MemoryArea.getRemainingMemorySize()));
				devices.Console.println(StringUtil.constructString("BS heap offset = ", backingStoreOffset));
				switchToArea(current);
			} else {
				devices.Console.println("No created memories recorded");
			}
		} else {
			devices.Console.println("Memory tracking disabled");
		}
	}

	@IcecapCompileMe
	public Memory(int base, int size, String name) {
		this.base = base;
		this.size = size;
		this.free = 0;
		this.name = (name == null ? "Unknown" : name);
		if (memoryAreaTrackingEnabled) {
			this.memoryInfo = addMemoryArea(this);
		}
	}
	
	private Memory(int base, int size) {
		this.base = base;
		this.size = size;
		this.free = 0;
		this.name = "BStore";
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(name);
		buffer.append(StringUtil.constructString(": size = ", size));
		buffer.append(StringUtil.constructString(", used = ", free));
		return buffer.toString();
	}

	@IcecapCompileMe
	public static Memory switchToArea(Memory newScope) {
		Memory previousMemoryArea = (Memory) currentMemoryArea;
		currentMemoryArea = newScope;
		return previousMemoryArea;
	}

	@IcecapCompileMe
	public static Memory allocateInHeap(int size) {
		// devices.Console.println("allocating backingstore");
		
		if (heapArea.free + size >= heapArea.size) {
			throw new OutOfMemoryError();
		}

		int startPtr = heapArea.base + heapArea.free;
		backingStoreOffset = heapArea.free;
		heapArea.free += size;

		Memory memory = new Memory(startPtr, size);
		
		return memory;
	}

	@IcecapCVar
	private static Memory currentMemoryArea;
	
	@IcecapCVar
	private static Memory heapArea;

	/*
	 * Returns the entire heap as a Memory object. This is used to allocate the
	 * backing store inside the heap.
	 * 
	 * The heap contains more than just the SCJ managed memories. It also
	 * contains everything loaded by the class initializers and all the
	 * constants.
	 */
	@IcecapCompileMe
	static public Memory getHeapArea() {
		return heapArea;
	}

	@IcecapCompileMe
	static public Memory getCurrentMemoryArea() {
		return currentMemoryArea;
	}
	
	/**
	 * Resets the memory by setting free to the new free value newFree = 0
	 * means, that the whole memory is reset Precondition: 0 <= newFree < size
	 * 
	 * @param newFree
	 *            the new free value
	 */
	public void reset(int newFree) {
		free = newFree;
	}

	public void resize(int newSize) {
		size = newSize;
	}
	
	public int consumedMemory() {
		return free;
	}

	public int getBase() {
		return base;
	}
	
	@IcecapCompileMe
	public int getStartMemoryAddress()
	{
		if (heapArea.base != 4)
		{
			return base;
		}
		else
		{
			return (int) (getHeapBase() + base);
		}
	}

	private static native long getHeapBase();

	public int getSize() {
		return size;
	}

	@IcecapCompileMe
	public static void startMemoryAreaTracking() {
		updateMaxUsed(heapArea);
		memoryAreaTrackingEnabled = true;
	}

	@IcecapCompileMe
	public static void updateMaxUsed(Memory m) {
		if (m.memoryInfo != null) {
			if (m.free > m.memoryInfo.maxUsed) {
				m.memoryInfo.maxUsed = m.free;
			}
		}
	}

	@IcecapCompileMe
	public static void executeInHeap(Runnable logic) {
		Memory current = switchToArea(areaToUseForTracking);
		logic.run();
		switchToArea(current);
	}

	public String getName()
	{
		return name;
	}

	private static int nameCount;
	
	public static String getNextMemoryName(String defaultName) {
		if (memoryAreaTrackingEnabled)
		{
			Memory current = switchToArea(areaToUseForTracking);
			String name = StringUtil.constructString(defaultName, nameCount);
			nameCount++;
			switchToArea(current);
			return name;
		}
		else
		{
			return defaultName;
		}
	}

	public static void dumpLiveMemories() {
		if (memoryAreaTrackingEnabled)
		{
			Memory current = switchToArea(areaToUseForTracking);
			MemoryArea.printMemoryAreas();
			switchToArea(current);
		}
		else
		{
		}		
	}
	
	public static void executeInTrackingArea(Runnable logic)
	{
		if (memoryAreaTrackingEnabled)
		{
			Memory current = switchToArea(areaToUseForTracking);
			logic.run();
			switchToArea(current);
		}
		else
		{
		}
	}
}
