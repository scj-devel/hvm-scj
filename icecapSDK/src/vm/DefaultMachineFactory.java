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

	@Override
	public void startSystemTick() {
		/* System tick must be started elsewhere */ 
		
		/* The right thing would be to start it here, or in a subclass, but
		 * this has not been implemented yet.
		 */
	}
}
