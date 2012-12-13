/**************************************************************************
 * File name  : AbsoluteTime.java
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
 * An object that represents a specific point in time given by milliseconds plus nanoseconds
 * past some point in time, an Epoch,  fixed by the clock. <br>
 * The default clock is the default real-time clock. <br>
 * A time object in normalized form represents negative time if both components are
 * nonzero and negative, or one is nonzero and negative and the other is zero. <br>
 * For add and subtract operations, negative values behave as they do in Java arithmetic.
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment
 *  - semantic issue: Java arithmetic is assumed for numeric values <br>
 *  - semantic issue: The clock of <code>RelativeTime</code> in add and subtract is ignored (RTSJ throws an exception) <br>
 *  - semantic issue: If <code>RelativeTime</code> time in add is null, an <code>IllegalArgumentException</code> is thrown <br>
 *  - semantic issue: If <code>AbsoluteTime</code> time in subtract is null, an <code>IllegalArgumentException</code> is thrown <br>
 *  - semantic issue: If clocks are different in <code>AbsoluteTime</code> subtract, an <code>IllegalArgumentException</code> is thrown.
 *   
 */
@SCJAllowed
public class AbsoluteTime extends HighResolutionTime 
{
  /**
   * Constructs an <code>AbsoluteTime</code> object with time millisecond and nanosecond components
   * past the real-time clock's Epoch. <br> 
   * The actual value is the result of parameter normalization.
   * 
   * @param millis is the desired value for the millisecond component
   * @param nanos is the desired value for the nanosecond component.
   */
  public AbsoluteTime(long millis, int nanos)
  {
    super (millis, nanos, null);
  }
 
  /**
   * Makes a new object that is a copy from the parameter <code>time</code>.
   * 
   * @param time the object copied
   */
  public AbsoluteTime (AbsoluteTime time)
  {
    super (time.getMilliseconds(), time.getNanoseconds(), 
           (Clock) time.getClock());
  }

  /**
   * Constructs an <code>AbsoluteTime</code> object with time millisecond and nanosecond components
   * past the clock's Epoch.
   * @param millis is the desired value for the millisecond component
   * @param nanos is the desired value for the nanosecond component.
   * @param clock is the desired value for the clock
   */
  public AbsoluteTime (long millis, int nanos, Clock clock)
  {
    super (millis, nanos, clock);
  }
  
  /**
   * Equivalent to <code>AbsoluteTime(0,0,clock)</code>.
   * @param clock is the desired value for the clock
   */
  public AbsoluteTime (Clock clock)
  {
    super (0, 0, clock);
  }
  
  /**
   * Creates a new object representing the result of adding millis and nanos 
   * to the values from this and normalizing the result.
   * @param millis is the number of milliseconds to be added to this.
   * @param nanos is the number of nanoseconds to be added to this.
   * @return a new <code>AbsoluteTime</code> object whose time is the normalization of this 
   *         plus millis and nanos.
   */
  public AbsoluteTime add (long millis, int nanos)
  {
    return new AbsoluteTime(this.millis + millis,
                            this.nanos  + nanos);
  }

  /**
   * Returns an object containing the value resulting from adding millis and nanos to the
   * values from this and normalizing the result. <br> 
   * The result is placed in <code>dest</code>.
   *  
   * @param millis is he number of milliseconds to be added to this.
   * @param nanos is the number of nanoseconds to be added to this.
   * @param dest if null a new <code>dest</code> object is created, the result is placed there and returned.
   * @return the <code>dest</code> object
   */
  public AbsoluteTime add (long millis, int nanos, AbsoluteTime dest)
  {
    if (dest == null) 
      dest = new AbsoluteTime(this.clock);
    dest.set(this.millis + millis, this.nanos  + nanos);
    
    return dest;
  }

  /**
   * Creates a new instance of <code>AbsoluteTime</code> representing the result of 
   * adding <code>time</code> to the value of this and normalizing the result.
   * 
   * @param time the time to add to this.
   * @return a new object whose time is the normalization of this plus the parameter <code>time</code>.
   */
  public AbsoluteTime add (RelativeTime time)
  {	
	if (time == null)
	  throw new IllegalArgumentException("time is null");
	
    return new AbsoluteTime(this.millis + time.getMilliseconds(),
                            this.nanos + time.getNanoseconds(),
                            this.clock);
  }

