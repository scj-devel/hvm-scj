/**************************************************************************
 * File name  : PriorityParameters.java
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
 * Instances of this <code>PriorityParameters</code> class are assigned
 * to schedulable objects that are managed by schedulers which use a
 * single integer to determine execution order.
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment 
 *  
 */
@SCJAllowed
public class PriorityParameters extends SchedulingParameters 
{
  private int priority;
  
  /**
   * Creates an instance of <code>PriorityParameters</code> 
   * with the given <code>priority</code>.
   * @param priority The priority value.
   */
  public PriorityParameters(int priority)
  {
    this.priority = priority;
  }
  
  /**
   * Gets the priority value.
   * @return The priority.
   */
  public int getPriority()
  {
    return priority;
  }
  
  // infrastructure, 06 Dec. 2012, HSO
  
  void setPriority (int value)
  {
	  priority = value;
  }
}
