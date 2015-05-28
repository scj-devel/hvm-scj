package javax.safetycritical;

import javax.safetycritical.OSProcess.MyThread;

public class Process {
	
	ManagedSchedulable msObject;
	int state;
	int index = -999; // The index of the ScjProcesses; used by
	// PriorityScheduler; -999 is 'no index set'
	
	MyThread executable; // only for multiprocessor
	
	protected void setProcess(ManagedSchedulable ms) {
		if (ms instanceof ManagedEventHandler)
			((ManagedEventHandler) ms).process = this;
		else if (ms instanceof ManagedThread)
			((ManagedThread) ms).process = this;
		else
			((ManagedLongEventHandler) ms).process = this;
	}
}
