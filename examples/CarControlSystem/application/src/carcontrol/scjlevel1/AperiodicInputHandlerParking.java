package carcontrol.scjlevel1;

import javax.realtime.AperiodicParameters;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.Mission;

import carcontrol.constants.Mode;
import carcontrol.data.RunData;
import carcontrol.device.Engine;
import carcontrol.io.Port;
import carcontrol.scjlevel0.CarSequencer;

import javax.realtime.memory.ScopeParameters;

public class AperiodicInputHandlerParking extends AperiodicEventHandler {

	Port port;
	RunData data;
	Engine engine;
	
	int command = -1;

	public AperiodicInputHandlerParking(PriorityParameters priority,
			AperiodicParameters release, ScopeParameters storage,
			ConfigurationParameters config, String name, 
			Port port, RunData data, Engine engine) {
		
		super (priority, release, storage, config, name);

		this.port = port;
		this.data = data;
		this.engine = engine;
	}

	public void handleAsyncEvent() {
		
		//command = port.receive();
		
		System.out.println(this.getName() + " received command: " + command);
		
//		switch (command) {
//			case Mode.OFF: 
//				CarSequencer.mode = Mode.OFF;				
//				engine.engineOff();
//				Mission.getMission().requestTermination();
//				System.out.println(this.getName() + " mode = " + Mode.OFF);
//				break;
//			case Mode.NEUTRAL: 
//				CarSequencer.mode = Mode.NEUTRAL;
//				engine.engineOn();
//				Mission.getMission().requestTermination();
//				break;			
//			default: 
//				break;
//		}
		
//		data.setDistance(??);
	}
	
}
