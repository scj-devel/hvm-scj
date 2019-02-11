package carcontrol.scjlevel0.motor;

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

public class DrivePEvhSpeed extends PeriodicEventHandler {

	Port port;
	RunData data;
	
	int command = -1;
	
	int count = 0;
	
	
	public DrivePEvhSpeed(PriorityParameters priority, PeriodicParameters release, 
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
		System.out.println(this.getName() + ": driving");
		CarConfiguration.engine.setEngineSpeed((byte)(100));
		
//		command = port.receive();
//		
//		if (command == Accelerator.ON || command == Accelerator.OFF || command == Brake.ON || command == Brake.OFF) {
//			driveHandling (command);
//			if (command == Brake.OFF)  // later: remove this
//				Mission.getMission().requestTermination();
//		}		
//		else
//			; // nothing
	}
	
	private void driveHandling(int command) {
		System.out.println(this.getName() + ".driveHandling");
	}
}
