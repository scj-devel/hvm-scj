package vm;

public abstract class AbstractMachineFactory implements MachineFactory {

	private boolean systemTickStarted;
	
	
	
	public AbstractMachineFactory()
	{
		Machine.setMachineFactory(this);
		systemTickStarted = false;
	}

	@Override
	public void startSystemTick() {
		if (!systemTickStarted){
			systemTickStarted = true;
			startMachineSpecificSystemTick();
		}		
	}

	protected abstract void startMachineSpecificSystemTick();
}
