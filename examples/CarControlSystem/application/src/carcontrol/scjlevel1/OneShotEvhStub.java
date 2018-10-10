package carcontrol.scjlevel1;

import javax.realtime.AperiodicParameters;
import javax.realtime.ConfigurationParameters;
import javax.realtime.HighResolutionTime;
import javax.realtime.PriorityParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.OneShotEventHandler;
import javax.realtime.memory.ScopeParameters;
import javax.scj.util.Const;

public class OneShotEvhStub extends OneShotEventHandler {
	
	ManagedISR_Parking isr;
	
	public OneShotEvhStub(PriorityParameters priority, HighResolutionTime<?> releaseTime, AperiodicParameters release,
			ScopeParameters storage, ManagedISR_Parking isr) {
		super(priority, releaseTime, release, storage, new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE }));
		this.isr = isr;
	}

	public void handleAsyncEvent() {
		devices.Console.println("Car control, Level 1: OneShotEvhStub.handleAsyncEvent: interrupt \n");
		isr.handle();
	}
}


