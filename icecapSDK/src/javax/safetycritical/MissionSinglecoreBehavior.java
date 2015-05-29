package javax.safetycritical;

import javax.safetycritical.annotate.Phase;
import javax.scj.util.Const;

class MissionSinglecoreBehavior extends MissionBehavior {

	@Override
	protected Mission getMission() {
		Mission mission = null;

		if (Launcher.level == 0 && CyclicScheduler.instance().seq != null) {
			mission = CyclicScheduler.instance().seq.currMission;
		} else if (Launcher.level > 0 && PriorityScheduler.instance().getCurrentProcess() != null) {

			if (PriorityScheduler.instance().getCurrentProcess().getTarget() instanceof MissionSequencer) {
				mission = ((MissionSequencer<?>) PriorityScheduler.instance().getCurrentProcess().getTarget()).currMission;
			} else {
				mission = ManagedSchedMethods.getMission(PriorityScheduler.instance().getCurrentProcess().getTarget());
			}
		}
		return mission;
	}

	@Override
	protected boolean requestTermination(Mission mission) {
		if (mission.missionTerminate == false) { // called the first time during mission execution	

			// terminate all the sequencer's MSObjects that were created by the mission.

			for (int i = 0; i < mission.msSetForMission.noOfRegistered; i++) {
				if (mission.msSetForMission.managedSchObjects[i] != null) {
					mission.msSetForMission.managedSchObjects[i].signalTermination();
				}
			}

			mission.missionTerminate = true;
			return false;
		} else
			return true; // called more than once: nothing done
	}

	int addNewMission(Mission mission) {
		for (int i = 0; i < missionSet.length; i++) {
			if (missionSet[i] == null) {
				missionSet[i] = mission;
				return i;
			}
		}
		throw new IndexOutOfBoundsException("Mission set: too small");
	}

	@Override
	protected void runInitialize(Mission mission) {
		vm.ClockInterruptHandler.instance.disable();

		if (missionSet == null || isMissionSetInit == false) {
			missionSet = new Mission[Const.DEFAULT_HANDLER_NUMBER];
			mission.isMissionSetInitByThis = true;
			isMissionSetInit = true;
		}
		mission.missionIndex = addNewMission(mission);

		mission.phaseOfMission = Phase.INITIALIZE; // used by JML ??
		mission.msSetForMission = new ManagedSchedulableSet();
		mission.initialize();

		vm.ClockInterruptHandler.instance.enable();
	}

	@Override
	protected void runExecute(Mission mission) {
		vm.ClockInterruptHandler.instance.disable();

		mission.phaseOfMission = Phase.EXECUTE;
		ManagedSchedulableSet msSet = mission.msSetForMission;
		PriorityFrame frame = PriorityScheduler.instance().pFrame;

		int index = mission.missionIndex * 20;

		for (int i = 0; i < msSet.noOfRegistered; i++) {

			ManagedSchedulable ms = msSet.managedSchObjects[i];

			msSet.scjProcesses[i] = ManagedSchedMethods.createScjProcess(ms);
			msSet.scjProcesses[i].setIndex(index);
			index++;
			frame.addProcess(msSet.scjProcesses[i]);
		}

		vm.ClockInterruptHandler.instance.enable();

	}

	@Override
	protected void runCleanup(Mission mission, MissionMemory missMem) {
		mission.phaseOfMission = Phase.CLEANUP;
		// wait until (all handlers in mission have terminated)			
		while (mission.msSetForMission.msCount > 0) {
			vm.RealtimeClock.awaitNextTick();
		}

		vm.ClockInterruptHandler.instance.disable();
		for (int i = 0; i < mission.msSetForMission.noOfRegistered; i++) {
			mission.msSetForMission.scjProcesses[i] = null;
			mission.msSetForMission.managedSchObjects[i] = null;
		}

		missionSet[mission.missionIndex] = null;
		if (mission.isMissionSetInitByThis == true) {
			isMissionSetInit = false;
		}
		mission.cleanUp();
		missMem.resetArea();
		vm.ClockInterruptHandler.instance.enable();
	}

	@Override
	protected Process getProcess(int index) {
		int missionIndex = index / 20;
		int scjProcessIndex = index % 20;
		return missionSet[missionIndex].msSetForMission.scjProcesses[scjProcessIndex];
	}

	@Override
	protected ManagedSchedulable getManageSched(int index) {
		return getProcess(index).msObject;
	}

}
