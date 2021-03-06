package carcontrol.scjlevel0.bt;

import java.io.IOException;

import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.PeriodicEventHandler;

import carcontrol.data.RunData;
import carcontrol.io.CommunicationDevice;
import carcontrol.io.Port;

import javax.realtime.memory.ScopeParameters;

public class ParkPEvhOutput extends PeriodicEventHandler {

	Port port;
	RunData outputData;

	public ParkPEvhOutput(PriorityParameters priority, PeriodicParameters release, 
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
		
		try {
			port.send((byte)80);  // P
			port.send((byte)65);  // A
			port.send((byte)82);  // R
			port.send((byte)75);  // K
		} 
		catch (IOException e) {
		}
	}

}
