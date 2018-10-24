package carcontrol.scjlevel2;

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.Mission;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import carcontrol.data.RunData;
import carcontrol.device.Engine;
import carcontrol.io.CommunicationDevice;

public class ParkingMission extends Mission {
	
	CommunicationDevice commDevice; 
	RunData data;
	Engine engine;
	
	public ParkingMission (CommunicationDevice commDevice, RunData data, Engine engine) {
		this.commDevice = commDevice;
		this.data = data;
		this.engine = engine;
	}

	@Override
	protected void initialize() {
		AperiodicEventHandler inputAPEvh = 
			new AperiodicInputHandlerParking(
					new PriorityParameters(Priorities.PR97), 
	         		new AperiodicParameters(new RelativeTime(0, 0, Clock.getRealtimeClock()), null), 
					CarSafelet.storageParameters_Handlers,
					CarSafelet.configParameters,
					"Aperiodic InputHandler Parking", 
					commDevice, 
					data,
					engine);
		inputAPEvh.register();
		
		new OneShotEvhStub(
				new PriorityParameters(Priorities.MAX_PRIORITY), 
				new RelativeTime(1000, 0),  // release time = 2 mseecs
				new AperiodicParameters(new RelativeTime(100, 0), null), 
				CarSafelet.storageParameters_Handlers,
				inputAPEvh).register();
		
		System.out.println("  ParkMission.initialize() finished");
	}

	@Override
	public long missionMemorySize() {
		return Const.MISSION_MEM;
	}
	
}
