package javax.safetycritical;

import thread.JavaLangThreadScheduler;
import vm.Monitor;

class MultiprocessorHelpingScheduler extends JavaLangThreadScheduler{
	
	@Override
	public Monitor getDefaultMonitor() {
		//devices.Console.println("default: " + Services.getDefaultCeiling());
		return getSCJMultiprocessorMonitor(Services.getDefaultCeiling());
	}
	
	protected static Monitor getMultiprocessorMonitor(int ceiling){
		return getSCJMultiprocessorMonitor(ceiling);
	}

}
