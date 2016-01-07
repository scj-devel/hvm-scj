package devices;

public interface TargetConfiguration {

	/* The folder into which the C source has been generated */
	String getOutputFolder();
	
	/* The command(s) required to build the C source to produce an
	 * executable for the target.
	 * 
	 * The command to invoke the C compiler must be first. It should not contain
	 * any filenames. Files to compile will be added automatically.
	 * 
	 */
	String[] getBuildCommands();
	
	/* Return the number of bytes that should be allocated for the
	 * Java heap at runtime.
	 */
	int getJavaHeapSize();
}
