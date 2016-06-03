package via.embedded.carcontrol.scjlevel0;

import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.StorageParameters;

import via.embedded.carcontrol.data.RunData;
import via.embedded.carcontrol.io.CommunicationDevice;

public class PeriodicOutputHandler extends PeriodicEventHandler {

	CommunicationDevice commDevice;
	RunData outputData;

	public PeriodicOutputHandler(PriorityParameters priority, PeriodicParameters release, 
			StorageParameters storage, ConfigurationParameters config,
			String name, CommunicationDevice commDevice, RunData outputData) {
		
	  /*public PeriodicEventHandler(PriorityParameters priority, PeriodicParameters release, 
	                                StorageParameters storage, ConfigurationParameters config,
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
