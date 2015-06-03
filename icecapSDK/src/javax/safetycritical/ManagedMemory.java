/**************************************************************************
 * File name  : ManagedMemory.java
 * 
 * This file is part a SCJ Level 0 and Level 1 implementation, 
 * based on SCJ Draft, Version 0.94 25 June 2013.
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
 *************************************************************************/

package javax.safetycritical;

import icecaptools.IcecapCompileMe;

import javax.realtime.MemoryArea;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;

import vm.Memory;

/**
 * Managed memory is a scoped memory area that is managed by a mission.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 */
@SCJAllowed
public abstract class ManagedMemory extends MemoryArea {

	static boolean flag = true;
	static MemoryBehavior memoryBehavior = null;

	/**
	 * statically allocated Exception prevents memory area reference mismatches.
	 */
	private static final IllegalArgumentException exception = new IllegalArgumentException();

	private static class BackingStore extends MemoryArea {

		public BackingStore(Memory delegate) {
			super(delegate);
		}
	}

	/**
	 * This method is called once in javax.safetycritical.Launcher
	 * 
	 * @param size
	 *            The size of the backing store used by the SCJ application
	 */
	static void allocateBackingStore(int size) {
		MemoryArea.overAllBackingStore = new BackingStore(Memory.allocateInHeap(size));
	}

	public static class ImmortalMemory extends ManagedMemory // HSO: not public
	{
		ImmortalMemory(int sizeOfArea) {
			super(sizeOfArea, sizeOfArea, MemoryArea.overAllBackingStore, "Imm");
		}

		public static ImmortalMemory instance() {
			MemoryArea result = MemoryArea.getNamedMemoryArea("Imm");
			if (result != null) {
				return (ImmortalMemory) result;
			}
			return null;
		}
	}

	/**
	 * @param size  is the number of free bytes in the memory area
	 */
	@IcecapCompileMe
	ManagedMemory(int size, int BackingStoreOfThisMemory, MemoryArea backingStoreProvider, String label) {
		super(size, BackingStoreOfThisMemory, backingStoreProvider, label);
	}

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
	 *    if the caller is a schedulable object and <code>logic</code>
	 *    is null.
	 * 
	 * @param logic
	 *    is the <code>Runnable</code> object whose <code>run()</code>
	 *    method shall be executed.
	 */
	@SCJAllowed(Level.INFRASTRUCTURE)
	@IcecapCompileMe
	void enter(Runnable logic) throws IllegalArgumentException {
		memoryBehavior.enter(logic,this);
	}

	/**
	 * Executes <code>logic</code> in this memory area, with no cleanup and no
	 * pointer reset at the end.
	 * 
	 * @param logic
	 *    The Runnable object whose <code>run()</code> method shall be executed.
	 * 
	 * @throws IllegalArgumentException
	 *    If <code>logic</code> is null.
	 */
	@SCJAllowed
	void executeInArea(Runnable logic) throws IllegalArgumentException {
		memoryBehavior.executeInArea(logic, this);
	}

	static final ManagedMemory getOuterMemory(MemoryArea mem) {
		if (mem instanceof InnerPrivateMemory)
			return ((InnerPrivateMemory) mem).prev;

		else if (mem instanceof PrivateMemory)
			return Mission.getMission().getSequencer().getMissionMemory();

		else if (mem instanceof MissionMemory) {
			// return nearest outermost memory
			MissionSequencer<?> missSeq = Mission.getMission().getSequencer();
			if (missSeq.outerSeq == null)
				return ImmortalMemory.instance();
			else
				return missSeq.getOuterSeq().getMissionMemory();
		} else
			return null;
	}

	/**
	 * Invoke the run method of logic with a fresh private memory area that is
	 * immediately nested within the current <code>ManagedMemory</code> area,
	 * sized to provide <code>size</code> bytes of free memory as the current
	 * allocation area.
	 * 
	 * @param size
	 *            is the number of free bytes within the inner-nested private
	 *            memory.
	 * 
	 * @param logic
	 *            provides the run method that is to be executed within the
	 *            inner-nested private memory area.
	 */
	@SCJAllowed
	public static void enterPrivateMemory(int size, Runnable logic) throws IllegalStateException {
		memoryBehavior.enterPrivateMemory(size, logic);
	}

	private static ManagedMemory getMemory(ManagedSchedulable ms) {
		if (ms instanceof ManagedEventHandler) {
			ManagedEventHandler mevh = (ManagedEventHandler) ms;
			return mevh.privateMemory;
		} else if (ms instanceof ManagedThread) {
			ManagedThread mth = (ManagedThread) ms;
			return mth.privateMemory;
		} else {
			// (ms is instanceof ManagedLongEventHandler)
			ManagedLongEventHandler mlevh = (ManagedLongEventHandler) ms;
			return mlevh.privateMemory;
		}
	}

