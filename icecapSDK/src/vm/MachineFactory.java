package vm;

public interface MachineFactory {

	public void initInterrupts();

	public SP getProcessSP();

	RealtimeClock getRealtimeClock();

	public void startSystemTick();

	public void stopSystemTick();
}
