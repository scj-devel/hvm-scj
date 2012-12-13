/**************************************************************************
 * File name  : Clock.java
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
 *  Copyright 2012 
 *  @authors  Anders P. Ravn, Aalborg University, DK
 *            Stephan E. Korsholm and Hans S&oslash;ndergaard, 
 *              VIA University College, DK
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

/**
 * A clock marks the passing of time. It has a concept of <i>now</i> that 
 * can be queried through <code>Clock.getTime()</code>. <br>
 * The <code>Clock</code> instance returned by <code>getRealtimeClock()
 * </code> may be used in any context that requires a clock. 
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment 
 *  - SCJ issue: User defined clocks? <br>
 *  - SCJ issue: Is the clock implementing an event queues 
 *      - i.e. implementing an <code>AsyncEvent</code> ? <br> 
 *      When a clock drives events, it implements a periodic handler. 
 *      If a mix of an aperiodic and a periodic handler is essential,
 *      a unification at the level of aperiodic event handler seems more general. 
 *      I.e. set periods for aperiodic event handlers. <br>
 *  - SCJ issue: The semantics of <code>getTime</code> with <code>dest
 *      </code> differs from <code>getTime</code> with no arguments.
 *      It is made consistent below.
 *  <p>
 *  - semantic issue: <code>EpochOffSet</code> must be relative to 
 *      <code>RealTimeClock</code>? 
 *  <p>
 *  - implementation issue:
 *   The constructor <code> public Clock() </code> is public, 
 *   if user-defined clocks are allowed, else package protected. <br>
 *   - implementation issue: Omitted ? , - the  following methods are not implemented:<br>
 *   <ul>
 *   <code>
 *   <li>public abstract boolean drivesEvents();<br>
 *   <li>public abstract void registerCallBack(AbsoluteTime t, ClockCallBack clockEvent);<br>
 *   <li>public abstract boolean resetTargetTime(AbsoluteTime time);
 *   </code>
 *   </ul>
 */
@SCJAllowed
public abstract class Clock 
{
  /**
   * This constructor for the abstract class may allocate objects within 
   * the same <code>MemoryArea</code> that holds the implicit this argument.
   * Allocates resolution here.
   */
  public Clock()
  {
  }
  
  /**
   * 
   * @return The singleton instance of the default <code>Clock</code>.
   */
  public static Clock getRealtimeClock()
  {
    return RealtimeClock.instance();
  }
  
  /**
   * Creates a new object representing now of this clock. 
   *
   * @return A new <code>AbsoluteTime</code> object whose time is now of this clock. 
   */
  @SCJAllowed
  public abstract AbsoluteTime getTime ();
  
  /**
   * Stores now of this clock in <code>dest</code>.
   * 
   * @param  dest If <code>dest</code>is null, allocate a new 
   *   <code>AbsoluteTime</code> instance to hold the returned value.
   * 
   * @return The resulting <code>dest</code> value.
   */
  @SCJAllowed
  public abstract AbsoluteTime getTime (AbsoluteTime dest);
  
  /**
   * Gets the resolution of the clock, the nominal interval between ticks.
   * 
   * @return The initially allocated resolution object.
   */
  @SCJAllowed
  public abstract RelativeTime getResolution ();
  
  /**
   * Returns the relative time of the offset of the epoch of this clock 
   * from the Epoch of the <code>RealtimeClock</code>.
   * 
   * @return The offset of this clock epoch from the <code>RealtimeClock</code>.
   */
  @SCJAllowed
  public abstract RelativeTime getEpochOffset ();
   
  /**
   * Gets the resolution of the clock, the nominal interval between ticks 
   * and stores the result in <code>dest</code>.
   * 
   * @param  dest If <code>dest</code> is null, allocate a new 
   *   <code>RelativeTime</code> instance to hold the returned value.
   * 
   * @return The resulting <code>dest</code> value.
   */
  @SCJAllowed
  public abstract RelativeTime getResolution(RelativeTime dest);
  
  /**
   * @scjComment
   *   Omitted?, - not implemented
   */
  @SCJAllowed
  public abstract boolean drivesEvents();
  
  /**
   * @scjComment
   *   Omitted?, - not implemented
   */
  @SCJAllowed(Level.LEVEL_1)
  public abstract void registerCallBack(AbsoluteTime t, ClockCallBack clockEvent);
  
  /**
   * @scjComment
   *   Omitted?, - not implemented
   */
  @SCJAllowed(Level.LEVEL_1)
  public abstract boolean resetTargetTime(AbsoluteTime time);

}
