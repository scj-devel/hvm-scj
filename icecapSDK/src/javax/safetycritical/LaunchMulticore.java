package javax.safetycritical;

import javax.scj.util.Configuration;

import vm.Machine;
import vm.MachineFactory;
import vm.POSIX64BitMachineFactory;

public final class LaunchMulticore extends Launcher {

	public LaunchMulticore(Safelet<?> app, int level) {
		this(app, level, new POSIX64BitMachineFactory());
	}

	public LaunchMulticore(Safelet<?> app, int level, MachineFactory mFactory) {
		super(true, mFactory);
		initAndRun(app, level);
	}
	
	protected void init() {
		Services.servicesBehavior = new MulticoreServicesBehavior();
		Mission.missionBehaviour = new MulticoreMissionBehavior();
		ManagedEventHandler.handlerBehavior = new MulticoreHandlerBehavior();
		ManagedMemory.memoryBehavior = new MulticoreMemoryBehavior();
	}
	
	@Override
	protected void start() {
		initAffinfitySetsMulticore();

		Machine.setCurrentScheduler(new MultiprocessorHelpingScheduler());
		
		OSProcess.initSpecificID();
		MissionSequencer<?> outerMostMS = app.getSequencer();
		outerMostMS.privateMemory.enter(outerMostMS);
		outerMostMS.cleanUp();
	}
	
	private void initAffinfitySetsMulticore() {
		if (Configuration.processors != null) {
			AffinitySet.checkAndInitAffinityByCustomized(Configuration.processors);
		} else {
			switch (Launcher.level) {
			case 0:
				AffinitySet.initAffinitySet_Level0();
				break;
			case 1:
				AffinitySet.initAffinitySet_Level1();
				break;
			case 2:
				AffinitySet.initAffinitySet_Level2();
				break;
			}
		}
	}
}
