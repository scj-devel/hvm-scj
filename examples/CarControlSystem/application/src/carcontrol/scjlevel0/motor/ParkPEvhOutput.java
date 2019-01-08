package carcontrol.scjlevel0.motor;

import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.PeriodicEventHandler;

import carcontrol.data.RunData;
import carcontrol.io.CommunicationDevice;
import carcontrol.io.Port;

import javax.realtime.memory.ScopeParameters;

public class ParkPEvhOutput extends PeriodicEventHandler {

	/*CommunicationDevice*/ Port commDevice;
	RunData outputData;

	public ParkPEvhOutput(PriorityParameters priority, PeriodicParameters release, 
			ScopeParameters storage, ConfigurationParameters config,
			String name, /*CommunicationDevice*/ Port commDevice, RunData outputData) {
		
	  /*public PeriodicEventHandler(PriorityParameters priority, PeriodicParameters release, 
	                                ScopeParameters storage, ConfigurationParameters config,
	                                String name)*/
	  super(priority, release, storage, config, name);
	  
	  this.commDevice = commDevice;
	  this.outputData = outputData;
	}

	@Override
	public void handleAsyncEvent() {
		System.out.println(this.getName());
		
		//commDevice.send(outputData.getData(), 0, outputData.getData().length);

	}

}
