package carcontrol.scjlevel0.bt;

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

public class NeutralPEvhInput extends PeriodicEventHandler {

	Port port;
	RunData data;
	
	int count = 0;
	
	
	public NeutralPEvhInput(PriorityParameters priority, PeriodicParameters release, 
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
		try {
			Command m = Command.getCommand(port.receive());
			
			System.out.println(this.getName() + " received cmd: " + m);
			
			switch (m) {
				case PARK: 
					CarSequencer.mode = Mode.PARK;
					System.out.println(" ==>  " +  this.getName() + " park");					
					Mission.getMission().requestTermination();
					break;
				case REVERSE:
					CarSequencer.mode = Mode.REVERSE;	
					System.out.println(" ==>  " +  this.getName() + " reverse");
					Mission.getMission().requestTermination();
					break;	
				case DRIVE:
					CarSequencer.mode = Mode.DRIVE;	
					System.out.println(" ==>  " +  this.getName() + " drive");
					Mission.getMission().requestTermination();
					break;
				default: 
					break;
			}
		}
		catch (IOException e) {
			System.out.println(this.getName() + " exception: " + e);	
		}
		
//		data.setDistance(??);
	}

}
