package carcontrol.scjlevel1;


import java.io.IOException;

import javax.realtime.PriorityParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.scj.util.Priorities;

import carcontrol.constants.Mode;
import carcontrol.data.RunData;
import carcontrol.device.Engine;
import carcontrol.device.FrontLight;
import carcontrol.device.impl.EngineImpl;
import carcontrol.device.impl.FrontLightImpl;
import carcontrol.io.Port;
import carcontrol.io.CommunicationDeviceImpl;

public class CarSequencer extends MissionSequencer {

	Mission[] missions;
	public static Mode mode;
	
//	String host = "HOST???";
//	int port = 1234;
	
	String target = "/dev/ttyUSB0";  // make a CarConfiguration class
	String property = "baudrate=19200";
	
	Port port = null; 
	
	RunData data = new RunData();	
	
	FrontLight frontLight =  new FrontLightImpl();	// see scjlevel0
	Engine engine = new EngineImpl(frontLight);
	
	public CarSequencer() {
		super (new PriorityParameters(Priorities.SEQUENCER_PRIORITY),
				CarSafelet.storageParameters_Sequencer,
				CarSafelet.configParameters,
				"CarSeq");
		
		try {
			port = new Port(new CommunicationDeviceImpl(target, property));
			System.out.println("CarSequencer.constructor port = " + port);
		}
		catch (IOException e) {
			System.out.println("CarCyclicExecutive.initialize error: " + e.getStackTrace());
			mode = Mode.OFF;	
		}
		
		missions = new Mission[4];
		missions[0] = new MissionParking(port, data, engine);
		missions[1] = new MissionParking(port, data, engine);  // ToDo: correct to MissionNeutral. ...
		missions[2] = new MissionParking(port, data, engine);  // ToDo: correct 
		missions[3] = new MissionParking(port, data, engine);  // ToDo: correct 
		
		mode = Mode.PARKING;
	}
	
	@Override
	protected Mission getNextMission() {
		if (mode == Mode.OFF) {
			System.out.println("CarSequencer.getNextMission: mode = OFF");
			return null;
		}			
		else {
			System.out.println("CarSequencer.getNextMission: mode = " + mode);
			return missions[mode.getValue()];
		}
	}
	
	public /*int*/ Mode getMode() {
		return mode;		
	}
	
	public void setMode (/*int*/ Mode mode) {
		CarSequencer.mode = mode;
	}

}
