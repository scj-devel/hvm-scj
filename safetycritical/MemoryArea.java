/**************************************************************************
 * File name  : MemoryArea.java
 * 
 * This file is part of our SCJ Level 0 and Level 1 implementation, 
 * based on SCJ Draft, Version 0.79. 16 May 2011.
 *
 * It is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as  
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This SCJ Level 0 and Level 1 implementation is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the  
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this SCJ Level 0 and Level 1 implementation.  
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2012 
 * @authors  Anders P. Ravn, Aalborg University, DK
 *           Stephan E. Korsholm and Hans S&oslash;ndergaard, 
 *             VIA University College, DK
 *   
 * Description: 
 * 
 * Revision history:
 *   date   init  comment
 *
 *************************************************************************/

package javax.safetycritical;

import icecaptools.IcecapCompileMe;

import java.lang.reflect.Array;
import java.util.Stack;

import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;

import vm.AllocationArea;
import vm.VM;

/**
 * All allocation contexts are implemented by memory areas. This is the
 * base-level class for all memory areas.
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment - semantic issue: <code>MemoryArea</code> is part of an
 *             infrastructure. It should not be accessible to applications. At
 *             least it should be tagged INFRASTRUCTURE.
 *             <p>
 *             - implementation issue: Omitted the following methods:
 *             <ul>
 *             <code>  
 *     <li>public static MemoryArea getMemoryArea(java.lang.Object object) 
 *     <li>public Object newArray (Class type, int number) 
 *     <li>public Object newInstance (Class type) 
 *    </code>
 *             </ul>
 */

@SCJAllowed(Level.INFRASTRUCTURE)
public class MemoryArea  implements AllocationContext {
	static class AllocationAreaStack {
		private Stack<AllocationArea> allocationAreaStack;

		static AllocationAreaStack instance;

		private AllocationAreaStack() {
			AllocationArea defaultAllocationArea = VM.getCurrentAllocationArea();
			allocationAreaStack = new Stack<AllocationArea>();
			allocationAreaStack.push(defaultAllocationArea);
		}

		static AllocationAreaStack instance() {
			return instance;
		}

		void popAllocArea() {
			AllocationArea oldScope;
			oldScope = allocationAreaStack.pop();
			oldScope.switchToArea(allocationAreaStack.peek());
		}

		void pushAllocArea(AllocationArea aa) {
			allocationAreaStack.peek().switchToArea(aa);
			allocationAreaStack.push(aa);
		}
	}

	/** Singleton reference for the immortal memory. */
	static ImmortalMemory immortal;

	private static MemoryArea lastAllocatedArea = null;

	AllocationArea delegate;
	
	MemoryArea(int base, int size) {
		delegate = VM.getAllocationArea(base, size);
		// devices.Console.println("MemoryArea, start-end-size " + this.base +
		// " " + (this.base + this.size) + " " + this.size);
	}

	/**
	 * Creates a new memory area.
	 * 
	 * @param size
	 *            The size of this new memory area.
	 */
	MemoryArea(int size) {
		delegate = VM.getAllocationArea(lastAllocatedArea.delegate.getBase() + lastAllocatedArea.delegate.getSize(), size);
		// devices.Console.println("MemoryArea, start-end-size " + this.base +
		// " " + (this.base + this.size) + " " + this.size);
		lastAllocatedArea = this;
	}

	/**
	 * Create a <code>MemoryArea</code> that represents immortal memory. <br>
	 * Is only called once in <code>ImmortalMemory.instance()</code> .
	 */
	static ImmortalMemory getImmortal(int backingStoreSize, int immortalMemSize) {
		if (immortal == null) {

			int base = VM.allocateBackingStore(backingStoreSize);

			immortal = new ImmortalMemory(base, immortalMemSize);

			lastAllocatedArea = immortal;

			AllocationAreaStack.instance = new AllocationAreaStack();
			// devices.Console.println("MemoryArea getImmortal, start-size " +
			// immortal.base + " " + immortal.size);
		}

		return immortal;
	}

	protected static MemoryArea currentActiveArea = null;

	/**
	 * Makes this memory area the allocation context for the execution of the
	 * <code>run()</code> method of the instance of <code>Runnable</code> given
	 * in the constructor. <br>
	 * During this period of execution, this memory area becomes the default
	 * allocation context until another allocation context is selected or the
	 * <code>Runnable</code>'s <code>run</code> method exits.
	 * <p>
	 * This method is like the <code>executeInArea</code> method, but extended
	 * with cleanup and pointer reset.
	 * 
	 * @throws IllegalArgumentException
	 *             Thrown if the caller is a schedulable object and
	 *             <code>logic</code> is null.
	 * 
	 * @param logic
	 *            The <code>Runnable</code> object whose <code>run()</code>
	 *            method shall be executed.
	 */
	@SCJAllowed(Level.INFRASTRUCTURE)
	@IcecapCompileMe
	void enter(Runnable logic) throws IllegalArgumentException {
		if (logic == null)
			throw new IllegalArgumentException();

		MemoryArea outerArea = currentActiveArea;
		currentActiveArea = this;

		AllocationAreaStack.instance().pushAllocArea(this.delegate);

		// devices.Console.println(this.toString());

		logic.run();

		AllocationAreaStack.instance().popAllocArea();

		// devices.Console.println(this.toString());

		this.delegate.reset();

		// devices.Console.println(this.toString());

		currentActiveArea = outerArea;
	}

