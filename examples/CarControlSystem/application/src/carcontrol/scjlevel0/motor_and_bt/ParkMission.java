package carcontrol.scjlevel0.motor_and_bt;

import javax.realtime.RelativeTime;
import javax.safetycritical.CyclicExecutive;
import javax.safetycritical.CyclicSchedule;
import javax.safetycritical.Frame;
import javax.safetycritical.PeriodicEventHandler;

import carcontrol.data.RunData;
import carcontrol.device.Engine;
import carcontrol.io.Port;

public class ParkMission extends CyclicExecutive {
	
	long minorCycle = 5000;  // 500 ms
	
	Port port; 
	RunData data;
	
	public ParkMission (Port port, RunData data) {
		this.port = port;
		this.data = data;
	}

	@Override
	protected void initialize() {
		String pevhName = CarConfiguration.SOnames.get(1);
		
		PeriodicEventHandler inputPEvh = 
			new ParkPEvhInput(
				CarConfiguration.table.get(pevhName).getPriorityParam(),
				CarConfiguration.table.get(pevhName).getPeriodicParam(),
				
				CarConfiguration.table.get(pevhName).getScopeParam(),					
				CarConfiguration.table.get(pevhName).getConfigurationParam(),	
					
				pevhName, 
				port, 
				data);
		inputPEvh.register();
		
		pevhName = CarConfiguration.SOnames.get(2);
		PeriodicEventHandler outputPEvh = 
			new ParkPEvhOutput(
				CarConfiguration.table.get(pevhName).getPriorityParam(),
				CarConfiguration.table.get(pevhName).getPeriodicParam(),
				
				CarConfiguration.table.get(pevhName).getScopeParam(),					
				CarConfiguration.table.get(pevhName).getConfigurationParam(),
				
				pevhName, 
				port, 
				data);			
		outputPEvh.register();
	}

	@Override
	public long missionMemorySize() {
		return CarConfiguration.missionMemSizes.get(0);
	}	

	@Override
	public CyclicSchedule getSchedule(PeriodicEventHandler[] handlers) {
		RelativeTime duration = new RelativeTime(minorCycle, 0);
		return generateCyclicSchedule(handlers, duration);
	}
	
	private CyclicSchedule generateCyclicSchedule(PeriodicEventHandler[] handlers, RelativeTime duration) {
		Frame[] frames = new Frame[1];
		PeriodicEventHandler[] frame0 = new PeriodicEventHandler[2];

		frame0[0] = handlers[0];
		frame0[1] = handlers[1];

		frames[0] = new Frame(duration, frame0);

		return new CyclicSchedule(frames);
	}

}
