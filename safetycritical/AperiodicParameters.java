/**************************************************************************
 * File name: AperiodicParameters.java
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
 * SCJ supports no detection of minimum interarrival time violations, therefore only
 * aperiodic parameters are needed. Hence the RTSJ <code> SporadicParameters </code> class is
 * absent. <br>
 * Deadline miss detection is supported. <br>
 * The RTSJ supports a queue for storing the arrival of release events in order to enable
 * burst of events to be handled. This queue is of length 1 in SCJ and the overflow behaviour
 * is to throw an exception on the firing an associated event.
 * 
 * @version 1.0; - May 2012
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment
 *  - semantic issue: a missHandler has to be an <code>AperiodicEventHandler</code> <br>
 *  - semantic issue: the remarks about queue length belongs in the semantics of <code>AperiodicEvent</code>.
 */
@SCJAllowed(Level.LEVEL_1)
public class AperiodicParameters extends ReleaseParameters 
{  
  public AperiodicParameters(RelativeTime deadline, 
		                     AperiodicEventHandler missHandler)
  {
    super(deadline, missHandler);
  }
}
