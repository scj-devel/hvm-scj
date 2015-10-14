package javax.realtime;

import javax.scj.util.Priorities;

public final class TestPortal {
	public static class HighResolutionTimeStub extends HighResolutionTime {
		public HighResolutionTimeStub(long millis, int nanos, Clock clock) {
			super(millis, nanos, clock);
		}
	}
	
	public static final int getMaxPr() {
		return Priorities.MAX_PRIORITY;
	}
	
	public static final int getMinPr() {
		return Priorities.MIN_PRIORITY;
	}
}
