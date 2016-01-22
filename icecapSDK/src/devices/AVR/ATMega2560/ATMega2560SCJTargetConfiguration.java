package devices.AVR.ATMega2560;

public abstract class ATMega2560SCJTargetConfiguration extends ATMega2560TargetConfiguration {

	@Override
	public String[] getBuildCommands() {
		String[] buildCommands = super.getBuildCommands();
		StringBuffer gcccommand = new StringBuffer(buildCommands[0]);
		gcccommand.append(" -DLAZY_INITIALIZE_CONSTANTS atmega2560_interrupt.s ");
		buildCommands[0] = gcccommand.toString();
		return buildCommands;
	}
	
	@Override
	public int getJavaHeapSize() {
		return 7100;
	}

}
