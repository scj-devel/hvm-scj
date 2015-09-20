package javax.safetycritical;


public final class LaunchLevel0 extends Launcher {

	public LaunchLevel0(Safelet<?> app) {
		super(app, 0);
		
		Mission.missionBehaviour = new SinglecoreMissionBehavior();
		ManagedEventHandler.handlerBehavior = new SinglecoreHandlerBehavior();
		Services.servicesBehavior = new Services.SinglecoreBehavior();
		ManagedMemory.memoryBehavior = new SinglecoreMemoryBehavior();
		
		createImmortalMemory();
	}

	@Override
	void start() {
		startLevel0();
	}
}
