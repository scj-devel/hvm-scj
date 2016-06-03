package via.embedded.carcontrol.scjlevel0;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.CyclicExecutive;
import javax.safetycritical.CyclicSchedule;
import javax.safetycritical.Frame;
import javax.safetycritical.PeriodicEventHandler;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import via.embedded.carcontrol.data.RunData;
import via.embedded.carcontrol.io.CommunicationDevice;

public class MissionNeutral extends CyclicExecutive {

	long minorCycle = 500;  // 500 ms

	CommunicationDevice commDevice; 
	RunData data;

	public MissionNeutral (CommunicationDevice commDevice, RunData data) {
		this.commDevice = commDevice;
		this.data = data;
	}

	protected void initialize() {
		PeriodicEventHandler inputPEvh = 
				new PeriodicInputHandlerNeutral(
					new PriorityParameters(
						Priorities.MIN_PRIORITY),
						new PeriodicParameters(
									new RelativeTime(), // start  
									new RelativeTime(1 * minorCycle, 0)), // period 
						CarSafelet.storageParameters_Handlers,
						CarSafelet.configParameters,
						"InputHandler N", 
						commDevice, 
						data);
		inputPEvh.register();
		PeriodicEventHandler outputPEvh = 
				new PeriodicOutputHandler(
					new PriorityParameters(Priorities.MIN_PRIORITY),
					new PeriodicParameters(
							new RelativeTime(), // start  
							new RelativeTime(1 * minorCycle, 0)), // period 
					CarSafelet.storageParameters_Handlers,
					CarSafelet.configParameters, 
					"OutputHandler", 
					commDevice, 
					data);			
		outputPEvh.register();

	}

	public long missionMemorySize() {
		return Const.MISSION_MEM;
	}

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