	@SCJAllowed
	public static void executeInAreaOf(Object obj, Runnable logic) {
		memoryBehavior.executeInAreaOf(obj, logic);
	}

	@SCJAllowed
	public static void executeInOuterArea(Runnable logic) {
		memoryBehavior.executeInOuterArea(logic);
	}

	/**
	 * 
	 * @return the size of the remaining memory available to the current
	 *         ManagedMemory area.
	 * @scjComment the same as memoryRemaining() in MemoryArea?
	 */
	@SCJAllowed
	public long getRemainingBackingStore() {
		return memoryRemaining();
	}

	/**
	 * Resetting the number of available bytes. The parameter
	 * <code>newFree</code> is typically acquired by a previous call of
	 * <code>memoryConsumed()</code>.
	 * 
	 * @param newFree
	 *            the number.
	 */
	private void resetArea(long newFree) {
		this.delegate.reset((int) newFree);
	}

	void resetArea() {
		this.delegate.reset(0);
	}

	void removeArea() {
		this.removeMemArea();
	}

	void resizeArea(long newSize) {
		this.resizeMemArea(newSize);

	}

	protected Memory getDelegate() {
		return delegate;
	}

	// used for JML annotation only (not public)    
	static MemoryArea getCurretAllocationArea() {
		return getCurrentMemoryArea();
	}

	// used for JML annotation only (not public)
	MemoryArea getTopMostArea() {
		return null;
	}

	static abstract class MemoryBehavior{
		abstract void enter(Runnable logic, ManagedMemory memory) throws IllegalArgumentException;
		
		abstract void executeInArea(Runnable logic, ManagedMemory memory) throws IllegalArgumentException;
		
		abstract void enterPrivateMemory(int size, Runnable logic) throws IllegalStateException;
		
		abstract void executeInAreaOf(Object obj, Runnable logic);
		
		abstract void executeInOuterArea(Runnable logic);	
	}
	
	static final class MulticoreBehavior extends MemoryBehavior{

		@Override
		void enter(Runnable logic, ManagedMemory memory) throws IllegalArgumentException {
			if (logic == null || !(logic instanceof ManagedSchedulable))
				throw new IllegalArgumentException();
			ManagedSchedulable ms = (ManagedSchedulable) logic;

			ManagedMemory outer;

			if (ms instanceof ManagedEventHandler) {
				outer = ((ManagedEventHandler) ms).currentMemory;
				((ManagedEventHandler) ms).currentMemory = memory;
			} else {
				outer = ((ManagedThread) ms).currentMemory;
				((ManagedThread) ms).currentMemory = memory;
			}

			OSProcess.setMemoryArea(memory.delegate);
			logic.run();
			OSProcess.setMemoryArea(outer.delegate);
			memory.delegate.reset(0);

			if (ms instanceof ManagedEventHandler) {
				((ManagedEventHandler) ms).currentMemory = outer;
			} else {
				((ManagedThread) ms).currentMemory = outer;
			}
		}

		@Override
		void executeInArea(Runnable logic, ManagedMemory memory) throws IllegalArgumentException {
			if (logic == null)
				throw new IllegalArgumentException("executeInArea: logic is null");

			if (flag) {
				flag = false;
				Memory currentMem = vm.Memory.getHeapArea();

				OSProcess.setMemoryArea(memory.delegate);
				logic.run();
				OSProcess.setMemoryArea(currentMem);

			} else {
				ManagedMemory outer;

				ManagedSchedulable ms = Services.currentManagedSchedulable();
				if (ms instanceof ManagedEventHandler) {
					outer = ((ManagedEventHandler) ms).getCurrentMemory();
					((ManagedEventHandler) ms).setCurrentMemory(memory);
				} else {
					outer = ((ManagedThread) ms).getCurrentMemory();
					((ManagedThread) ms).setCurrentMemory(memory);
				}

				OSProcess.setMemoryArea(memory.delegate);
				logic.run();
				OSProcess.setMemoryArea(outer.delegate);

				if (ms instanceof ManagedEventHandler) {
					((ManagedEventHandler) ms).setCurrentMemory(outer);
				} else {
					((ManagedThread) ms).setCurrentMemory(outer);
				}

			}
		}

		@Override
		void enterPrivateMemory(int size, Runnable logic) throws IllegalStateException {
			if (logic == null)
				throw exception;

			ManagedSchedulable ms = Services.currentManagedSchedulable();
			devices.Console.println("enterPrivateMemory");
			runEnterPrivateMemoryMulticore(ms, size, logic);
		}
		
