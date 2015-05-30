package devices.AVR.ATMega2560;

import vm.AbstractMachineFactory;
import vm.RealtimeClock;
import vm.SP;
import vm.X86_32SP;

public class ATMega2560MachineFactory extends AbstractMachineFactory {

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
		return new ATMega2560SCJTargetConfiguration.ATMega2560RealtimeClock();
	}

	@Override
	public void startSystemTick() {
		ATMega2560SCJTargetConfiguration.init();
	}
	
	@Override
	public void stopSystemTick() {
		ATMega2560SCJTargetConfiguration.deinit();
	}
}
