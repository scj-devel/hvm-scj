package javax.safetycritical;

final class SinglecoreServicesBehavior extends ServicesBehavior {

	@Override
	int getDefaultCeiling() {
		return PriorityScheduler.instance().getMaxPriority();
	}

	@Override
	void setCeiling(Object target, int ceiling) {
		Monitor monitor = new Monitor(ceiling);
		monitor.attach(target);
		//devices.Console.println("Services.setCeiling");
	}

	@Override
	String getNameOfCurrentMemoryArea() {
		return vm.Memory.getCurrentMemoryArea().getName();
	}


}
