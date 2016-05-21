package javax.realtime.device;

import javax.realtime.RealtimeExecutionContext;
import javax.safetycritical.annotate.SCJAllowed;

@SCJAllowed(javax.safetycritical.annotate.Level.LEVEL_1)
public abstract class InterruptServiceRoutine implements
		RealtimeExecutionContext {
	
	public static javax.realtime.device.InterruptServiceRoutine getHandler(int interrupt) {
		return null;
	}
	
//	public static javax.realtime.Affinity getInterruptAffinity(int InterruptId) {
//		return null;
//	}
	
	public static int getInterruptPriority(int InterruptId) {
		return -1;
	}
	
	public static int getMaximumInterruptPriority() {
		return -1;
	}
	
	public static int getMinimumInterruptPriority() {
		return -1;
	}
	
	protected abstract void handle();
}