		void runEnterPrivateMemoryMulticore(ManagedSchedulable ms, int size, Runnable logic) {
			ManagedMemory prev = getMemory(ms);
			devices.Console.println("enterPrivateMemory: prev " + prev);
			long prevFree = prev.memoryConsumed();

			InnerPrivateMemory inner = new InnerPrivateMemory(size,
					prev.getRemainingBackingstoreSize(), prev, "InnerPrvMem");
			inner.prev = prev;

			ManagedMemory outer;

			if (ms instanceof ManagedEventHandler) {
				outer = ((ManagedEventHandler) ms).getCurrentMemory();
				((ManagedEventHandler) ms).setCurrentMemory(inner);
			} else {
				outer = ((ManagedThread) ms).getCurrentMemory();
				((ManagedThread) ms).setCurrentMemory(inner);
			}

			OSProcess.setMemoryArea(inner.delegate);
			logic.run();
			OSProcess.setMemoryArea(outer.delegate);

			if (prev.memoryConsumed() != prevFree)
				prev.resetArea(prevFree);

			inner.removeArea();

			if (ms instanceof ManagedEventHandler) {
				((ManagedEventHandler) ms).setCurrentMemory(outer);
			} else {
				((ManagedThread) ms).setCurrentMemory(outer);
			}
		}

		@Override
		void executeInAreaOf(Object obj, Runnable logic) {
			if (obj == null || logic == null)
				throw exception;

			ManagedMemory outer;
			ManagedMemory memAreaOfObject = (ManagedMemory) MemoryArea.getMemoryArea(obj);

			ManagedSchedulable ms = Services.currentManagedSchedulable();
			if (ms instanceof ManagedEventHandler) {
				outer = ((ManagedEventHandler) ms).getCurrentMemory();
				((ManagedEventHandler) ms).setCurrentMemory(memAreaOfObject);
			} else {
				outer = ((ManagedThread) ms).getCurrentMemory();
				((ManagedThread) ms).setCurrentMemory(memAreaOfObject);
			}

			OSProcess.setMemoryArea(memAreaOfObject.delegate);
			logic.run();
			OSProcess.setMemoryArea(outer.delegate);

			if (ms instanceof ManagedEventHandler) {
				((ManagedEventHandler) ms).setCurrentMemory(outer);
			} else {
				((ManagedThread) ms).setCurrentMemory(outer);
			}
		}

		@Override
		void executeInOuterArea(Runnable logic) {
			if (logic == null)
				throw exception;

			ManagedSchedulable ms = Services.currentManagedSchedulable();

			ManagedMemory currentMem;
			if (ms instanceof ManagedEventHandler) {
				ManagedEventHandler handler = ((ManagedEventHandler) ms);
				currentMem = handler.getCurrentMemory();
			} else {
				ManagedThread handler = ((ManagedThread) ms);
				currentMem = handler.getCurrentMemory();
			}
			devices.Console.println("executeInOuterArea: currentMem: " + currentMem);

			if (currentMem instanceof ManagedMemory.ImmortalMemory) {
				devices.Console.println("executeInOuterArea: already in ImmortalMemory");
				throw new IllegalStateException("executeInOuterArea: already in ImmortalMemory");
			}

			ManagedMemory outerMemory = getOuterMemory(currentMem);

			if (ms instanceof ManagedEventHandler) {
				((ManagedEventHandler) ms).setCurrentMemory(outerMemory);
			} else {
				((ManagedThread) ms).setCurrentMemory(outerMemory);
			}

			OSProcess.setMemoryArea(outerMemory.delegate);
			logic.run();
			OSProcess.setMemoryArea(currentMem.delegate);

			if (ms instanceof ManagedEventHandler) {
				((ManagedEventHandler) ms).setCurrentMemory(currentMem);
			} else {
				((ManagedThread) ms).setCurrentMemory(currentMem);
			}
		}
		
	}
	
	static final class SinglecoreBehavior extends MemoryBehavior{

