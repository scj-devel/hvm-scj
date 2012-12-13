/**************************************************************************
 * File name  : AsyncEvent.java
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
import javax.safetycritical.annotate.Level;

/**
 * This is the base class for all asynchronous events.
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment
 *  - SCJ issue: No <code>AbstractAsyncEvent</code> class; superfluous abstraction <br>
 *  - SCJ issue: This class is a superfluous abstraction for <code>AperiodicEvent.</code> <br><br>
 *  - SCJ semantic issue: Is fire count a general semaphore for each attached handler. 
 *      I.e. fire count equals number of releases ? <br> 
 *  - SCJ semantic issue: is fire allowed before or during initialization ? <br>
 */

@SCJAllowed(Level.LEVEL_1)
public class AsyncEvent
{
   /**
   * Fire this event, i.e., releases the execution of all handlers that were added to this event.
   */
  public void fire() 
  {
  }
}
  
 
