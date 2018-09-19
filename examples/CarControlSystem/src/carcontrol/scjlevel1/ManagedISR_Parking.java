package carcontrol.scjlevel1;

import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.ManagedInterruptServiceRoutine;

public class ManagedISR_Parking extends ManagedInterruptServiceRoutine {
	
	AperiodicEventHandler apevh; 
	
	public ManagedISR_Parking(long sizes, AperiodicEventHandler apevh) {
		super(sizes);
		this.apevh = apevh;
	}
	
	public void handle() {
		
		System.out.println("ManagedISR_Parking.handle calls apevh.release()");
		apevh.release();

	}
}
