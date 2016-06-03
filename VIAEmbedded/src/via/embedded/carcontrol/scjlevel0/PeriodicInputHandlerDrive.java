package via.embedded.carcontrol.scjlevel0;

import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.StorageParameters;

import via.embedded.carcontrol.constants.Mode;
import via.embedded.carcontrol.data.RunData;
import via.embedded.carcontrol.io.CommunicationDevice;

public class PeriodicInputHandlerDrive extends PeriodicEventHandler {
	CommunicationDevice commDevice;
	RunData data;
	
	int command = -1;
	
	int count = 0;
	
	
	public PeriodicInputHandlerDrive(PriorityParameters priority, PeriodicParameters release, 
			StorageParameters storage, ConfigurationParameters config, String name, 
			CommunicationDevice commDevice, RunData data) {
		
		/*public PeriodicEventHandler(PriorityParameters priority, PeriodicParameters release, 
				                      StorageParameters storage, ConfigurationParameters config,
				                      String name)*/
		super (priority, release, storage, config, name);
;
		this.commDevice = commDevice;
		this.data = data;
	}
	
	@Override
	public void handleAsyncEvent() {
		System.out.println(this.getName());
		
		command = commDevice.receive();
		
		if (command == Mode.NEUTRAL) {
			CarSequencer.mode = command; 
			Mission.getMission().requestTermination();
		}
		else
			; // nothing		
		
//		data.setDistance(??);

	}

}
