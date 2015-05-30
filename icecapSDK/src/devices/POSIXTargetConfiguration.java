package devices;

import java.io.File;

import util.BaseTargetConfiguration;
import vm.POSIX64BitMachineFactory;
import vm.Machine;
import vm.MachineFactory;

public abstract class POSIXTargetConfiguration extends BaseTargetConfiguration implements TargetConfiguration {

	private static MachineFactory mFac;

	static {

		Machine.setMachineFactory(factoryInstance());
		Console.writer = new DefaultWriter();
	}

	@Override
	public String[][] getBuildCommands() {
		return new String[][] { new String[] {
				"gcc -Os -pedantic -Wall -DJAVA_STACK_SIZE=2048 -DPC64 -DLAZY_INITIALIZE_CONSTANTS natives_i86.c" } };
	}

	private static MachineFactory factoryInstance() {
		if (mFac == null) {
			mFac = new POSIX64BitMachineFactory();
		}
		return mFac;
	}

	@Override
	public int getJavaHeapSize() {
		return 512000;
	}

	@Override
	public abstract String getOutputFolder();

	@Override
	public String getDeployCommand() {
		return getOutputFolder() + File.separator + "main.exe";
	}

	protected static MachineFactory getConfiguration() {
		return factoryInstance();
	}
}
