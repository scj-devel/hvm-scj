package test.same70.configuration;

import devices.AVR.ATMega2560.ATMega2560InterruptDispatcher;
import vm.AbstractMachineFactory;
import vm.RealtimeClock;
import vm.SP;
import vm.X86_32SP;

public class MachineFactorySAME extends AbstractMachineFactory  {

	@Override
	public void initInterrupts() {
		/* Reusing the ATMega2560 interrupt initialization. 
		 * This will not be good enough for a program actually using interrupts.
		 */
		ATMega2560InterruptDispatcher.init(1);
	}

	@Override
	public SP getProcessSP() {
		return new X86_32SP();
	}

	@Override
	public RealtimeClock getRealtimeClock() {
		RealtimeClock clock = new TargetConfigurationSAME.ATSAMe70RealtimeClock(); 
		startSystemTick();
		return clock; 
	}

	@Override
	public void stopSystemTick() {
		// Don't know how to actually stop the system tick again */
		devices.Console.println("MachineFactorySAME.stopSystemTick: not impl");
	}

	@Override
	protected void startMachineSpecificSystemTick() {
		TargetConfigurationSAME.initSystemTick();
	}
}