	void resetMemoryArea() {
		this.delegate.reset();
		// devices.Console.println("MemoryArea.resetMemoryArea");
		// devices.Console.println(this.toString());
	}

	/**
	 * Return the memory region which we are currently in.
	 */
	static MemoryArea getCurrentMemory() // It has not been tested
	{
		return currentActiveArea;
	}

	/**
	 * 
	 * @scjComment Omitted, - not implemented
	 * @param object
	 *            An object.
	 * @return The memory area in which <code>object</code> is allocated.
	 */
	@SCJAllowed
	public static MemoryArea getMemoryArea(java.lang.Object object) {
		return null;
	}

	/**
	 * Executes <code>logic</code> in this memory area, with no cleanup and no
	 * pointer reset at the end.
	 * 
	 * @param logic
	 *            The Runnable object whose <code>run()</code> method shall be
	 *            executed.
	 * 
	 * @throws IllegalArgumentException
	 *             If <code>logic</code> is null.
	 */
	@SCJAllowed
	public void executeInArea(Runnable logic) throws IllegalArgumentException {
		if (logic == null)
			throw new IllegalArgumentException();

		MemoryArea outerArea = currentActiveArea;
		currentActiveArea = this;

		AllocationAreaStack.instance().pushAllocArea(this.delegate);

		// devices.Console.println(this.toString());

		logic.run();

		AllocationAreaStack.instance().popAllocArea();

		// devices.Console.println(this.toString());

		currentActiveArea = outerArea;
	}

	/**
	 * @return The memory consumed (in bytes) in this memory area.
	 */
	@SCJAllowed
	public long memoryConsumed() {
		return (long) delegate.consumedMemory();
	}

	/**
	 * @return The memory remaining (in bytes) in this memory area.
	 */
	@SCJAllowed
	public long memoryRemaining() {
		return size() - memoryConsumed();
	}

	/**
	 * Creates an new array of the given type in this memory area.
	 * 
	 * @scjComment Omitted, - not tested
	 * 
	 * @param type
	 *            is the class type of objects this array should hold
	 * @param number
	 *            is the length of the array
	 * @return A new array of type <code>type</code> and length
	 *         <code>number</code>.
	 */
	@SCJAllowed
	public Object newArray(@SuppressWarnings("rawtypes") Class type, int number) throws IllegalArgumentException, OutOfMemoryError {
		MemoryArea outerArea = currentActiveArea;
		currentActiveArea = this;

		AllocationAreaStack.instance().pushAllocArea(this.delegate);

		Object array = Array.newInstance(type, number);

		AllocationAreaStack.instance().popAllocArea();

		currentActiveArea = outerArea;
		return array;
	}

	/**
	 * Creates a new instance of the given type in this memory area.
	 * 
	 * @scjComment Omitted, - not tested
	 * 
	 * @param type
	 *            is the class type of object to be created
	 * @return An object of type <code>type</code>.
	 */
	@SCJAllowed
	public Object newInstance(@SuppressWarnings("rawtypes") Class type) throws IllegalAccessException, IllegalArgumentException, InstantiationException, OutOfMemoryError {
		MemoryArea outerArea = currentActiveArea;
		currentActiveArea = this;

		AllocationAreaStack.instance().pushAllocArea(this.delegate);
		Object object = type.newInstance();

		AllocationAreaStack.instance().popAllocArea();

		currentActiveArea = outerArea;
		return object;
	}

	/**
	 * @return The size of the current memory area in bytes.
	 */
	@SCJAllowed
	public long size() {
		return this.delegate.getSize();
	}

	/**
	 * Helper method for <code>ManagedMemory.enterPrivateMemory</code>
	 * 
	 * @param size
	 *            The size of inner private memory
	 * @param logic
	 *            The logic to be executed in inner private memory
	 */
	/** Inner area for enterPrivateMemory. */
	MemoryArea innerArea;

	void enterMemory(int size, Runnable logic) {

		// ToDo: resize ? + test it
		if (innerArea == null) {
			innerArea = new MemoryArea(size);
		}
		// Set all fields for inner and call enter
		innerArea.enter(logic);
		innerArea = null; // ??
	}
}
