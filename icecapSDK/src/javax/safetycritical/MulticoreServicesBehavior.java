package javax.safetycritical;

final class MulticoreServicesBehavior extends ServicesBehavior {
	
	static ManagedSchedulable currentManagedSchedulable() {
		return OSProcess.getCurrentMSObject();
	}

	@Override
	int getDefaultCeiling() {
		return OSProcess.getMaxPriority();
	}

	@Override
	void setCeiling(Object target, int ceiling) {
		vm.Monitor monitor = MultiprocessorHelpingScheduler.getMultiprocessorMonitor(ceiling);
		monitor.attach(target);
	}

	@Override
	String getNameOfCurrentMemoryArea() {
		return OSProcess.getCurrentMemoryArea().getName();
	}
}


