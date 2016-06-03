package via.embedded.carcontrol.scjlevel0;

import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.StorageParameters;

import via.embedded.carcontrol.constants.Accelerator;
import via.embedded.carcontrol.constants.Brake;
import via.embedded.carcontrol.data.RunData;
import via.embedded.carcontrol.io.CommunicationDevice;

public class PeriodicSpeedHandlerReverse extends PeriodicEventHandler {

	CommunicationDevice commDevice;
	RunData data;
	
	int command = -1;
	
	int count = 0;
	
	
	public PeriodicSpeedHandlerReverse(PriorityParameters priority, PeriodicParameters release, 
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
		System.out.println(this.getName() + ": handleAsyncEvent");
		
		command = commDevice.receive();
		
		if (command == Accelerator.ON || command == Accelerator.OFF || command == Brake.ON || command == Brake.OFF) {
			reverseHandling (command);
			if (command == Brake.OFF)  // later: remove this
				Mission.getMission().requestTermination();
		}		
		else
			; // nothing	
	}
	
	private void reverseHandling(int command) {
		System.out.println(this.getName() + ".reverseHandling");
	}
}
