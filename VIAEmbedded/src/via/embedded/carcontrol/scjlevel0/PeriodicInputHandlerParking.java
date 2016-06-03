package via.embedded.carcontrol.scjlevel0;


import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.StorageParameters;

import via.embedded.carcontrol.constants.Mode;
import via.embedded.carcontrol.data.RunData;
import via.embedded.carcontrol.device.Engine;
import via.embedded.carcontrol.io.CommunicationDevice;

public class PeriodicInputHandlerParking extends PeriodicEventHandler {

	CommunicationDevice commDevice;
	RunData data;
	Engine engine;
	
	int command = -1;
	
	int count = 0;
	
	
	public PeriodicInputHandlerParking(PriorityParameters priority, PeriodicParameters release, 
			StorageParameters storage, ConfigurationParameters config, String name, 
			CommunicationDevice commDevice, RunData data, Engine engine) {
		
		/*public PeriodicEventHandler(PriorityParameters priority, PeriodicParameters release, 
				                      StorageParameters storage, ConfigurationParameters config,
				                      String name)*/
		super (priority, release, storage, config, name);

		this.commDevice = commDevice;
		this.data = data;
		this.engine = engine;
	}
	
	@Override
	public void handleAsyncEvent() {
		
		System.out.println(this.getName());
		
		command = commDevice.receive();
		
		System.out.println(this.getName() + " received command: " + command);
		
		switch (command) {
			case Mode.OFF: 
				CarSequencer.mode = Mode.OFF;				
				engine.engineOff();
				Mission.getMission().requestTermination();
				break;
			case Mode.NEUTRAL: 
				CarSequencer.mode = Mode.NEUTRAL;
				engine.engineOn();
				Mission.getMission().requestTermination();
				break;			
			default: 
				break;
		}
		
//		data.setDistance(??);		
	}

}
