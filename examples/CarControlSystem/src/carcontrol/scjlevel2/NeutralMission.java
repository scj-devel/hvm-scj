package carcontrol.scjlevel2;


import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.Mission;
import javax.safetycritical.PeriodicEventHandler;
import javax.realtime.memory.ScopeParameters;
import javax.scj.util.Const;


public class NeutralMission extends Mission {

	protected void initialize() {
		new NeutralPeriodicEvh(
				new PriorityParameters(15), 
				new PeriodicParameters(
						new RelativeTime(0, 0, Clock.getRealtimeClock()), 
						new RelativeTime(1000, 0, Clock.getRealtimeClock())),
				CarSafelet.storageParameters_Handlers, 
				CarSafelet.configParameters,
				this).register();
	}

	@Override
	public long missionMemorySize() {
		return Const.MISSION_MEM;
	}

	private static class NeutralPeriodicEvh extends PeriodicEventHandler {
		Mission m;
		int count = 0;

		public NeutralPeriodicEvh(PriorityParameters priority, PeriodicParameters periodicParameters,
				ScopeParameters storage, ConfigurationParameters config, Mission m) {
			super(priority, periodicParameters, storage, config);
			this.m = m;
		}

		public void handleAsyncEvent() {
			devices.Console.println("        NeutralPeriodicEvh");
			count++;
			if (count == 3)
				m.requestTermination();
		}
	}
}
