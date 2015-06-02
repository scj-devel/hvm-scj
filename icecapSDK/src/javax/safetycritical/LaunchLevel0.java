package javax.safetycritical;


public final class LaunchLevel0 extends Launcher {

	public LaunchLevel0(Safelet<?> app) {
		super(app, 0);
		
		Mission.missionBehaviour = new Mission.SinglecoreBehavior();
		ManagedEventHandler.handlerBehavior = new ManagedEventHandler.SinglecoreBehavior();
		Services.servicesBehavior = new Services.SinglecoreBehavior();
		ManagedMemory.memoryBehavior = new ManagedMemory.SinglecoreBehavior();
		
		createImmortalMemory();
	}

	@Override
	void start() {
		startLevel0();
	}
}
