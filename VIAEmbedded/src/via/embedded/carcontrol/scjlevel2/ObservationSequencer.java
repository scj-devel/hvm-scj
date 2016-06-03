package via.embedded.carcontrol.scjlevel2;

import javax.realtime.PriorityParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.StorageParameters;

public class ObservationSequencer extends MissionSequencer<Mission> {
	
	private boolean finalized;
	
	
	public ObservationSequencer(PriorityParameters priority, StorageParameters storage) {
		super (priority, storage, CarSafelet.configParameters,
			   "ObservationSeq");
		finalized = false;
		System.out.println("    ObservationSequencer constructor");
	}
	
	protected Mission getNextMission() {
		if (finalized)
			return null;
		else  {
			System.out.println("      ObservationSequencer.getNextMission: ObservationMission returned");
			finalized = true;
			return new ObservationMission();
		}
	}

}
