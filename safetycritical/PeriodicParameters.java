/**************************************************************************
 * File name  : PeriodicParameters.java
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
 * This class is restricted relative to RTSJ so that it allows the 
 * start time and the period to be set but not changed or queried.
 * 
 * @version 1.0; - May 2012
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment
 * 
 */
@SCJAllowed
public class PeriodicParameters extends ReleaseParameters 
{
  /**
   * Attributes that are accessible by the infrastructure.
   */
  RelativeTime start;
  RelativeTime period;
  
  /** 
   * Constructs a new object within the current memory area.  
   * The default deadline is the same value as period. There is no missHandler.
   *
   * @param start is the start time relative to start of the mission. 
   *   A null value defaults to an offset of zero milliseconds.
   * @param period is the period. 
   * 
   * @throws IllegalArgumentException if the period is null.
   */
  public PeriodicParameters(RelativeTime start, RelativeTime period)
  {
	super(period,null);
	
	if (start == null)
	  this.start = new RelativeTime();
	else
	  this.start  = new RelativeTime(start);
	
	if (period == null) throw new IllegalArgumentException("period is null");
	
	this.period = new RelativeTime(period);
  }
  
  /** Constructs a new object within the current memory area. 
   * 
   * @param start is the start time relative to start of the mission. 
   *   A null value defaults to an offset of zero milliseconds.
   * @param period is the period.
   * @param deadline is the deadline. If it is null, there is no deadline.
   * @param missHandler  is the event handler to be released if the deadline is missed. 
   *   A null value means that misses are not handled.
   * 
   * @throws IllegalArgumentException if the period is null.
   */
  @SCJAllowed(Level.LEVEL_1)
  public PeriodicParameters(RelativeTime start, RelativeTime period,
    RelativeTime deadline, AperiodicEventHandler missHandler)
  { 
	super (deadline, missHandler);
	  
	if (start == null)
	  this.start = new RelativeTime();
	else
	  this.start  = new RelativeTime(start);
	
	if (period == null) throw new IllegalArgumentException("period is null");
	
	this.period = new RelativeTime(period);
  }
}
