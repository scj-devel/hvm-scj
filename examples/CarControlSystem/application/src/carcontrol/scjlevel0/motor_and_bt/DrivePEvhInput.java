package carcontrol.scjlevel0.motor_and_bt;

import java.io.IOException;

import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.PeriodicEventHandler;

import carcontrol.constants.Mode;
import carcontrol.data.RunData;
import carcontrol.io.Command;
import carcontrol.io.Port;

import javax.realtime.memory.ScopeParameters;

public class DrivePEvhInput extends PeriodicEventHandler {
	Port port;
	RunData data;
	
	int command = -1;
	
	int count = 0;
	
	
	public DrivePEvhInput(PriorityParameters priority, PeriodicParameters release, 
			ScopeParameters storage, ConfigurationParameters config, String name, 
			Port port, RunData data) {
		
		/*public PeriodicEventHandler(PriorityParameters priority, PeriodicParameters release, 
				                      StorageParameters storage, ConfigurationParameters config,
				                      String name)*/
		super (priority, release, storage, config, name);
;
		this.port = port;
		this.data = data;
	}
	
	@Override
	public void handleAsyncEvent() {
		try {
			Command m = Command.getCommand(port.receive());
			
			System.out.println(this.getName() + " received cmd: " + m);
			
			if (m.getValue() == 1 /*Command.NEUTRAL*/) {
				CarSequencer.mode = Mode.NEUTRAL;
				
				CarConfiguration.speeder.speed(-100);
				int speed = CarConfiguration.speeder.getSpeed();
				CarConfiguration.engine.setEngineSpeed((byte)(speed));
				
				Mission.getMission().requestTermination();
			}
			if (m.getValue() == 4 /*Command.BRAKE*/) {
				CarConfiguration.speeder.speed(-25);
				
				int speed = CarConfiguration.speeder.getSpeed();
				System.out.println(this.getName() + ": speeding " + speed);	
				
				CarConfiguration.engine.setEngineSpeed((byte)(speed));
			}
			if (m.getValue() == 5 /*Command.ACC*/) {
				CarConfiguration.speeder.speed(25);
				
				int speed = CarConfiguration.speeder.getSpeed();
				System.out.println(this.getName() + ": speeding " + speed);	
				
				CarConfiguration.engine.setEngineSpeed((byte)(speed));
			}
			else
				; // nothing	
			}
		catch (IOException e) {
			System.out.println(this.getName() + " exception: " + e);	
		}
//		data.setDistance(??);

	}

}
