package carcontrol.scjlevel0.bt;


import java.io.IOException;

import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.PeriodicEventHandler;

import carcontrol.constants.Mode;
import carcontrol.data.RunData;
import carcontrol.device.Engine;
import carcontrol.io.Command;
import carcontrol.io.Port;

import javax.realtime.memory.ScopeParameters;

public class ParkPEvhInput extends PeriodicEventHandler {

	Port port;
	RunData data;
	
	int count = 0;	
	
	public ParkPEvhInput(PriorityParameters priority, PeriodicParameters release, 
			ScopeParameters storage, ConfigurationParameters config, String name, 
			Port port, RunData data) {
		
		/*public PeriodicEventHandler(PriorityParameters priority, PeriodicParameters release, 
				                      ScopeParameters storage, ConfigurationParameters config,
				                      String name)*/
		super (priority, release, storage, config, name);

		this.port = port;
		this.data = data;
	}
	
	@Override
	public void handleAsyncEvent() {
		try {	
			Command m = Command.getCommand(port.receive());
			
			System.out.println(this.getName() + " Received cmd: " + m);		
		
			switch (m) {
				case OFF: 
					CarSequencer.mode = Mode.OFF;				
					
					//CarConfiguration.engine.engineOff();
					CarConfiguration.frontLight.turnOff();
					
					System.out.println(" ==>  " +  this.getName() + " turn off");		
					
					Mission.getMission().requestTermination();
					break;
				case NEUTRAL: 
					System.out.println(" ==>  " +  this.getName() + "; Mode = " + m);
					CarSequencer.mode = Mode.NEUTRAL;
					
					//CarConfiguration.engine.engineOn();
					CarConfiguration.frontLight.turnOn();
					
					System.out.println(" ==>  " +  this.getName() + " turn on");
					
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
