package vm;

import icecaptools.IcecapCompileMe;

public abstract class Monitor {

	public abstract void lock();

	public abstract void unlock();

	protected Monitor() {
	}

	public void attach(Object target) {
		attachMonitor(target);
		lock(this);
		unlock(this);
	}

	private native void attachMonitor(Object target);

	/* Method below is called by the VM to enter a monitor */
	@IcecapCompileMe
	private static void lock(Monitor monitor) {
		monitor.lock();
	}

	/* Method below is called be the VM to exit a monitor */
	@IcecapCompileMe
	private static void unlock(Monitor monitor) {
		monitor.unlock();
	}

	// SEK: not in SCJ
	@IcecapCompileMe
	public static void wait(Object target) {
		ClockInterruptHandler clock = vm.ClockInterruptHandler.instance;
		clock.disable();
		Scheduler sch = ClockInterruptHandler.getCurrentScheduler();
		sch.wait(target);
		clock.enable();
		ClockInterruptHandler.instance.yield();
	}

	// SEK: not in SCJ
	@IcecapCompileMe
	public static void notify(Object target) {
		ClockInterruptHandler clock = vm.ClockInterruptHandler.instance;
		clock.disable();
		Scheduler sch = ClockInterruptHandler.getCurrentScheduler();
		sch.notify(target);
		clock.enable();
	}
}
