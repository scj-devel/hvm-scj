package javax.safetycritical;


public final class LaunchLevel2 extends Launcher {

	public LaunchLevel2(Safelet<?> app) {
		super(app, 2);
		
		Mission.missionBehaviour = new SinglecoreMissionBehavior();
		ManagedEventHandler.handlerBehavior = new SinglecoreHandlerBehavior();
		Services.servicesBehavior = new SinglecoreServicesBehavior();
		ManagedMemory.memoryBehavior = new SinglecoreMemoryBehavior();
		
		createImmortalMemory();
	}

	@Override
	void start() {
		startLevel1_2();
	}
}
