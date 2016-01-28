package vm;

public abstract class AbstractMachineFactory implements MachineFactory {

	public AbstractMachineFactory()
	{
		Machine.setMachineFactory(this);
	}
}
