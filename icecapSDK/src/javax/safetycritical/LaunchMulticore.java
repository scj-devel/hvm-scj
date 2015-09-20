package javax.safetycritical;


public final class LaunchMulticore extends Launcher {

	public LaunchMulticore(Safelet<?> app, int level) {
		super(app, level, true);
		
		Services.servicesBehavior = new Services.MulticoreBehavior();
		Mission.missionBehaviour = new MulticoreMissionBehavior();
		ManagedEventHandler.handlerBehavior = new MulticoreHandlerBehavior();
		ManagedMemory.memoryBehavior = new ManagedMemory.MulticoreBehavior();
		
		createImmortalMemory();
	}

	@Override
	void start() {
		startwithOS();
	}
}
