package via.embedded.carcontrol.scjlevel2;

import javax.realtime.AperiodicParameters;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.Mission;
import javax.safetycritical.StorageParameters;

import via.embedded.carcontrol.constants.Mode;
import via.embedded.carcontrol.data.RunData;
import via.embedded.carcontrol.device.Engine;
import via.embedded.carcontrol.io.CommunicationDevice;

public class AperiodicInputHandlerParking extends AperiodicEventHandler {

	CommunicationDevice commDevice;
	RunData data;
	Engine engine;
	
	int command = -1;

	public AperiodicInputHandlerParking(PriorityParameters priority,
			AperiodicParameters release, StorageParameters storage,
			ConfigurationParameters config, String name, 
			CommunicationDevice commDevice, RunData data, Engine engine) {
		
		super (priority, release, storage, config, name);

		this.commDevice = commDevice;
		this.data = data;
		this.engine = engine;
	}

	public void handleAsyncEvent() {
		
		//command = commDevice.receive();
		command = Mode2.TRANSMISSION;
		System.out.println("    " + this.getName() + " received command: " + command);
		
		switch (command) {
			case Mode.OFF: 
				CarSequencer.mode = Mode2.OFF;				
				engine.engineOff();
				Mission.getMission().requestTermination();
				System.out.println("    " + this.getName() + " mode = " + Mode.OFF);
				break;
			case Mode2.TRANSMISSION: 
				CarSequencer.mode = Mode2.TRANSMISSION;
				Mission.getMission().requestTermination();
				break;			
			default: 
				break;
		}
		
//		data.setDistance(??);
	}
	
}
