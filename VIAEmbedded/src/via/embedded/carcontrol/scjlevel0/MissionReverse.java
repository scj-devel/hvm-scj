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

public class MissionReverse extends CyclicExecutive {

	long minorCycle = 50;  // 50 ms

	CommunicationDevice commDevice; 
	RunData data;

	public MissionReverse (CommunicationDevice commDevice, RunData data) {
		this.commDevice = commDevice;
		this.data = data;
	}

	protected void initialize() {
		PeriodicEventHandler speedPEvh = 
				new PeriodicSpeedHandlerReverse(
					new PriorityParameters(
						Priorities.MIN_PRIORITY),
						new PeriodicParameters(
									new RelativeTime(), // start  
									new RelativeTime(1 * minorCycle, 0)), // period 
						CarSafelet.storageParameters_Handlers,
						CarSafelet.configParameters,
						"SpeedHandler R", 
						commDevice, 
						data);
		speedPEvh.register();
		
		PeriodicEventHandler inputPEvh = 
				new PeriodicInputHandlerReverse(
					new PriorityParameters(
						Priorities.MIN_PRIORITY),
						new PeriodicParameters(
									new RelativeTime(), // start  
									new RelativeTime(10 * minorCycle, 0)), // period 
						CarSafelet.storageParameters_Handlers,
						CarSafelet.configParameters,
						"InputHandler R", 
						commDevice, 
						data);
		inputPEvh.register();
		PeriodicEventHandler outputPEvh = 
				new PeriodicOutputHandler(
					new PriorityParameters(Priorities.MIN_PRIORITY),
					new PeriodicParameters(
							new RelativeTime(), // start  
							new RelativeTime(10 * minorCycle, 0)), // period 
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
		Frame[] frames = new Frame[10];
		PeriodicEventHandler[] frame0 = new PeriodicEventHandler[1];
		PeriodicEventHandler[] frame1 = new PeriodicEventHandler[2];
		PeriodicEventHandler[] frame2 = new PeriodicEventHandler[2];
		PeriodicEventHandler[] frame3 = new PeriodicEventHandler[1];
		PeriodicEventHandler[] frame4 = new PeriodicEventHandler[1];
		PeriodicEventHandler[] frame5 = new PeriodicEventHandler[1];
		PeriodicEventHandler[] frame6 = new PeriodicEventHandler[1];
		PeriodicEventHandler[] frame7 = new PeriodicEventHandler[1];
		PeriodicEventHandler[] frame8 = new PeriodicEventHandler[1];
		PeriodicEventHandler[] frame9 = new PeriodicEventHandler[1];

		frame0[0] = handlers[0];
		frame1[0] = handlers[0]; frame1[1] = handlers[1];
		frame2[0] = handlers[0]; frame2[1] = handlers[2];
		frame3[0] = handlers[0];
		frame4[0] = handlers[0];
		frame5[0] = handlers[0];
		frame6[0] = handlers[0];
		frame7[0] = handlers[0];
		frame8[0] = handlers[0];
		frame9[0] = handlers[0];		

		frames[0] = new Frame(duration, frame0);
		frames[1] = new Frame(duration, frame1);
		frames[2] = new Frame(duration, frame2);
		frames[3] = new Frame(duration, frame3);
		frames[4] = new Frame(duration, frame4);
		frames[5] = new Frame(duration, frame5);
		frames[6] = new Frame(duration, frame6);
		frames[7] = new Frame(duration, frame7);
		frames[8] = new Frame(duration, frame8);
		frames[9] = new Frame(duration, frame9);

		return new CyclicSchedule(frames);
	}

}
