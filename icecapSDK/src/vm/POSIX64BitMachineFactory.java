package vm;

import devices.i86.I86InterruptDispatcher;

public class POSIX64BitMachineFactory extends AbstractMachineFactory {
	@Override
	public void initInterrupts() {
		I86InterruptDispatcher.init();
	}

	@Override
	public SP getProcessSP() {
		return new X86_64SP();
	}

	@Override
	public RealtimeClock getRealtimeClock() {
		return new RealtimeClock.DefaultRealtimeClock();
	}

	@Override
	public void startSystemTick() {
		start_system_tick();
	}

	@Override
	public void stopSystemTick() {
		stop_system_tick();
	}

	private static native void start_system_tick();
	
	private static native void stop_system_tick();
}
