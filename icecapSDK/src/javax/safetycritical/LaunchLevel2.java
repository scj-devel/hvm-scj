package javax.safetycritical;


public final class LaunchLevel2 extends Launcher {

	public LaunchLevel2(Safelet<?> app) {
		super(app, 2);
		
		Mission.missionBehaviour = new Mission.SinglecoreBehavior();
		ManagedEventHandler.handlerBehavior = new ManagedEventHandler.SinglecoreBehavior();
		Services.servicesBehavior = new Services.SinglecoreBehavior();
		ManagedMemory.memoryBehavior = new ManagedMemory.SinglecoreBehavior();
		
		createImmortalMemory();
	}

	@Override
	void start() {
		startLevel1_2();
	}
}
