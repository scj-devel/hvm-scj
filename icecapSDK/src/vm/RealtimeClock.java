package vm;

import icecaptools.IcecapCompileMe;

import javax.realtime.AbsoluteTime;

public abstract class RealtimeClock {
	private static RealtimeClock instance;

	protected RealtimeClock() {
	}

	@IcecapCompileMe
	public static RealtimeClock getRealtimeClock() {
		if (instance != null) {
			return instance;
		} else {
			instance = Machine.getMachineFactory().getRealtimeClock();
			return getRealtimeClock();
		}
	}

	abstract public int getGranularity();

	abstract public void getCurrentTime(AbsoluteTime now);

	abstract public void delayUntil(AbsoluteTime time);
	
	abstract public void awaitTick();
	
	public static class DefaultRealtimeClock extends RealtimeClock {
		@Override
		public int getGranularity() {
			return getNativeResolution();
		}

		@Override
		public void getCurrentTime(AbsoluteTime now) {
			getNativeTime(now); 
			/* 'now' may not be normalized */ 
		}

		@Override
		public void delayUntil(AbsoluteTime time) {
			delayNativeUntil(time);
		}

		@Override
		public void awaitTick() {
			awaitNextTick();
		}
	}

	/* ==== Clock and Time ================================================ */

	/**
	 * Gets the current resolution of the realtime clock. The resolution is the
	 * nominal interval between ticks. 
	 * 
	 * @return The current resolution of the realtime clock in nanoseconds.
	 */
	private static native int getNativeResolution();

	/**
	 * Gets the current time of the realtime clock Returns Absolute time in
	 * <code>dest</code>.
	 * 
	 * @return 0 if ok, not zero if an error occor.
	 */
	private static native int getNativeTime(AbsoluteTime dest);

	/**
	 * Delay until <code>time</code>.
	 * 
	 * @param time
	 *            is the absolut time
	 */
	public static void delayUntilTime(AbsoluteTime time)
	{
		getRealtimeClock().delayUntil(time);
	}
	
	private static native void delayNativeUntil(AbsoluteTime time);
	
	/**
	 * Delay until next system tick 
	 * 
	 */
	public static void waitForNextTick()
	{
		getRealtimeClock().awaitTick();
	}
	
	private static native void awaitNextTick();
}
