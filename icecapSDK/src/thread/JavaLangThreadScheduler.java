package thread;

import vm.Monitor;
import vm.Process;
import vm.Scheduler;

public class JavaLangThreadScheduler implements Scheduler {

	private static class JavaLangThreadMonitor extends vm.Monitor {
		private int mutex;
		private int conditionVariable;
		private int ceiling;

		JavaLangThreadMonitor() {
			ceiling = -99;
			initializeMutex(-99);
		}
		
		JavaLangThreadMonitor(int ceiling) {
			if(ceiling <= 0)
				throw new IllegalArgumentException();
			
			this.ceiling = ceiling;
			initializeMutex(ceiling);
		}

		private native void initializeMutex(int ceiling);

		@Override
		public void lock() {
			if (mutex != 0) {
				acquireMutex();
			}
		}

		private native void acquireMutex();

		@Override
		public void unlock() {
			if (mutex != 0) {
				releaseMutex();
			}
		}

		private native void releaseMutex();

	}

	@Override
	public Process getNextProcess() {
		return null;
	}

	@Override
	public void wait(Object target) {
		waitOnCondition(target);
	}

	private static native void waitOnCondition(Object target);

	@Override
	public void notify(Object target) {
		notifyOnCondition(target);
	}
	
	@Override
	public void notifyAll(Object target)
	{
		
	}
	
	private static native void notifyOnCondition(Object target);

	@Override
	public Monitor getDefaultMonitor() {
		return new JavaLangThreadMonitor();
	}
	
	protected static Monitor getSCJMultiprocessorMonitor(int ceiling){
			return new JavaLangThreadMonitor(ceiling);
	}
}
