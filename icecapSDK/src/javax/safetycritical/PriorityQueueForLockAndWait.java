package javax.safetycritical;

/**
 * This class is a two dimension set, which contains pairs of clock and process. 
 * The infrastructure will use this class to model the wait set and lock set.
 */
class PriorityQueueForLockAndWait {
	protected int heapSize;
	
	protected ScjProcess[] tree;
	
	int tail;

	PriorityQueueForLockAndWait(int size) {
		heapSize = 0;
		tree = new ScjProcess[size];
		makeEmptyTree(tree);
		tail = -1;
	}

	private void makeEmptyTree(ScjProcess[] set) {
		for (int i = 0; i < set.length; i++)
			set[i] = null;
	}

	/**
	 * Add a relation between the lock and the process into the set.
	 * 
	 * @param target
	 *            The lock associate with the process
	 * @param process
	 *            The process
	 */
	protected void addProcess(Object monitor, ScjProcess process) {
		vm.ClockInterruptHandler.instance.disable();

		if (tail < tree.length - 1) {
			tail++;
			int index = tail;
			// find the place in the set for this process
			for (int i = 0; i < tail; i++) {
				ScjProcess temp = tree[i];
				if (temp == null)
					throw new IllegalArgumentException("1");

				if (ManagedSchedMethods.getPriorityParameter(process.getTarget()).getPriority() > ManagedSchedMethods
						.getPriorityParameter(temp.getTarget()).getPriority()) {
					index = i;
					break;
				}
			}
			// reserve the place in the set for this process
			if (index != tail) {
				for (int i = tail; i > index; i--) {
					tree[i] = tree[i - 1];
				}
			}
			// add the index of the process into the set and set the required lock
			process.monitorLock = monitor;
			tree[index] = process;
			heapSize++;
		} else {
			throw new IndexOutOfBoundsException("set: too small");
		}
		//print();
		vm.ClockInterruptHandler.instance.enable();
	}

	/**
	 * Get the first process who needs to lock. 
	 * Rules: 
	 * 1. The highest priority process who needs the lock will be returned. 
	 * 2. If there are more than one processes who have the same priority, then FIFO.
	 * 
	 * @param target
	 *            The lock
	 * @return The process who needs to lock.
	 */
	protected/*synchronized*/ScjProcess getNextProcess(Object monitor) {
		for (int i = 0; i <= tail; i++) {
			ScjProcess process = tree[i];
			if (process.monitorLock == monitor) {
				process.monitorLock = null;
				reorderSet(i);
				heapSize--;
				return process;
			}
		}
		return null;
	}

	public/*synchronized*/void removeProcess(ScjProcess process) {
		for (int i = 0; i <= tail; i++) {
			if (tree[i] == process) {
				reorderSet(i);
				process.monitorLock = null;

				heapSize--;
			}
		}
	}

	private void reorderSet(int index) {
		for (int i = index; i <= tail - 1; i++) {
			tree[i] = tree[i + 1];
		}
		tree[tail] = null;
		tail--;
	}

	/**
	 * For testing purpose.
	 */
	public void print() {
		devices.Console.println("queue size = " + (tail + 1));
		//		for (int i = 0; i <= tail; i++) {
		//			ScjProcess temp = getScjProcess(queue[i]);
		//			if (temp != null)
		//				devices.Console.println(temp.print());
		//		}

		for (int i = 0; i < tree.length; i++) {
			devices.Console.print("[ " + tree[i] + " ] ");
		}
		devices.Console.println("");
	}
}