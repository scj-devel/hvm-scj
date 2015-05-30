package devices;

public abstract class POSIXSCJTargetConfiguration extends POSIXTargetConfiguration {

	@Override
	public String[][] getBuildCommands() {
		StringBuffer command = new StringBuffer(super.getBuildCommands()[0][0]);
		command.append(" native_scj.c x86_64_interrupt.s -l pthread");

		return new String[][] { new String[] { command.toString() } };
	}

	@Override
	public int getJavaHeapSize() {
		return 1024000;
	}

}
