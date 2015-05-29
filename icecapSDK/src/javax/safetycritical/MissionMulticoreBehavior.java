package javax.safetycritical;

import javax.safetycritical.annotate.Phase;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

class MissionMulticoreBehavior extends MissionBehavior {

	protected MissionMulticoreBehavior() {
		Services.setCeiling(this, Priorities.SEQUENCER_PRIORITY);
	}

	@Override
	protected Mission getMission() {
		Mission m = null;

		ManagedSchedulable ms = Services.currentManagedSchedulable();
		if (ms != null) {
			if (ms instanceof ManagedEventHandler) {
				if (ms instanceof MissionSequencer<?>) {
					m = ((MissionSequencer<?>) ms).currMission;
				} else {
					m = ((ManagedEventHandler) ms).mission;
				}
			} else {
				m = ((ManagedThread) ms).mission;
			}
		}

		return m;
	}

	@Override
	protected boolean requestTermination(Mission mission) {
		if (mission.missionTerminate == false) { // called the first time during mission
			// execution

			// terminate all the sequencer's MSObjects that were created by the
			// mission.
			mission.missionTerminate = true;

			for (int i = 0; i < mission.msSetForMission.noOfRegistered; i++) {
				if (mission.msSetForMission.managedSchObjects[i] != null) {
					if (Launcher.useOS) {
						if (mission.msSetForMission.managedSchObjects[i] instanceof AperiodicEventHandler) {
							((AperiodicEventHandler) mission.msSetForMission.managedSchObjects[i]).fireNextRelease();
						}
						if (mission.msSetForMission.managedSchObjects[i] instanceof OneShotEventHandler) {
							((OneShotEventHandler) mission.msSetForMission.managedSchObjects[i]).deschedule();
							((OneShotEventHandler) mission.msSetForMission.managedSchObjects[i]).fireNextRelease();
						}
					}
					mission.msSetForMission.managedSchObjects[i].signalTermination();
				}
			}

			return false;
		} else
			return true; // called more than once: nothing done
	}

	synchronized int addNewMission(Mission mission) {
		if (missionSet == null || isMissionSetInit == false) {
			missionSet = new Mission[Const.DEFAULT_HANDLER_NUMBER];
			isMissionSetInit = true;
			mission.isMissionSetInitByThis = true;
		}

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
		mission.phaseOfMission = Phase.INITIALIZE;
		mission.missionIndex = addNewMission(mission);
		mission.msSetForMission = new ManagedSchedulableSet();
		mission.initialize();
	}

	@Override
	protected void runExecute(Mission mission) {
		mission.phaseOfMission = Phase.EXECUTE;
		ManagedSchedulableSet msSet = mission.msSetForMission;

		int index = mission.missionIndex * Const.DEFAULT_HANDLER_NUMBER;

		for (int i = 0; i < msSet.noOfRegistered; i++) {
			ManagedSchedulable ms = msSet.managedSchObjects[i];
			OSProcess process = new OSProcess(ms);
			process.executable.id = index;
			index++;
			mission.msSetForMission.activeCount++;
			process.executable.start();
		}

		mission.currMissSeq.seqWait();

		for (int i = 0; i < mission.msSetForMission.noOfRegistered; i++) {
			try {
				if (mission.msSetForMission.managedSchObjects[i] instanceof ManagedThread)
					((ManagedThread) mission.msSetForMission.managedSchObjects[i]).process.executable.join();
				else
					((ManagedEventHandler) mission.msSetForMission.managedSchObjects[i]).process.executable.join();
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	protected void runCleanup(Mission mission, MissionMemory missMem) {
		mission.phaseOfMission = Phase.CLEANUP;

		if (mission.msSetForMission.activeCount > 0) {
			devices.Console.println("still have SOs");
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < mission.msSetForMission.noOfRegistered; i++) {
			mission.msSetForMission.managedSchObjects[i].cleanUp();
			mission.msSetForMission.managedSchObjects[i] = null;
			mission.msSetForMission.msCount--;
		}

		missionSet[mission.missionIndex] = null;
		if (mission.isMissionSetInitByThis == true) {
			isMissionSetInit = false;
		}

		mission.cleanUp();
		missMem.resetArea();
	}

	@Override
	protected Process getProcess(int index) {
		ManagedSchedulable ms = getManageSched(index);

		if (ms instanceof ManagedEventHandler)
			return ((ManagedEventHandler) ms).process;
		else
			return ((ManagedThread) ms).process;

	}

	@Override
	protected ManagedSchedulable getManageSched(int index) {
		if (index == -99)
			return null;
		if (index == -11)
			return MissionSequencer.outerMostSeq;

		int missionIndex = index / 20;
		int managedSchdeulableIndex = index % 20;
		return missionSet[missionIndex].msSetForMission.managedSchObjects[managedSchdeulableIndex];
	}
}
