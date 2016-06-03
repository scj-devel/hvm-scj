package via.embedded.carcontrol.scjlevel1;

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RegistrationException;
import javax.realtime.RelativeTime;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.Mission;
import javax.safetycritical.PeriodicEventHandler;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import via.embedded.carcontrol.data.RunData;
import via.embedded.carcontrol.device.Engine;
import via.embedded.carcontrol.io.CommunicationDevice;

public class MissionParking extends Mission {
	
	CommunicationDevice commDevice; 
	RunData data;
	Engine engine;
	
	public MissionParking (CommunicationDevice commDevice, RunData data, Engine engine) {
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
		
		ManagedISR_Parking isr_Parking = new ManagedISR_Parking(Const.PRIVATE_MEM_DEFAULT, inputAPEvh);
//		try {
//			isr_Parking.register(1);
//		}
//		catch (RegistrationException e) {
//			System.err.println("In MissionParking.initialize: RegistrationException: " + e.getMessage());
//		}
		
		new OneShotEvhStub(
				new PriorityParameters(Priorities.MAX_PRIORITY), 
				new RelativeTime(2*1000, 0),  // release time = 2 mseecs
				new AperiodicParameters(new RelativeTime(500, 0), null), 
				CarSafelet.storageParameters_Handlers,
				isr_Parking).register();

		
//		PeriodicEventHandler outputPEvh = 
//				new OutputHandler(
//					new PriorityParameters(Priorities.MIN_PRIORITY),
//					new PeriodicParameters(
//							new RelativeTime(), // start  
//							new RelativeTime(1 * minorCycle, 0)), // period 
//					CarSafelet.storageParameters_Handlers,
//					CarSafelet.configParameters, 
//					"OutputHandler", 
//					commDevice, 
//					data);			
//		outputPEvh.register();
		
		System.out.println("MissionParking.initialize() finished");
	}

	@Override
	public long missionMemorySize() {
		return Const.MISSION_MEM;
	}
	
}
