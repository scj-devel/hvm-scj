/**************************************************************************
 * File name  : Frame.java
 * 
 * This file is part a SCJ Level 0 and Level 1 implementation, 
 * based on SCJ Draft, Version 0.94 25 June 2013.
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
 *************************************************************************/
package javax.safetycritical;

import javax.realtime.RelativeTime;
import javax.safetycritical.annotate.SCJAllowed;

/**
 * The frames array represents the order in which event handlers are to be scheduled. <br>
 * Each <code>Frame</code> contains a duration of the frame and a list of 
 * periodic handlers to be executed during that time. <br>
 * Note that a <code>Frame</code> may have zero periodic handlers associated with them. 
 * This represents a period of time during which the <code>CyclicExecutive</code> is idle.
 * Within each execution frame of the <code>CyclicSchedule</code>, the <code>PeriodicEventHandler</code>
 * objects represented by the handlers array will be release in the order they appear within this array. <br>
 * The object allocates and retains private shallow copies of the duration and handlers array
 * within the same memory area as <code>this</code>. 
* 
* @version 1.2; - December 2013
* 
* @author Anders P. Ravn, Aalborg University, 
* <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
* Hans S&oslash;ndergaard, VIA University College, Denmark, 
* <A HREF="mailto:hso@viauc.dk">hso@via.dk</A>
* 
* @scjComment 
*  - semantic issue: Negative duration. It is checked in the constructor. <br>
*  - semantic issue: Null duration. It is checked in the constructor.
*/
@SCJAllowed
public final class Frame {
	RelativeTime duration;
	PeriodicEventHandler[] handlers;

	/*@ 
	  public invariant 
	    this.getDuration() != null && this.getHandlers() != null &&
	    ( (this.getDuration().getMilliseconds() > 0 || this.getDuration().getNanoseconds() > 0) );
	  @*/

	/**
	 * Constructor for a frame.
	 * 
	 * @param duration is a <code>RelativeTime</code> object.
	 * @param handlers is the list of periodic handlers.
	 */
	/*@ 
	  public normal_behavior
	    requires duration != null && handlers != null;
	    
	    requires (duration.getMilliseconds() > 0 ||
	              duration.getNanoseconds() > 0 );
	    
	//       requires this.getHandlers().length >= 0 ==>
	//         (\forall int i; 0 <= i && i < this.getHandlers().length;
	//             Mission.getCurrentMission().isRegistered(this.getHandlers()[i])  && 
	//             Mission.getCurrentMission().inMissionScope(this.getHandlers()[i]) 
	//         ); // thus no need to copy.
	    
	    ensures this.getDuration() == duration && this.getHandlers() == handlers; 
	  also
	  public exceptional_behavior
	    requires duration == null; 
	    signals (IllegalArgumentException) true;
	    
	  also
	  public exceptional_behavior
	    requires duration != null && duration.getMilliseconds() <= 0 && duration.getNanoseconds() <= 0;
	    signals (IllegalArgumentException) true;
	
	  also
	  public exceptional_behavior
	    requires handlers == null;
	    signals (IllegalArgumentException) true;       
	  @*/
	@SCJAllowed
	public Frame(RelativeTime duration, PeriodicEventHandler[] handlers) {
		if (duration == null)
			throw new IllegalArgumentException();
		if (duration.getMilliseconds() <= 0 && duration.getNanoseconds() <= 0)
			throw new IllegalArgumentException();
		if (handlers == null)
			throw new IllegalArgumentException();

		this.duration = duration;
		this.handlers = handlers;
	}

	/**
	 * 
	 * @return The list of handlers
	 */
	/*@ // implementation specification 
	  behavior
	    requires true; 
	    assignable \nothing;      
	    ensures \result == this.handlers;
	  @*/
	PeriodicEventHandler[] getHandlers() // /*@ spec_public @*/
	{
		return handlers;
	}

	/**
	 * 
	 * @return The duration
	 */
	/*@ // implementation specification 
	  behavior   
	    requires true; 
	    assignable \nothing;      
	    ensures \result == this.duration;
	  @*/
	RelativeTime getDuration() // /*@ spec_public @*/
	{
		return duration;
	}
}
