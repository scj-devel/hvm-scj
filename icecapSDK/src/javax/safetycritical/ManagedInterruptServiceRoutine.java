package javax.safetycritical;

import javax.realtime.device.InterruptServiceRoutine;
import javax.safetycritical.annotate.SCJAllowed;

@SCJAllowed(javax.safetycritical.annotate.Level.LEVEL_1)
public abstract class ManagedInterruptServiceRoutine extends InterruptServiceRoutine {
	
	public ManagedInterruptServiceRoutine(long sizes) {
		
	}

	public final void register(int interrupt)
			throws javax.realtime.RegistrationException {
		
	}
	
	public final void register(int interrupt, int ceiling)
			throws javax.realtime.RegistrationException {
		
	}
	
	
	public void unhandledException(Exception except) {
		
	}
	
	public final void unregister( ) {
		
	}
	
	@Override
	protected void handle() {
		// TODO Auto-generated method stub

	}

}
