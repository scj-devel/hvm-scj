package via.embedded.carcontrol.scjlevel2;


import javax.realtime.PriorityParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;

public class TransmissionMission extends Mission {
	
	public static StorageParameters storageParameters_InnerSequencer1 = new StorageParameters(
			//350 * 1000, Const.PRIVATE_MEM, 0, Const.MISSION_MEM_DEFAULT - 150 * 1000);
			200 * 1000, Const.PRIVATE_MEM, 0, Const.MISSION_MEM_DEFAULT - 150 * 1000);
	public static StorageParameters storageParameters_InnerSequencer2 = new StorageParameters(
			//350 * 1000, Const.PRIVATE_MEM, 0, Const.MISSION_MEM_DEFAULT - 150 * 1000);
			200 * 1000, Const.PRIVATE_MEM, 0, Const.MISSION_MEM_DEFAULT - 150 * 1000);
	
	@Override
	protected void initialize() {
		
		System.out.println("  TransmissionMission.initialize() started");
		
		new ObservationSequencer(new PriorityParameters(5), storageParameters_InnerSequencer1).register();
		
		new DrivingSequencer(new PriorityParameters(4), storageParameters_InnerSequencer2).register();
		
		System.out.println("  TransmissionMission.initialize() finished");
	}

	@Override
	public long missionMemorySize() {
		return Const.MISSION_MEM;
	}
	
}
