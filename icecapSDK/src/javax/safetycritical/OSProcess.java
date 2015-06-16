package javax.safetycritical;

import icecaptools.IcecapCompileMe;
import vm.Memory;

@IcecapCompileMe
class OSProcess extends Process {

	public OSProcess(ManagedSchedulable ms) {
		msObject = ms;
		setProcess(ms);

		executable = new MyThread(new Runnable() {
			@Override
			public void run() {
				try {
					runLogic(msObject);
				} catch (Exception e) {
				}
			}
		}, this);
	}

	private void runLogic(ManagedSchedulable ms) {
		ManagedSchedMethodsMulticore.executeManagedSchedulable(ms);
	}

	class ThreadInfo {
		@SuppressWarnings("unused")
		private int priority;
		@SuppressWarnings("unused")
		private int isPeriodic;
		@SuppressWarnings("unused")
		private long start;
		@SuppressWarnings("unused")
		private long period;

		public ThreadInfo(int priority, int isPeriodic, long start, long period) {
			this.priority = priority;
			this.isPeriodic = isPeriodic;
			this.start = start;
			this.period = period;
		}
	}

	class MyThread extends java.lang.Thread {
		OSProcess process;
		ThreadInfo info;
		int startTimer_c = -99;
		int id = -99;
		int[] processors;
		int sizeOfProcessor;

		MyThread(Runnable run, OSProcess process) {
			super(run);
			this.process = process;
			info = setThreadInfo(process.msObject);

			if (process.msObject instanceof ManagedEventHandler) {
				processors = ((ManagedEventHandler) process.msObject).set.processorSet;
			} else {
				processors = ((ManagedThread) process.msObject).set.processorSet;
			}
			
			sizeOfProcessor = processors.length;
		}

		OSProcess getProcess() {
			return process;
		}
	}

	private ThreadInfo setThreadInfo(ManagedSchedulable ms) {
		int priority = -99;
		int isPeriodic = -99;
		long start = -99;
		long period = -99;

		if (ms instanceof ManagedEventHandler) {
			priority = ((ManagedEventHandler) ms).getPriorityParam().getPriority();
			if (ms instanceof PeriodicEventHandler) {
				isPeriodic = 99;
				start = ((PeriodicEventHandler) ms).getStart();
				period = ((PeriodicEventHandler) ms).getPeriod();
			}
			if (ms instanceof OneShotEventHandler) {
				isPeriodic = 98;
				start = ((OneShotEventHandler) ms).getStart();
			}
			if (ms instanceof AperiodicEventHandler) {
				isPeriodic = 97;
			}
		} else if (ms instanceof ManagedThread) {
			priority = ((ManagedThread) ms).getPriorityParam().getPriority();
		}

		else {
			priority = ((ManagedLongEventHandler) ms).getPriorityParam().getPriority();
		}

		return new ThreadInfo(priority, isPeriodic, start, period);
	}

	static native void requestTermination_c(Thread thread);

	static native void testCancel_c();

	static native void setMemoryArea(Memory memory);

	static native Memory getCurrentMemoryArea();

	static native void setOuterMostMissionSequencer(int priority);

	static native void setTimerfd(int timerfd, long start);

	static native void initSpecificID();

	public static native int getThreadID();

	public static native int getCurrentCPUID();

	public static native int getAvailableCPUCount();

	public static native int isProcessorInSet(int processor);

	public static native int getAllCPUCount();

	static native void setOMMSAffinitySet(int level);

	static native void setAffinity(int size, int[] processorSet);
	
	static native int getMaxPriority();
}
