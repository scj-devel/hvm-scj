package carcontrol.scjlevel2;

import javax.realtime.PriorityParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.realtime.memory.ScopeParameters;
import javax.scj.util.Priorities;

public class DrivingSequencer extends MissionSequencer {
	
	private boolean finalized;
	
	
	public DrivingSequencer(PriorityParameters priority, ScopeParameters storage) {
		super (priority, storage, CarSafelet.configParameters,
			   "DrivingSeq");
		finalized = false;
		System.out.println("    DrivingSequencer constructor");
	}
	
	protected Mission getNextMission() {
		if (finalized)
			return null;
		else  {
			System.out.println("      DrivingSequencer.getNextMission: NeutralMission returned");
			finalized = true;
			return new NeutralMission();
		}
	}
	
	
//	public static int transmissionMode;
//	Mission[] missions;
//	
//	public DrivingSequencer() {
//		super (new PriorityParameters(Priorities.SEQUENCER_PRIORITY),
//				CarSafelet.storageParameters_Sequencer,
//				CarSafelet.configParameters,
//				"DrivingSeq");
//		missions = new Mission[3];
//		missions[0] = new NeutralMission();
//		missions[1] = new ReverseMission();
//		missions[2] = new DriveMission();
//		
//		transmissionMode = TransmissionMode.TRANSMISSION_OFF;
//	}
//	
//	protected Mission getNextMission() {
//		if (transmissionMode == TransmissionMode.PARK) {
//			System.out.println("DrivingSequencer.getNextMission: mode = PARK");
//			Mission.getMission().requestTermination();
//			return null;
//		}			
//		else {
//			System.out.println("DrivingSequencer.getNextMission: mode = " + transmissionMode);
//			return missions[transmissionMode - 11];
//		}
//	}

}
