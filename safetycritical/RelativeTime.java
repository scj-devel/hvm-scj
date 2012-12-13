/**************************************************************************
 * File name  : RelativeTime.java
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

import javax.safetycritical.annotate.SCJAllowed;

/**
 * An object that represents a duration in milliseconds and nanoseconds
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment
 *  - semantic issue: <code>Clock</code> is meaningless and ignored (RTSJ thinks otherwise).
 *   
 */
@SCJAllowed
public class RelativeTime extends HighResolutionTime 
{
  /**
   * Equivalent to <code>RelativeTime(0,0)</code>.
   */
  public RelativeTime()
  {
    this (0, 0);
  }

  /**
   * Constructs a <code>RelativeTime</code> object representing a duration based on the parameters.
   * @param millis is the milliseconds.
   * @param nanos is the nanoseconds.
   */
  public RelativeTime(long millis, int nanos)
  {
    this (millis, nanos, null);
  } 
  
  /**
   * Equivalent to <code>new RelativeTime(0, 0, clock)</code>.
   * @param clock is the clock argument.
   */
  public RelativeTime (Clock clock)
  {
    this (0, 0, clock);
  }
  
  /**
   * Constructs a <code>RelativeTime</code> object representing a duration based on the parameters.
   * @param millis is the milliseconds
   * @param nanos is the nanoseconds
   * @param clock is the clock
   */
  public RelativeTime(long millis, int nanos, Clock clock)
  {
    super (millis, nanos, clock);
  }
  
  /**
   * Makes a new <code>RelativeTime</code> object from the given <code>RelativeTime</code> object.
   * 
   * @param time is the copied object.  
   */
  public RelativeTime(RelativeTime time)
  { 
    this (time.getMilliseconds(), time.getNanoseconds(), time.getClock());
  }
  
  /**
   * Creates a new object representing the result of adding millis and nanos to the values
   * from this
   * @param millis is the milliseconds to be added.
   * @param nanos is the nanoseconds to be added.
   * @return the new object with the added durations.
   */ 
  public RelativeTime add (long millis, int nanos)
  {
    return new RelativeTime(this.millis + millis, this.nanos + nanos);
  }

  /**
   * Creates a new object representing the result of adding <code>time</code> to the value of this
   * @param time is the duration to be added.
   * @return the new object with the added durations.
   */
  public RelativeTime add (RelativeTime time)
  {
	if (time == null)
	  throw new IllegalArgumentException("time is null");
	
    return new RelativeTime(this.millis + time.getMilliseconds(),
                            this.nanos + time.getNanoseconds());
  }

  /**
   * Returns an object containing the value resulting from adding millis and nanos to this.
   * The result is stored in <code>dest</code>.
   * @param millis millis is the milliseconds to be added.
   * @param nanos is the nanoseconds to be added.
   * @param dest is the destination, if initially null a <code>new RelativeTime()</code> object is created.
   * @return the <code>dest</code> object with the resulting value.
   */
  public RelativeTime add (long millis, int nanos, RelativeTime dest)
  {
    if (dest == null) {
      dest = new RelativeTime(this.millis + millis, this.nanos  + nanos);
    } else {
      dest.set(this.millis + millis, this.nanos  + nanos);
    }
    return dest;
  }

  /**
   * Returns an object containing the value resulting from adding <code>time</code> to this.
   * The result is stored in <code>dest</code>.
   * @param time is the time to be added
   * @param dest is the destination, if null a <code>new RelativeTime()</code> object is created
   * @return the <code>dest</code> object with the resulting value.
   */
  public RelativeTime add (RelativeTime time, RelativeTime dest)
  {
    if (time == null)
      throw new IllegalArgumentException("time is null");
    
    return this.add(time.getMilliseconds(), time.getNanoseconds(), dest);
  }

  /**
   * Creates a new <code>RelativeTime</code> object representing the result 
   * of subtracting <code>time</code> from the value of this.
   * @param time the value to be subtracted.
   * @return the difference between durations.
   */
  public RelativeTime subtract (RelativeTime time)
  {
    if (time == null)
      throw new IllegalArgumentException("time is null");
    
    return new RelativeTime(this.millis - time.getMilliseconds(),
                            this.nanos  - time.getNanoseconds());
  }

  /**
   * Creates a new <code>RelativeTime</code> object representing the result 
   * of subtracting <code>time</code> from the value of this
   * The result is stored in <code>dest</code>.
   * @param time is the duration to be subtracted
   * @param dest is the destination, if  null a <code>new RelativeTime()</code> object is created
   * @return the <code>dest</code> object with the resulting value.
   */
  public RelativeTime subtract (RelativeTime time, RelativeTime dest)
  {
    if (time == null)
      throw new IllegalArgumentException("time is null");
    
    if (dest == null) {
      dest = new RelativeTime(this.millis - millis, this.nanos  - nanos);
    } else {
      dest.set(this.millis - millis, this.nanos  - nanos);
    }
    return dest;
  }
}
