package carcontrol.scjlevel0.motor;

import javax.realtime.ConfigurationParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.CyclicExecutive;
import javax.safetycritical.CyclicSchedule;
import javax.safetycritical.Frame;
import javax.safetycritical.PeriodicEventHandler;
import javax.realtime.memory.ScopeParameters;
import carcontrol.data.RunData;
import carcontrol.io.Port;

public class DriveMission extends CyclicExecutive {

	long minorCycle = 50;  // 50 ms

	Port port; 
	RunData data;
	
	ScopeParameters storageParameters_Handlers;
	ConfigurationParameters configParameters;

	public DriveMission (Port port, RunData data) {
		this.port = port;
		this.data = data;
	}

	protected void initialize() {
		String pevhName = CarConfiguration.SOnames.get(8);
		
		PeriodicEventHandler inputPEvh = 
			new DrivePEvhInput(
				CarConfiguration.table.get(pevhName).getPriorityParam(),
				CarConfiguration.table.get(pevhName).getPeriodicParam(),
				
				CarConfiguration.table.get(pevhName).getScopeParam(),					
				CarConfiguration.table.get(pevhName).getConfigurationParam(),	
					
				pevhName, 
				port, 
				data);
		inputPEvh.register();
		
		pevhName = CarConfiguration.SOnames.get(9);
		PeriodicEventHandler outputPEvh = 
			new DrivePEvhOutput(
				CarConfiguration.table.get(pevhName).getPriorityParam(),
				CarConfiguration.table.get(pevhName).getPeriodicParam(),
				
				CarConfiguration.table.get(pevhName).getScopeParam(),					
				CarConfiguration.table.get(pevhName).getConfigurationParam(),
				
				pevhName, 
				port, 
				data);			
		outputPEvh.register();
		
		pevhName = CarConfiguration.SOnames.get(10);
		PeriodicEventHandler speedPEvh = 
			new DrivePEvhSpeed(
				CarConfiguration.table.get(pevhName).getPriorityParam(),
				CarConfiguration.table.get(pevhName).getPeriodicParam(),
				
				CarConfiguration.table.get(pevhName).getScopeParam(),					
				CarConfiguration.table.get(pevhName).getConfigurationParam(),
				
				pevhName, 
				port, 
				data);
		speedPEvh.register();		
	}

	public long missionMemorySize() {
		return CarConfiguration.missionMemSizes.get(3);
	}

	public CyclicSchedule getSchedule(PeriodicEventHandler[] handlers) {
		RelativeTime duration = new RelativeTime(minorCycle, 0);
		return generateCyclicSchedule(handlers, duration);
	}
	
	private CyclicSchedule generateCyclicSchedule(PeriodicEventHandler[] handlers, RelativeTime duration) {
		Frame[] frames = new Frame[1];
		PeriodicEventHandler[] frame0 = new PeriodicEventHandler[3];

		frame0[0] = handlers[0];
		frame0[1] = handlers[1];
		frame0[2] = handlers[2];

		frames[0] = new Frame(duration, frame0);
		
		return new CyclicSchedule(frames);
	}

}