		@Override
		void enter(Runnable logic, ManagedMemory memory) throws IllegalArgumentException {
			if (logic == null || !(logic instanceof ManagedSchedulable))
				throw new IllegalArgumentException();

			ManagedSchedulable ms = (ManagedSchedulable) logic;

			if (ms instanceof ManagedEventHandler) {
				ManagedEventHandler mevh = (ManagedEventHandler) ms;
				Memory mem = Memory.switchToArea(mevh.privateMemory.delegate);
				logic.run();
				Memory.switchToArea(mem);
				mevh.privateMemory.delegate.reset(0);
			} else if (ms instanceof ManagedThread) {
				devices.Console.println("ManagedMemory.enter: managedThred should work");
				ManagedThread mth = (ManagedThread) ms;
				Memory mem = Memory.switchToArea(mth.privateMemory.delegate);
				logic.run();
				Memory.switchToArea(mem);
				mth.privateMemory.delegate.reset(0);
			} else {
				// (ms is instanceof ManagedLongEventHandler)
				devices.Console.println("ManagedMemory.enter: UPS ManagedLongEventHandler not implemented");
				//ManagedLongEventHandler mevh = (ManagedLongEventHandler) ms;
				// finish this ...
			}
		}
		
		ScjProcess getCurrentProcess() {
			if (Launcher.level != 0)
				return PriorityScheduler.instance().getCurrentProcess();
			else
				return CyclicScheduler.instance().getCurrentProcess();
		}

		@Override
		void executeInArea(Runnable logic, ManagedMemory memory) throws IllegalArgumentException {
			if (logic == null)
				throw new IllegalArgumentException("executeInArea: logic is null");

			if (flag) {
				flag = false;
				Memory currentMem = vm.Memory.getHeapArea();
				Memory.switchToArea(memory.delegate);
				logic.run();
				Memory.switchToArea(currentMem);
			} else {
				ScjProcess currProcess = getCurrentProcess();
				if (currProcess == null)
					throw new IllegalArgumentException("executeInArea: process is null");

				Memory mem = Memory.switchToArea(memory.delegate);
				logic.run();
				Memory.switchToArea(mem);
			}
		}

		@Override
		void enterPrivateMemory(int size, Runnable logic) throws IllegalStateException {
			/**
			 * prevMemory is the memory area at entry; prevFree is the free pointer
			 * before allocation of the private memory. If the current free has
			 * changed after running the logic, there has been allocation in the
			 * outer area, and the private memory cannot be released.
			 */
			if (logic == null)
				throw exception;

			vm.ClockInterruptHandler.instance.disable(); // atomic operation ??

			ManagedSchedulable ms = getCurrentProcess().getTarget();
			//devices.Console.println("enterPrivateMemory by " + getCurrentProcess().index);
			runEnterPrivateMemory(ms, size, logic);

			vm.ClockInterruptHandler.instance.enable();
		}
		
		 void runEnterPrivateMemory(ManagedSchedulable ms, int size, Runnable logic) {
			ManagedMemory prev = getMemory(ms);
			long prevFree = prev.memoryConsumed();

			InnerPrivateMemory inner = new InnerPrivateMemory(size, prev.getRemainingBackingstoreSize(), prev,
					"InnerPrvMem");
			inner.prev = prev;

			Memory mem = Memory.switchToArea(inner.delegate);
			logic.run();
			Memory.switchToArea(mem);

			if (prev.memoryConsumed() != prevFree)
				prev.resetArea(prevFree);

			inner.removeArea();
		}

		@Override
		void executeInAreaOf(Object obj, Runnable logic) {
			if (obj == null || logic == null)
				throw exception;

			vm.ClockInterruptHandler.instance.disable(); // atomic operation ??

			ManagedMemory memAreaOfObject = (ManagedMemory) MemoryArea.getMemoryArea(obj);
			//devices.Console.println("executeInAreaOf: memAreaOfObject: " + memAreaOfObject);

			Memory mem = Memory.switchToArea(memAreaOfObject.getDelegate());
			logic.run();
			Memory.switchToArea(mem);

			vm.ClockInterruptHandler.instance.enable(); // atomic operation ??
		}

		@Override
		void executeInOuterArea(Runnable logic) {
			if (logic == null)
				throw exception;

			vm.ClockInterruptHandler.instance.disable(); // atomic operation ??

			MemoryArea currentMem = MemoryArea.getCurrentMemoryArea();
			//devices.Console.println("executeInOuterArea: currentMem: " + currentMem);

			if (currentMem instanceof ManagedMemory.ImmortalMemory) {
				devices.Console.println("executeInOuterArea: already in ImmortalMemory");

				vm.ClockInterruptHandler.instance.enable(); // atomic operation ??
				throw new IllegalStateException("executeInOuterArea: already in ImmortalMemory");
			}

			ManagedMemory outerMemory = getOuterMemory(currentMem);

			Memory mem = Memory.switchToArea(outerMemory.getDelegate());
			logic.run();
			Memory.switchToArea(mem);

			vm.ClockInterruptHandler.instance.enable(); // atomic operation ??
		}
		
	}
}