  /**
   * Returns an object containing the value resulting from adding <code>time</code> to the value of this
   * and normalizing the result. The result is placed in <code>dest</code>.
   * 
   * @param time is the time to add to this
   * @param dest if null a new <code>dest</code> object is created, the result is placed there and returned.
   * @return the <code>dest</code> object
   */
  public AbsoluteTime add (RelativeTime time, AbsoluteTime dest)
  {
    if (time == null)
      throw new IllegalArgumentException("time is null");
    
    return add(time.getMilliseconds(), time.getNanoseconds(), dest);
  }

  /**
   * Creates a new instance of <code>RelativeTime</code> representing the result of subtracting <code>time</code>
   * from the value of this and normalizing the result.
   * 
   * @param time is the time to subtract from this.
   * @return a new object whose time is the normalization of this minus <code>time</code>.
   */
  public RelativeTime subtract (AbsoluteTime time)
  {
    if (time == null)
      throw new IllegalArgumentException("time is null");
    if (this.clock != time.clock)
  	  throw new IllegalArgumentException("time.clock mismatch with this.clock");
    
    return new RelativeTime(this.millis - time.getMilliseconds(),
                            this.nanos  - time.getNanoseconds(),
                            this.clock); 
  }

  /**
   * Returns an object containing the value resulting from subtracting <code>time</code> from the value
   * of this and normalizing the result. The result is placed in <code>dest</code>.
   * 
   * @param time is the time to subtract from this.
   * @param dest if null a new <code>dest</code> object is created, the result is placed there and returned.
   * @return the <code>dest</code> object
   */
  public RelativeTime subtract (AbsoluteTime time, RelativeTime dest)
  // dest = this - time
  {
    if (time == null)
      throw new IllegalArgumentException("time is null");
    if (this.clock != time.clock)
    	  throw new IllegalArgumentException("time.clock mismatch with this.clock");
    
    if (dest != null)
    {
      dest.set(this.millis - time.getMilliseconds(),
               this.nanos  - time.getNanoseconds()); 
    }
    else
    {
      dest = this.subtract (time);
    }
      
    return dest;
  }

  /**
   * Creates a new instance of <code>AbsoluteTime</code> representing the result of subtracting <code>time</code>
   * from the value of this and normalizing the result.
   * 
   * @param time is the time to subtract from this.
   * @return a new <code>AbsoluteTime</code> object whose time is the normalization of this minus <code>time</code>.
   */
  public AbsoluteTime subtract (RelativeTime time)
  {
    if (time == null)
      throw new IllegalArgumentException("time is null");
    
    return new AbsoluteTime (this.millis - time.getMilliseconds(),
                             this.nanos  - time.getNanoseconds(),
                             this.clock);    
  }

  /**
   * Returns an object containing the value resulting from subtracting <code>time</code> from the value of this
   * and normalizing the result. The result is placed in <code>dest</code>.
   * 
   * @param time is the time to subtract from to this
   * @param dest if null a new <code>dest</code> object is created, the result is placed there and returned.
   * @return the <code>dest</code> object
   */
  public AbsoluteTime subtract (RelativeTime time, AbsoluteTime dest)
  {
    if (time == null)
      throw new IllegalArgumentException("time is null");
    if (this.clock != time.clock)
  	  throw new IllegalArgumentException("time.clock mismatch with this.clock");
    
    if (dest == null)
    {
      dest = new AbsoluteTime (this.millis - time.getMilliseconds(),
                               this.nanos  - time.getNanoseconds(),
                               this.clock);  
    }
    else
    {
      dest.set(this.millis - time.getMilliseconds(),
               this.nanos  - time.getNanoseconds());
    }
    return dest;
  }
  
  /**
   * Normalize the values of millis and nanos in this.
   * The method is called in <code>class RealtimeClock</code>. <br>
   * From: http://flex.cscott.net/Harpoon/Realtime/doc/src-html/javax/realtime/HighResolutionTime.html
   */
  final void normalize() 
  {    
    if (nanos < 0) 
    {
      nanos = Math.abs(nanos);
      millis = millis - nanos/1000000;
      nanos = nanos % 1000000;
      if (nanos > 0) {
        millis--;
        nanos = 1000000 - nanos;
      }
    }
    else 
    {
      millis = millis + nanos/1000000;
      nanos = nanos%1000000;
    }
  }
}
