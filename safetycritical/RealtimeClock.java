/**************************************************************************
 * File name  : RealtimeClock.java
 * 
 * This file is part of our SCJ Level 0 and Level 1 implementation, 
 * based on SCJ Draft, Version 0.79. 16 May 2011.
 *
 * It is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as  
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This SCJ Level 0 and Level 1 implementation is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the  
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this SCJ Level 0 and Level 1 implementation.  
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2012 
 * @authors  Anders P. Ravn, Aalborg University, DK
 *           Stephan E. Korsholm and Hans S&oslash;ndergaard, 
 *             VIA University College, DK
 *   
 * Description: 
 * 
 * Revision history:
 *   date   init  comment
 *
 *************************************************************************/

package javax.safetycritical;

import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;

import vm.VM;

class RealtimeClock extends Clock {
	private static Clock rtClock = null;

	private static vm.RealtimeClock nativeClock;

	private static RelativeTime resolution = null; // The nominal interval
													// between ticks.

	private RealtimeClock() {
	}

	static Clock instance() {
		if (rtClock == null) {
			RelativeTime dest = new RelativeTime(0, 0);

			nativeClock = VM.getRealtimeClock();

			nativeClock.getGranularity(dest);

			rtClock = new RealtimeClock();
			resolution = new RelativeTime(dest.getMilliseconds(), dest.getNanoseconds(), rtClock);
		}
		return rtClock;
	}

	@Override
	public RelativeTime getEpochOffset() {
		return new RelativeTime(0, 0, this);
	}

	/**
	 * Returns a newly allocated RelativeTime object that indicates the nominal
	 * interval between ticks. The return value shall be associated with this
	 * clock. All relative time differences measured by this clock are
	 * approximately an integral multiple of the resolution.
	 * 
	 * @return clock resolution.
	 */
	public RelativeTime getResolution() {
		return new RelativeTime(resolution);
	}

	@Override
	public AbsoluteTime getTime() {
		return getTime(new AbsoluteTime(0, 0, this));
	}

	@Override
	public AbsoluteTime getTime(AbsoluteTime dest) {
		if (dest != null) {
			// VMFct.getNativeTime(dest); // returns Abs time in dest
			nativeClock.getCurrentTime(dest);

			// The values in dest are not normalized:
			// Native values are (x secs, y nanoSecs) which are returned in
			// dest as (1000*x milliSecs, y nanoSecs)

			dest.normalize();
			dest.clock = rtClock;
		}
		return dest;
	}

	@Override
	public RelativeTime getResolution(RelativeTime dest) {
		if (dest != null) {
			dest.set(resolution.getMilliseconds(), resolution.getNanoseconds());
			dest.clock = rtClock;
		}
		return dest;
	}

	@Override
	public boolean drivesEvents() {
		// TODO Auto-generated method stub
		return false;
	}

	@SCJAllowed(Level.LEVEL_1)
	public void registerCallBack(AbsoluteTime t, ClockCallBack clockEvent) {
		// TODO method stub
	}

	@SCJAllowed(Level.LEVEL_1)
	public boolean resetTargetTime(AbsoluteTime time) {
		// TODO method stub
		return false;
	}
}
