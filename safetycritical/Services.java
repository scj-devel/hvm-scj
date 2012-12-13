package javax.safetycritical;

import vm.VM;

public class Services {

	public static int getDefaultCeiling() {
		return PriorityScheduler.instance().getMaxPriority();
	}

	public static void setCeiling(Object target, int ceiling) {
		Monitor monitor = new Monitor(ceiling);
		VM.setMonitor(target, monitor);
		monitor.lock();
		monitor.unlock();
	}
}
