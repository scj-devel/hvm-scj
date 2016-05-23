package devices;

public abstract class POSIXSCJTargetConfiguration extends POSIXTargetConfiguration {

	@Override
	public String[][] getBuildCommands() {
		String[][] defaultCommands = super.getBuildCommands();
		String[] compileCommand = defaultCommands[0];
		String[] newCompileCommand = new String[compileCommand.length + 3];
		
		int inx;
		for (inx = 0; inx < compileCommand.length; inx++)
		{
			newCompileCommand[inx] = compileCommand[inx];
		}
			
		newCompileCommand[inx++] = "x86_64_interrupt.s";
		newCompileCommand[inx++] = "-l";
		newCompileCommand[inx++] = "pthread";

		defaultCommands[0] = newCompileCommand;
		return defaultCommands;
	}

	@Override
	public int getJavaHeapSize() {
		return 1024000;
	}

}
