package javax.safetycritical;

import javax.safetycritical.annotate.Phase;
import javax.scj.util.Const;


final class MulticoreMissionBehavior extends MissionBehavior {

	MulticoreMissionBehavior() {
		Services.setCeiling(this, Services.getDefaultCeiling());
	}

	@Override
	Mission getMission() {
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
	boolean requestTermination(Mission mission) {
		if (mission.missionTerminate == false) { // called the first time during mission
			// execution

			// terminate all the sequencer's MSObjects that were created by the
			// mission.
			mission.missionTerminate = true;

			for (int i = 0; i < mission.getNumberOfManagedSchedulables(); i++) {
				ManagedSchedulable schedulable = mission.getManagedSchedulable(i);
				if (schedulable != null) {
					if (schedulable instanceof AperiodicEventHandler) {
						((AperiodicEventHandler) schedulable).fireNextRelease();
					}
					if (schedulable instanceof OneShotEventHandler) {
						((OneShotEventHandler) schedulable).deschedule();
						((OneShotEventHandler) schedulable).fireNextRelease();
					}
					schedulable.signalTermination();
				}
			}

			return false;
		} else
			return true; // called more than once: nothing done
	}

	synchronized int addNewMission(Mission mission) {
		if (Mission.missionSet == null || Mission.isMissionSetInit == false) {
			Mission.missionSet = new Mission[Const.DEFAULT_HANDLER_NUMBER];
			Mission.isMissionSetInit = true;
			mission.isMissionSetInitByThis = true;
		}

		for (int i = 0; i < Mission.missionSet.length; i++) {
			if (Mission.missionSet[i] == null) {
				Mission.missionSet[i] = mission;
				return i;
			}
		}
		throw new IndexOutOfBoundsException("Mission set: too small");
	}

	@Override
	void runInitialize(Mission mission) {
		mission.phaseOfMission = Phase.INITIALIZATION;
		mission.missionIndex = addNewMission(mission);
		mission.msSetForMission = new ManagedSchedulableSet();
		mission.initialize();
	}

	@Override
	void runExecute(Mission mission) {
		mission.phaseOfMission = Phase.RUN;
		int index = mission.missionIndex * Const.DEFAULT_HANDLER_NUMBER;

		for (int i = 0; i < mission.getNumberOfManagedSchedulables(); i++) {
			ManagedSchedulable ms = mission.getManagedSchedulable(i);
			OSProcess process = new OSProcess(ms);
			process.executable.id = index;
			index++;
			mission.activeCount++;
			process.executable.start();
		}

		mission.currMissSeq.seqWait();

		for (int i = 0; i < mission.getNumberOfManagedSchedulables(); i++) {
			try {
				ManagedSchedulable ms = mission.getManagedSchedulable(i);
				if (ms instanceof ManagedThread)
					((ManagedThread) ms).process.executable.join();
				else
					((ManagedEventHandler) ms).process.executable.join();
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	void runCleanup(Mission mission, MissionMemory missMem) {
		mission.phaseOfMission = Phase.CLEANUP;

		if (mission.activeCount > 0) {
			devices.Console.println("still have SOs");
			throw new IllegalArgumentException();
		}

		mission.terminateMSObjects();
		
		Mission.missionSet[mission.missionIndex] = null;
		if (mission.isMissionSetInitByThis == true) {
			Mission.isMissionSetInit = false;
		}

		mission.cleanUp();
		missMem.resetArea();
	}

	@Override
	Process getProcess(int index) {
		ManagedSchedulable ms = getManageSched(index);

		if (ms instanceof ManagedEventHandler)
			return ((ManagedEventHandler) ms).process;
		else
			return ((ManagedThread) ms).process;

	}

	@Override
	ManagedSchedulable getManageSched(int index) {
		if (index == -99)
			return null;
		if (index == -11)
			return MissionSequencer.outerMostSeq;

		int missionIndex = index / 20;
		int managedSchdeulableIndex = index % 20;
		return Mission.missionSet[missionIndex].getManagedSchedulable(managedSchdeulableIndex);
	}
}



