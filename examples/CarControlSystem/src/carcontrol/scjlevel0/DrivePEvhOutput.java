package carcontrol.scjlevel0;

import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.PeriodicEventHandler;

import carcontrol.data.RunData;
import carcontrol.io.Port;

import javax.realtime.memory.ScopeParameters;

public class DrivePEvhOutput extends PeriodicEventHandler {

	Port port;
	RunData outputData;

	public DrivePEvhOutput(PriorityParameters priority, PeriodicParameters release, 
			ScopeParameters storage, ConfigurationParameters config,
			String name, Port port, RunData outputData) {
		
	  /*public PeriodicEventHandler(PriorityParameters priority, PeriodicParameters release, 
	                                ScopeParameters storage, ConfigurationParameters config,
	                                String name)*/
	  super(priority, release, storage, config, name);
	  
	  this.port = port;
	  this.outputData = outputData;
	}

	@Override
	public void handleAsyncEvent() {
		System.out.println(this.getName());
		
		//port.send(outputData.getData(), 0, outputData.getData().length);

	}

}
