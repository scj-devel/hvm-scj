package via.embedded.carcontrol.scjlevel2;

import javax.realtime.AperiodicParameters;
import javax.realtime.ConfigurationParameters;
import javax.realtime.HighResolutionTime;
import javax.realtime.PriorityParameters;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.OneShotEventHandler;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;

public class OneShotEvhStub extends OneShotEventHandler {
	
	AperiodicEventHandler apevh;
	
	public OneShotEvhStub(PriorityParameters priority, HighResolutionTime<?> releaseTime, AperiodicParameters release,
			StorageParameters storage, AperiodicEventHandler apevh) {
		super(priority, releaseTime, release, storage, new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE }));
		this.apevh = apevh;			
	}

	public void handleAsyncEvent() {
		devices.Console.println("    OneShotEvhStub.handleAsyncEvent: instead of interrupt \n");
		apevh.release();
	}
}


