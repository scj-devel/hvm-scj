package carcontrol.scjlevel2;


import java.io.IOException;

import javax.realtime.PriorityParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.scj.util.Priorities;

import carcontrol.data.RunData;
import carcontrol.device.Engine;
import carcontrol.device.FrontLight;
import carcontrol.device.impl.EngineImpl;
import carcontrol.device.impl.FrontLightImpl;
import carcontrol.io.CommunicationDevice;
import carcontrol.io.CommunicationDeviceImpl;
import carcontrol.scjlevel0.CarConfiguration;

public class CarSequencer extends MissionSequencer {

	Mission[] missions;
	public static int mode;
	
//	String host = "HOST???";
//	int port = 1234;
	CommunicationDevice commDevice = null; 
	
	RunData data = new RunData();	
	
	//FrontLight frontLight =  new FrontLightImpl();	// not finished; see scjlevel0
	//Engine engine = new EngineImpl(frontLight);
	
	public CarSequencer() {
		super (new PriorityParameters(Priorities.SEQUENCER_PRIORITY),
				CarSafelet.storageParameters_Sequencer,
				CarSafelet.configParameters,
				"CarSeq");
		
//		try {
//			//commDevice = new CommunicationDeviceImpl(/*host, port*/);
//			//System.out.println("CarSequencer.constructor commDevice = " + commDevice);
//		}
//		catch (IOException e) {
//			System.out.println("CarCyclicExecutive.initialize error: " + e.getStackTrace());
//			mode = Mode2.OFF;	
//		}
		
		missions = new Mission[2];
		missions[0] = new ParkingMission(commDevice, data, CarConfiguration.engine);  // see scjlevel0
		missions[1] = new TransmissionMission();
		
		mode = Mode2.PARK;
	}
	
	@Override
	protected Mission getNextMission() {
		if (mode == Mode2.OFF) {
			System.out.println("CarSequencer.getNextMission: mode = OFF");
			return null;
		}			
		else {
			
			System.out.println("CarSequencer.getNextMission: mode = " + mode);
			int m = mode;
			mode = Mode2.OFF;
			return missions[m];
		}
	}
	
	public int getMode() {
		return mode;		
	}
	
	public void setMode (int mode) {
		CarSequencer.mode = mode;
	}

}
