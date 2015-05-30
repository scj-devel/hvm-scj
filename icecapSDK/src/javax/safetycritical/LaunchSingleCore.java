package javax.safetycritical;

import vm.Machine;
import vm.MachineFactory;
import vm.POSIX64BitMachineFactory;

public abstract class LaunchSingleCore extends Launcher {

	public LaunchSingleCore(Safelet<?> app, int level, MachineFactory mFactory) {
		super(false, mFactory);
		initAndRun(app, level);
	}

	public LaunchSingleCore(Safelet<?> app, int level) {
		this(app, level, new POSIX64BitMachineFactory());
	}

	protected void init() {
		initSingleCoreBehaviour();
	}
	
	protected void startLevel0() {
		MissionSequencer<?> seq = app.getSequencer();
		CyclicScheduler sch = CyclicScheduler.instance();
		
		Machine.setCurrentScheduler(sch);
		
		sch.start(seq, mFactory);
	}
	
	protected void startLevel1_2() {
		// insert idle process before the mission sequencer.
		PriorityScheduler sch = PriorityScheduler.instance();

		Machine.setCurrentScheduler(sch.prioritySchedulerImpl);
		
		sch.insertReadyQueue(ScjProcess.createIdleProcess());
		app.getSequencer();
		PriorityScheduler.instance().start(mFactory);
	}
	
	static void initSingleCoreBehaviour() {
		Mission.missionBehaviour = new SinglecoreMissionBehavior();
		ManagedEventHandler.handlerBehavior = new SinglecoreHandlerBehavior();
		Services.servicesBehavior = new SinglecoreServicesBehavior();
		ManagedMemory.memoryBehavior = new SinglecoreMemoryBehavior();
	}
}
