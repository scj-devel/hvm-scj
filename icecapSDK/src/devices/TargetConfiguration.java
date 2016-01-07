package devices;

public interface TargetConfiguration {

	/* The folder into which the C source has been generated */
	String getOutputFolder();
	
	/* The command(s) required to build the C source to produce an
	 * executable for the target.
	 * 
	 * The command to invoke the C compiler must be the first element in the array. 
	 * It should only contain target specific filenames. Files generated by the
	 * icecap tool are added automatically.
	 * 
	 * Use $EXE$ to refer to the executable name. 
	 * 
	 */
	String[] getBuildCommands();
		
	/* Returns the command for deploying the generated executable to the target.
	 * 
	 * Use $EXE$ to refer to the executable name. 
	 */
	String getDeployCommand();
	
	/* Return the number of bytes that should be allocated for the
	 * Java heap at runtime.
	 */
	int getJavaHeapSize();
}
