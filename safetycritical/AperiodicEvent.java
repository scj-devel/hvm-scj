/**************************************************************************
 * File name: AperiodicEvent.java
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

import java.util.Vector;

import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.Phase;
import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.annotate.SCJRestricted;

/**
 * @version 1.0; - May 2012
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 *  @scjComment
 *  - The super-class <code> AsyncEvent </code> is superflous. 
 *    It is package protected in this version. <br><br>
 *  - Semantic clarification: the attached handlers are a set.(handlers cannot be added twice) <br>
 *  - Semantic issue: Is fire count a general semaphore for each attached handler. I.e. fire count equals number of releases? <br><br>
 *  - Implementation issue: <code>fire</code> must not allocate memory. Thus if the object has a list of handlers, then: <br>
 *  <ul>
 *    <li> a) a scope violation, or <br>
 *    <li> b) the object and the list have to be in mission memory and handlers are added during initialization only. 
 *            This prevents dynamic addition and removal for Level 2, or  <br>
 *    <li> c) <code> fire </code> is implemented by a search through 
 *            <code>AperiodicEventHandler</code>s and their event-lists.
 *   </ul><br>
 * - Perhaps it would be wiser to eliminate Event, and introduce the 
 *   <code>fire</code> method on the handler ? 
 */
@SCJAllowed(Level.LEVEL_1)
public class AperiodicEvent extends AsyncEvent
{
  /**
   * The handlers associated with this event.
  */
  private Vector<AperiodicEventHandler> handlers;
  
  
  /* Equivalent set for use by the fire iteration
  */
  private AperiodicEventHandler[] hs;


  public AperiodicEvent()
  {
	  super();
	  handlers = new Vector<AperiodicEventHandler>(); 
  }

  /**
   * Adds a handler to the set of active handlers
   *
   * @param handler is the handler to be added
   */
    @SCJAllowed(Level.INFRASTRUCTURE)
    @SCJRestricted(Phase.INITIALIZE)
    void addHandler(AperiodicEventHandler handler) throws IllegalArgumentException
    {
      //devices.Console.println ("AperiodicEvent.addHandler" + handlers);
      try {
    	if (!handlers.contains(handler))
          handlers.add(handler);
      } catch(Exception e) {
  		throw new IllegalArgumentException("bad handler added");
      }
    }

    /**
     * Transfers set of active handlers to an array so fire does not 
     * have to allocate memory
     */
    @SCJAllowed(Level.INFRASTRUCTURE)
    @SCJRestricted(Phase.INITIALIZE)
    void setActive()
    {
      hs = new AperiodicEventHandler[handlers.size()];
      handlers.toArray(hs);
    } 

    /**
     * Removes a handler from the set of active handlers
     *
     * @param handler is the handler to be removed from the set
     */
    @SCJAllowed(Level.INFRASTRUCTURE)
    @SCJRestricted(Phase.CLEANUP)
    void removeHandler(AperiodicEventHandler handler)
    {
      handlers.remove(handler);
    }
    /**
     * Fire this event, i.e., releases the execution of all handlers 
     * that were added to this event.
     */
    @SCJAllowed(Level.LEVEL_1)
    @SCJRestricted(Phase.EXECUTE)
    public final void fire()
    {
      for (int i= hs.length-1; i>= 0; i--)
      {
    	//devices.Console.println("AperiodicEvent.fire");
        hs[i].fire();
      }
     }
}

