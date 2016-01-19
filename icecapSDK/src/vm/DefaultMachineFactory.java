package vm;

import devices.i86.I86InterruptDispatcher;

public class DefaultMachineFactory implements MachineFactory {
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
}
