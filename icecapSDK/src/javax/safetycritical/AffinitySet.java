package javax.safetycritical;

import javax.safetycritical.annotate.SCJAllowed;

@SCJAllowed
public final class AffinitySet {
	static AffinitySet[] AFFINITY_SET;
	int[] processorSet;

	AffinitySet(int processorNumber) {
		this.processorSet = new int[1];
		this.processorSet[0] = processorNumber;
	}

	AffinitySet(int[] processors) {
		this.processorSet = processors;
	}

	public static AffinitySet generate(int processorNumber) {
		if (Launcher.level != 2) {
			throw new IllegalArgumentException();
		}

		boolean isProcessorInSet = false;
		for (int i = 0; i < AffinitySet.AFFINITY_SET[0].processorSet.length; i++) {
			if (AffinitySet.AFFINITY_SET[0].processorSet[i] == processorNumber) {
				isProcessorInSet = true;
			}
		}

		if (isProcessorInSet)
			return new AffinitySet(processorNumber);
		else
			throw new IllegalArgumentException();
	}

	public static final AffinitySet getAffinitySet(ManagedSchedulable sched) {
		AffinitySet set = null;
		if (sched instanceof ManagedEventHandler) {
			set = ((ManagedEventHandler) sched).set;
		} else {
			set = ((ManagedThread) sched).set;
		}
		return set;
	}

	public static final void setProcessorAffinity(AffinitySet set, ManagedSchedulable sched) {
		if (Launcher.level < 1)
			throw new IllegalArgumentException();

		if (sched instanceof ManagedEventHandler) {
			ManagedEventHandler handler = (ManagedEventHandler) sched;
			handler.set = set;
		} else {
			ManagedThread thread = (ManagedThread) sched;
			thread.set = set;
		}
	}

	public final boolean isProcessorInSet(int processorNumber) {
		for (int i = 0; i < processorSet.length; i++) {
			if (processorSet[i] == processorNumber) {
				return true;
			}
		}
		return false;
	}

	protected static void initAffinitySet_Level0() {
		OSProcess.setOMMSAffinitySet(0);
		AFFINITY_SET = new AffinitySet[1];
		AFFINITY_SET[0] = new AffinitySet(OSProcess.getCurrentCPUID());
	}

	protected static void initAffinitySet_Level1() {
		AFFINITY_SET = new AffinitySet[OSProcess.getAvailableCPUCount()];

		int count = 0;
		int processorNumber = OSProcess.getAllCPUCount();

		for (int i = 0; i < processorNumber; i++) {
			if (OSProcess.isProcessorInSet(i) != 0) {
				AFFINITY_SET[count] = new AffinitySet(i);
				count++;
			}
		}
		OSProcess.setOMMSAffinitySet(1);
	}

	protected static void initAffinitySet_Level2() {
		OSProcess.setOMMSAffinitySet(2);
		AFFINITY_SET = new AffinitySet[OSProcess.getAvailableCPUCount() + 1];

		int[] processors = new int[OSProcess.getAvailableCPUCount()];
		int count = 0;
		int processorNumber = OSProcess.getAllCPUCount();

		for (int i = 0; i < processorNumber; i++) {
			if (OSProcess.isProcessorInSet(i) != 0) {
				processors[count] = i;
				count++;
			}
		}

		AFFINITY_SET[0] = new AffinitySet(processors);
	}

	protected static void checkAndInitAffinityByCustomized(int[][] processor) {
		switch (Launcher.level) {
		case 0:
			if (processor.length == 1 && processor[0].length == 1
					&& OSProcess.isProcessorInSet(processor[0][0]) != 0) {
				AFFINITY_SET = new AffinitySet[1];
				AFFINITY_SET[0] = new AffinitySet(processor[0]);
				OSProcess.setAffinity(processor[0].length, processor[0]);
			} else {
				devices.Console
						.println("Notice: Level0 can only has one affinity set with one valid processor");
				throw new IllegalArgumentException();
			}
			break;
		case 1:
			if (processor.length > 0 && processor.length <= OSProcess.getAvailableCPUCount()) {
				for (int i = 0; i < processor.length; i++) {
					if (processor[i].length == 1
							&& OSProcess.isProcessorInSet(processor[i][0]) != 0) {
						continue;
					} else {
						devices.Console.println("invaild processor id found");
						throw new IllegalArgumentException();
					}
				}

				AFFINITY_SET = new AffinitySet[processor.length];
				for (int i = 0; i < AffinitySet.AFFINITY_SET.length; i++) {
					AFFINITY_SET[i] = new AffinitySet(processor[i]);
				}
				OSProcess.setAffinity(AFFINITY_SET[0].processorSet.length,
						AFFINITY_SET[0].processorSet);
			} else {
				devices.Console
						.println("Notice: In Level1 the number of affinity sets should be greater"
								+ " than 0 but not greater than the number of available processors.");
				throw new IllegalArgumentException();
			}
			break;
		case 2:
			if (processor.length <= 0) {
				devices.Console
						.println("Notice: In level2 the number of affinity sets should be greater than 0");
				throw new IllegalArgumentException();
			} else {
				for (int i = 0; i < processor.length; i++) {
					if (processor[i].length <= 0
							|| processor[i].length > OSProcess.getAvailableCPUCount()) {
						devices.Console
								.println("Notice: In level2 the number of processors in a affinity "
										+ "set should greater than 0 but not greater than the number of"
										+ " available processors");
						throw new IllegalArgumentException();
					}

					for (int j = 0; j < processor[i].length; j++) {
						if (OSProcess.isProcessorInSet(processor[i][j]) != 0) {
							continue;
						} else {
							devices.Console.println("invaild processor id found");
							throw new IllegalArgumentException();
						}
					}
				}

				AFFINITY_SET = new AffinitySet[processor.length];
				for (int i = 0; i < AFFINITY_SET.length; i++) {
					AFFINITY_SET[i] = new AffinitySet(processor[i]);
				}

				OSProcess.setAffinity(AFFINITY_SET[0].processorSet.length,
						AFFINITY_SET[0].processorSet);
			}
			break;
		}

	}

	public static void printAffinitySets() {
		devices.Console.println("-----------Affinity Sets---------");
		for (int i = 0; i < AffinitySet.AFFINITY_SET.length; i++) {
			if (AffinitySet.AFFINITY_SET[i] != null) {
				for (int j = 0; j < AffinitySet.AFFINITY_SET[i].processorSet.length; j++) {
					devices.Console.print(AffinitySet.AFFINITY_SET[i].processorSet[j] + "	");
				}
				devices.Console.print("\n");
			} else
				continue;
		}
		devices.Console.println("----------------------------------");
	}

	public void formatPrint(String s) {
		devices.Console.println(s + "*********");
		devices.Console.print(s);
		for (int j = 0; j < processorSet.length; j++) {
			devices.Console.print(processorSet[j] + "	");
		}
		devices.Console.print("\n");
		devices.Console.println(s + "*********");
	}

	public int[] getProcessorSet() {
		return this.processorSet;
	}
}
