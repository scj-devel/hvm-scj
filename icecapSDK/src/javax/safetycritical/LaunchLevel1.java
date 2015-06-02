package javax.safetycritical;


public final class LaunchLevel1 extends Launcher {

	public LaunchLevel1(Safelet<?> app) {
		super(app, 1);
		
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
