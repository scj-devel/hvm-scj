package javax.safetycritical;

import javax.realtime.MemoryArea;

import vm.Memory;

final class MulticoreMemoryBehavior extends MemoryBehavior {

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

		OSProcess.setMemoryArea(memory.getDelegate());
		logic.run();
		OSProcess.setMemoryArea(outer.getDelegate());
		memory.getDelegate().reset(0);

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

		if (ManagedMemory.flag) {
			ManagedMemory.flag = false;
			Memory currentMem = vm.Memory.getHeapArea();

			OSProcess.setMemoryArea(memory.getDelegate());
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

			OSProcess.setMemoryArea(memory.getDelegate());
			logic.run();
			OSProcess.setMemoryArea(outer.getDelegate());

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
			throw ManagedMemory.exception;

		ManagedSchedulable ms = Services.currentManagedSchedulable();
		devices.Console.println("enterPrivateMemory");
		runEnterPrivateMemoryMulticore(ms, size, logic);
	}
	
	void runEnterPrivateMemoryMulticore(ManagedSchedulable ms, int size, Runnable logic) {
		ManagedMemory prev = ManagedMemory.getMemory(ms);
		// devices.Console.println("enterPrivateMemory: prev " + prev);
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

		OSProcess.setMemoryArea(inner.getDelegate());
		logic.run();
		OSProcess.setMemoryArea(outer.getDelegate());

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
			throw ManagedMemory.exception;

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

		OSProcess.setMemoryArea(memAreaOfObject.getDelegate());
		logic.run();
		OSProcess.setMemoryArea(outer.getDelegate());

		if (ms instanceof ManagedEventHandler) {
			((ManagedEventHandler) ms).setCurrentMemory(outer);
		} else {
			((ManagedThread) ms).setCurrentMemory(outer);
		}
	}

	@Override
	void executeInOuterArea(Runnable logic) {
		if (logic == null)
			throw ManagedMemory.exception;

		ManagedSchedulable ms = Services.currentManagedSchedulable();

		ManagedMemory currentMem;
		if (ms instanceof ManagedEventHandler) {
			ManagedEventHandler handler = ((ManagedEventHandler) ms);
			currentMem = handler.getCurrentMemory();
		} else {
			ManagedThread handler = ((ManagedThread) ms);
			currentMem = handler.getCurrentMemory();
		}
		// devices.Console.println("executeInOuterArea: currentMem: " + currentMem);

		if (currentMem instanceof ImmortalMemory) {
			//devices.Console.println("executeInOuterArea: already in ImmortalMemory");
			throw new IllegalStateException("executeInOuterArea: already in ImmortalMemory");
		}

		ManagedMemory outerMemory = ManagedMemory.getOuterMemory(currentMem);

		if (ms instanceof ManagedEventHandler) {
			((ManagedEventHandler) ms).setCurrentMemory(outerMemory);
		} else {
			((ManagedThread) ms).setCurrentMemory(outerMemory);
		}

		OSProcess.setMemoryArea(outerMemory.getDelegate());
		logic.run();
		OSProcess.setMemoryArea(currentMem.getDelegate());

		if (ms instanceof ManagedEventHandler) {
			((ManagedEventHandler) ms).setCurrentMemory(currentMem);
		} else {
			((ManagedThread) ms).setCurrentMemory(currentMem);
		}
	}

}


