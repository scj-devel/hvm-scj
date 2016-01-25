package devices.AVR.ATMega2560;

import vm.MachineFactory;
import vm.RealtimeClock;
import vm.SP;
import vm.X86_32SP;

public class ATMega2560MachineFactory implements MachineFactory {

	@Override
	public void initInterrupts() {
		ATMega2560InterruptDispatcher.init();
	}

	@Override
	public SP getProcessSP() {
		/* Should actually use 16 bit stack accessor */
		return new X86_32SP();
	}

	@Override
	public RealtimeClock getRealtimeClock() {
		return new RealtimeClock.DefaultRealtimeClock();
	}

	@Override
	public void startSystemTick() {
		ATMega2560SCJTargetConfiguration.init();
	}
}
