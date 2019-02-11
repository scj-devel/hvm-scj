package carcontrol.scjlevel0.motor_and_bt;


import java.io.IOException;

import javax.safetycritical.CyclicExecutive;
import javax.safetycritical.MissionSequencer;

import carcontrol.constants.Mode;

public class CarSequencer extends MissionSequencer {

	CyclicExecutive[] missions;
	public static Mode mode = Mode.OFF;
			
	public CarSequencer(String seqName) {
		super ( 
			CarConfiguration.table.get(seqName).getPriorityParam(),
			CarConfiguration.table.get(seqName).getScopeParam(),					
			CarConfiguration.table.get(seqName).getConfigurationParam(),
			seqName);
		
		missions = new CyclicExecutive[4]; 
		missions[0] = new ParkMission(CarConfiguration.port, CarConfiguration.data);
		missions[1] = new NeutralMission(CarConfiguration.port, CarConfiguration.data);
		missions[2] = new ReverseMission(CarConfiguration.port, CarConfiguration.data);
		missions[3] = new DriveMission(CarConfiguration.port, CarConfiguration.data);
		
		mode = Mode.PARK;
	}
	
	@Override
	protected CyclicExecutive getNextMission() {
		System.out.println("CarSequencer.getNextMission: mode = " + mode) ;
		if (mode == Mode.OFF) 
			return null;
		else 			
			return missions[mode.getValue()];
	}
}
