package javax.safetycritical;

import javax.scj.util.Const;
import javax.scj.util.Priorities;

class MissionHelper {
	Mission[] missionSet = null;
	boolean isMissionSetInit = false;

	public MissionHelper() {
		Services.setCeiling(this, Priorities.SEQUENCER_PRIORITY);
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

	ManagedSchedulable getManagedSchedulableByID(int id) {
		if (id == -99)
			return null;
		if (id == -11)
			return MissionSequencer.outerMostSeq;

		int missionIndex = id / 20;
		int managedSchdeulableIndex = id % 20;
		return missionSet[missionIndex].msSetForMission.managedSchObjects[managedSchdeulableIndex];
	}
}
