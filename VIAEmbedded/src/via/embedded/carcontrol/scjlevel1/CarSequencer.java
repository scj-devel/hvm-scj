package via.embedded.carcontrol.scjlevel1;


import java.io.IOException;

import javax.realtime.PriorityParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.scj.util.Priorities;

import via.embedded.carcontrol.constants.Mode;
import via.embedded.carcontrol.data.RunData;
import via.embedded.carcontrol.device.Engine;
import via.embedded.carcontrol.device.EngineImpl;
import via.embedded.carcontrol.io.CommunicationDevice;
import via.embedded.carcontrol.io.CommunicationDeviceImpl;

public class CarSequencer extends MissionSequencer<Mission> {

	Mission[] missions;
	public static int mode;
	
	String host = "HOST???";
	int port = 1234;
	CommunicationDevice commDevice = null; 
	
	RunData data = new RunData();	
	
	Engine engine = new EngineImpl();
	
	public CarSequencer() {
		super (new PriorityParameters(Priorities.SEQUENCER_PRIORITY),
				CarSafelet.storageParameters_Sequencer,
				CarSafelet.configParameters,
				"CarSeq");
		
		try {
			commDevice = new CommunicationDeviceImpl(host, port);
			System.out.println("CarSequencer.constructor commDevice = " + commDevice);
		}
		catch (IOException e) {
			System.out.println("CarCyclicExecutive.initialize error: " + e.getStackTrace());
			mode = Mode.OFF;	
		}
		
		missions = new Mission[4];
		missions[0] = new MissionParking(commDevice, data, engine);
		missions[1] = new MissionParking(commDevice, data, engine); // new MissionN(commDevice, data);
		missions[2] = new MissionParking(commDevice, data, engine); // new MissionR(commDevice, data);
		missions[3] = new MissionParking(commDevice, data, engine); // new MissionD(commDevice, data);
		
		mode = Mode.PARK;
	}
	
	@Override
	protected Mission getNextMission() {
		if (mode == Mode.OFF) {
			System.out.println("CarSequencer.getNextMission: mode = OFF");
			return null;
		}			
		else {
			System.out.println("CarSequencer.getNextMission: mode = " + mode);
			return missions[mode];
		}
	}
	
	public int getMode() {
		return mode;		
	}
	
	public void setMode (int mode) {
		CarSequencer.mode = mode;
	}

}
