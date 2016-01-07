package devices;

import java.io.File;

public abstract class POSIXTargetConfiguration implements TargetConfiguration {

	@Override
	public String[] getBuildCommands() {
		return new String[] { "gcc -O0 -g -pedantic -Wall -DPC64 natives_i86.c" };
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
}
