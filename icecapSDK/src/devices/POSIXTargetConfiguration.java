package devices;

import java.io.File;

import util.BaseTargetConfiguration;

public abstract class POSIXTargetConfiguration extends BaseTargetConfiguration implements TargetConfiguration {

	@Override
	public String[] getBuildCommands() {
		return new String[] { "gcc -Os -pedantic -Wall -DPC64 -DLAZY_INITIALIZE_CONSTANTS natives_i86.c" };
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
