package vm;

import icecaptools.IcecapCVar;
import icecaptools.IcecapCompileMe;

import javax.realtime.AbsoluteTime;

public abstract class RealtimeClock {
	private static RealtimeClock instance;

	@IcecapCVar(expression = "systemClock", requiredIncludes = "extern volatile uint32 systemClock;")
	private static int systemClock;

	@IcecapCompileMe
	protected RealtimeClock() {
		systemClock = 0;
	}

	public static RealtimeClock getRealtimeClock() {
		if (instance != null) {
			return instance;
		} else {
			instance = Machine.getMachineFactory().getRealtimeClock();
			return getRealtimeClock();
		}
	}

	/**
	 * Gets the current resolution of the realtime clock. The resolution is the
	 * nominal interval between ticks. 
	 * 
	 * @return The current resolution of the realtime clock in nanoseconds.
	 */
	abstract public int getGranularity();

	abstract public void getCurrentTime(AbsoluteTime now);

	abstract public void delayUntil(AbsoluteTime time);

	abstract public void awaitTick();

	public static class DefaultRealtimeClock extends RealtimeClock {
		@IcecapCVar(expression = "systemClock", requiredIncludes = "extern volatile uint32 systemClock;")
		protected static int systemClock;
		
		protected AbsoluteTime now;

		protected DefaultRealtimeClock() {
			now = new AbsoluteTime();
		}

		@Override
		public int getGranularity() {
			return 1000000;
		}

		@IcecapCompileMe
		@Override
		public void getCurrentTime(AbsoluteTime now) {
			now.set(systemClock, 0);
		}

		@Override
		public void delayUntil(AbsoluteTime time) {
			do {
				getCurrentTime(now);
			} while (now.compareTo(time) < 0);
		}

		@IcecapCompileMe
		@Override
		public void awaitTick() {
			int time = systemClock;
			/* Should call a wait assembler instruction instead */
			while (time == systemClock) {
				;
			}
		}
	}

	/**
	 * Delay until <code>time</code>.
	 * 
	 * @param time
	 *            is the absolut time
	 */
	public static void delayUntilTime(AbsoluteTime time) {
		getRealtimeClock().delayUntil(time);
	}

	/**
	 * Delay until next system tick 
	 * 
	 */
	public static void waitForNextTick() {
		getRealtimeClock().awaitTick();
	}
}
