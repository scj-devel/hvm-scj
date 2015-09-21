package javax.safetycritical;

import javax.realtime.MemoryArea;

import vm.Memory;

final class SinglecoreMemoryBehavior extends MemoryBehavior {

	@Override
	void enter(Runnable logic, ManagedMemory memory) throws IllegalArgumentException {
		if (logic == null || !(logic instanceof ManagedSchedulable))
			throw new IllegalArgumentException();

		ManagedSchedulable ms = (ManagedSchedulable) logic;

		if (ms instanceof ManagedEventHandler) {
			ManagedEventHandler mevh = (ManagedEventHandler) ms;
			Memory mem = Memory.switchToArea(mevh.privateMemory.getDelegate());
			logic.run();
			Memory.switchToArea(mem);
			mevh.privateMemory.getDelegate().reset(0);
		} else if (ms instanceof ManagedThread) {
			devices.Console.println("ManagedMemory.enter: managedThred should work");
			ManagedThread mth = (ManagedThread) ms;
			Memory mem = Memory.switchToArea(mth.privateMemory.getDelegate());
			logic.run();
			Memory.switchToArea(mem);
			mth.privateMemory.getDelegate().reset(0);
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

		if (ManagedMemory.flag) {
			ManagedMemory.flag = false;
			Memory currentMem = vm.Memory.getHeapArea();
			Memory.switchToArea(memory.getDelegate());
			logic.run();
			Memory.switchToArea(currentMem);
		} else {
			ScjProcess currProcess = getCurrentProcess();
			if (currProcess == null)
				throw new IllegalArgumentException("executeInArea: process is null");

			Memory mem = Memory.switchToArea(memory.getDelegate());
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
			throw ManagedMemory.exception;

		vm.ClockInterruptHandler.instance.disable(); // atomic operation ??

		ManagedSchedulable ms = getCurrentProcess().getTarget();
		//devices.Console.println("enterPrivateMemory by " + getCurrentProcess().index);
		runEnterPrivateMemory(ms, size, logic);

		vm.ClockInterruptHandler.instance.enable();
	}
	
	 void runEnterPrivateMemory(ManagedSchedulable ms, int size, Runnable logic) {
		ManagedMemory prev = ManagedMemory.getMemory(ms);
		long prevFree = prev.memoryConsumed();

		InnerPrivateMemory inner = new InnerPrivateMemory(size, prev.getRemainingBackingstoreSize(), prev,
				"InnerPrvMem");
		inner.prev = prev;

		Memory mem = Memory.switchToArea(inner.getDelegate());
		logic.run();
		Memory.switchToArea(mem);

		if (prev.memoryConsumed() != prevFree)
			prev.resetArea(prevFree);

		inner.removeArea();
	}

	@Override
	void executeInAreaOf(Object obj, Runnable logic) {
		if (obj == null || logic == null)
			throw ManagedMemory.exception;

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
			throw ManagedMemory.exception;

		vm.ClockInterruptHandler.instance.disable(); // atomic operation ??

		//MemoryArea currentMem = MemoryArea.getCurrentMemoryArea();
		MemoryArea currentMem = ManagedMemory.getCurrentAllocationArea();
		//devices.Console.println("executeInOuterArea: currentMem: " + currentMem);

		if (currentMem instanceof ImmortalMemory) {
			devices.Console.println("executeInOuterArea: already in ImmortalMemory");

			vm.ClockInterruptHandler.instance.enable(); // atomic operation ??
			throw new IllegalStateException("executeInOuterArea: already in ImmortalMemory");
		}

		ManagedMemory outerMemory = ManagedMemory.getOuterMemory(currentMem);

		Memory mem = Memory.switchToArea(outerMemory.getDelegate());
		logic.run();
		Memory.switchToArea(mem);

		vm.ClockInterruptHandler.instance.enable(); // atomic operation ??
	}
	
}



