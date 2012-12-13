/**************************************************************************
 * File name  : HighResolutionTime.java
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

/**
 * <code>HighResolutionTime</code> is the base class for time given by milliseconds plus nanoseconds. <p>
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
 *  - semantic issue: clock is only meaningful for <code>AbsoluteTime</code> <br>
 *  - semantic issue: Java arithmetic means no overflow detection <br>
 *  - semantic issue: constructor should be protected <br>
 *  - semantic issue: method <code>set</code>: if parameter null, no Exception? <p>
 *  
 *  - implementation issue: 
 *      <code>public int compareTo (Object arg0)</code> is inherited from <code>interface Comparable</code> <br>
 *   - implementation issue: method <code>waitForObject</code> omitted ? <br>
 *   <ul>
 *   <li>
 *    <code>   
 *      public static void waitForObject(java.lang.Object target, HighResolutionTime time)
 *      throws java.lang.InterruptedException 
 *    </code>
 *   </ul>
 */
@SCJAllowed
@SuppressWarnings("rawtypes")
public abstract class HighResolutionTime implements Comparable
{
  Clock clock;
  long millis; 
  int nanos;
  
  HighResolutionTime(long millis, int nanos, Clock clock) 
  {
    setNormalized (millis, nanos);
    this.clock = clock;
  }
  
  /**
   * 
   * @return Returns a reference to the clock associated with this.
   */
  public Clock getClock()
  {
    return this.clock;
  }
  
  /**
   * 
   * @return the milliseconds component of the time represented by this.
   */
  public final long getMilliseconds ()
  {
    return this.millis;
  }

  /**
   * 
   * @return the nanoseconds component of the time represented by this.
   */
  public final int getNanoseconds ()
  {
    return this.nanos;
  }
  /**
   * Change the value represented by this to that of the given time.
   * 
   * @param time is the new value for this. If null, this is left unchanged.
   */
  public void set(HighResolutionTime time)
  { 
	if (time != null) 
	{
	  setNormalized(time.getMilliseconds(), time.getNanoseconds());
	  clock = time.clock;
    }
  }
  
  /**
   * Sets the millisecond component of this to the given argument, and the nanosecond
   * component of this to 0. The clock is unchanged.
   * @param millis is the new value of the millisecond component.
   */
  public void set (long millis)
  {
    this.millis = millis;
    this.nanos  = 0;
  }
  
  /**
   * Sets the millisecond and nanosecond components of this to the given arguments.
   * The clock is unchanged.
   * @param millis is the new value of the millisecond component.
   * @param nanos is the new value of the nanosecond component.
   */
  public void set (long millis, int nanos)
  {
    setNormalized(millis, nanos);
  }
 
  /**
   * @param time
   * @return is true if the parameter time is of the same type and has the same values as this.
   */
  public boolean equals (HighResolutionTime time)
  {
    if (time == null)
      return false;
    
    return ( this.getClass() == time.getClass() ) &&
           ( this.millis     == time.getMilliseconds() ) &&
           ( this.nanos      == time.getNanoseconds() ) &&
           ( this.clock      == time.getClock() );    
  }
  
  /**
   * Compares this with the specified HighResolutionTime time.
   * @param time is the second argument to the comparison.
   * @return the result of the comparison.
   */
  public int compareTo (HighResolutionTime time)
  {
    if (time == null)
      throw new IllegalArgumentException("null parameter");
    if (this.getClass() != time.getClass())
      throw new ClassCastException();
    if (this.clock != time.getClock())
      throw new IllegalArgumentException("Different clocks");
    
    if (this.millis < time.getMilliseconds())
      return -1;
    else if (this.millis > time.getMilliseconds())
      return 1;
    else if (this.nanos < time.getNanoseconds())
      return -1;
    else if (this.nanos > time.getNanoseconds())
      return 1;
    else
      return 0;
  }
  
  public int compareTo (Object arg0)
  {
    return compareTo((HighResolutionTime) arg0);
  }
 
  public String toString()
  {
    return ("(ms,ns) = (" + millis + ", " + nanos + ")");
  }
  
  /**
   * @scjComment
   *   Omitted ?; - not implemented
   */
  @SCJAllowed(Level.LEVEL_2)  
  public static void waitForObject(java.lang.Object target,
  HighResolutionTime time) throws java.lang.InterruptedException
  {
    // Not implemented
  }
  
  static final int NANOS_PER_MILLI = 1000000;
  /**
   * Sets the normalized values of millis and nanos in this. 
   */
  final void setNormalized(final long ms, final int ns) 
  {
   // nanos == nanos/NANOS_PER_MILLI + nanos%NANOS_PER_MILLI
	  
   millis = ms + ns/NANOS_PER_MILLI; 
   nanos = ns % NANOS_PER_MILLI;
    if (millis > 0 &&  nanos < 0) { 
      millis--; // millis >= 0
      nanos += NANOS_PER_MILLI;
    } else if (millis < 0 &&  nanos > 0){ 
      millis++; // millis <= 0
      nanos -= NANOS_PER_MILLI; 
    }
  }
}
