package carcontrol.configuration;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.ConfigurationParameters;
import javax.realtime.memory.ScopeParameters;

import javax.scj.util.Const;

/**
 * Schedulable Object configuration values
 * 
 * @author hso
 *
 */
public class SOConfig {
	
	int priority;
	
	long start_ms; // ms means millisecs
	int start_ns;  // ns means nanosecs
	
	long period_ms; 
	int period_ns;  
		
	long deadline_ms;
	int deadline_ns;
	
	// ScopeParameters:
	long maxInitialArea;
	long maxImmortal;
	long maxContainingArea;
	long maxInitialBackingStore;
	
	// ConfigurationParameters:
	int messageLength;
	int stackTraceLength;
	long stackSize;  
		
	
//	public SOConfig() {
//		priority = Priorities.NORM_PRIORITY;
//		
//		period_ms = 0; period_ns = 0;
//		
//		// etc.
//	}
//	
//	public SOConfig(int priority, long period_ms, int period_ns /* etc.*/) {
//		this.priority = priority;
//		
//		this.period_ms = period_ms; this.period_ns = period_ns;
//		
//		// etc.
//	}
	
	public SOConfig(int[] values) {
		this.priority = values[0];
		
		this.start_ms = values[1]; this.start_ns = values[2];
		this.period_ms = values[3]; this.period_ns = values[4];
		this.deadline_ms = values[5]; this.deadline_ns = values[6];
		
		this.maxInitialArea = values[7]; 
		this.maxImmortal = values[8];
		this.maxContainingArea = values[9];
		this.maxInitialBackingStore = values[10];
		
		this.messageLength = values[11];
		this.stackTraceLength = values[12];
		this.stackSize = values[13];
	}
	
	
//	public void setPriority (int priority) {
//		this.priority = priority;		
//	}
	
	public PriorityParameters getPriorityParam() {
		return new PriorityParameters (priority);
	}
		
	public PeriodicParameters getPeriodicParam() {
		return new PeriodicParameters (new RelativeTime(start_ms, start_ns), new RelativeTime(period_ms, period_ns));
	}
	
	public ScopeParameters getScopeParam() {
		return new ScopeParameters (maxInitialArea, maxImmortal, maxContainingArea, maxInitialBackingStore);
	}
	
	public ConfigurationParameters getConfigurationParam() {
		return new ConfigurationParameters (messageLength, stackTraceLength, new long[] { stackSize });
	}
	
	public String toString() {
		return "pr = " + priority + "; period = (" + period_ms + ", " + period_ns + ");" ;
	}

}

