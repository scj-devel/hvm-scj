package javax.safetycritical;


public final class LaunchMulticore extends Launcher {

	public LaunchMulticore(Safelet<?> app, int level) {
		super(app, level, true);
		
		Mission.missionBehaviour = new Mission.MulticoreBehavior();
		ManagedEventHandler.handlerBehavior = new ManagedEventHandler.MulticoreBehavior();
		Services.servicesBehavior = new Services.MulticoreBehavior();
		ManagedMemory.memoryBehavior = new ManagedMemory.MulticoreBehavior();
		
		createImmortalMemory();
	}

	@Override
	void start() {
		startwithOS();
	}
}
