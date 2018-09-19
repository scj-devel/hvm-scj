package carcontrol.scjlevel0;

import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.PeriodicEventHandler;

import carcontrol.constants.Accelerator;
import carcontrol.constants.Brake;
import carcontrol.data.RunData;
import carcontrol.io.Port;

import javax.realtime.memory.ScopeParameters;

public class ReversePEvhSpeed extends PeriodicEventHandler {

	Port port;
	RunData data;
	
	int command = -1;
	
	int count = 0;
	
	
	public ReversePEvhSpeed(PriorityParameters priority, PeriodicParameters release, 
			ScopeParameters storage, ConfigurationParameters config, String name, 
			Port port, RunData data) {
		
		/*public PeriodicEventHandler(PriorityParameters priority, PeriodicParameters release, 
				                      ScopeParameters storage, ConfigurationParameters config,
				                      String name)*/
		super (priority, release, storage, config, name);
;
		this.port = port;
		this.data = data;
	}
	
	@Override
	public void handleAsyncEvent() {
		System.out.println(this.getName());
		
//		command = port.receive();
//		
//		if (command == Accelerator.ON || command == Accelerator.OFF || command == Brake.ON || command == Brake.OFF) {
//			reverseHandling (command);
//			if (command == Brake.OFF)  // later: remove this
//				Mission.getMission().requestTermination();
//		}		
//		else
//			; // nothing	
	}
	
	private void reverseHandling(int command) {
		System.out.println(this.getName() + ".reverseHandling");
	}
}
