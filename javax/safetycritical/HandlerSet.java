/**************************************************************************
 * File name  : HandlerSet.java
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
import javax.scj.util.Const;

/**
 * This collection class of handlers is created in mission memory and used 
 * by the mission.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 * 
 * @scjComment 
 *  - implementation issue: infrastructure class; not part of the SCJ specification.
 */
@SCJAllowed(Level.INFRASTRUCTURE)  
class HandlerSet
{  
  ManagedEventHandler[] eventHandlers = new ManagedEventHandler[Const.DEFAULT_HANDLER_NUMBER];
  int noOfHandlers = 0;
  
  ScjProcess[] scjProcesses = new ScjProcess[Const.DEFAULT_HANDLER_NUMBER];  
   
  /**
   * Handler count for the mission. Only one mission at a time; no sub-mission
   * 
   * The handlerCount is incremented by addHandler,
   * and is decremented by PriorityScheduler when handler is terminated.
   * 
   * Mission.runCleanup is waiting until handlerCount == 0
   */
  int handlerCount;  
  
  HandlerSet ()
  {
  }

  /*@ 
    behavior
      requires eh != null;
      ensures this.contains(eh);
    @*/
  void addHandler (ManagedEventHandler eh)
  {
	if (!contains (eh))
	{
      eventHandlers[noOfHandlers] = eh;
      noOfHandlers++;
      handlerCount++;
	}
  }
  
  boolean contains (ManagedEventHandler eh)
  {
	for (int i = 0; i < noOfHandlers; i++)
	{
	  if (eventHandlers[i] == eh)
		return true;
	  }
	  return false;
  }
  
  void terminateHandlers() // stop all handlers; called in CyclicExecutive.runCleanup
  {
    for (int i = noOfHandlers; i > 0; i--)
    {
      eventHandlers[i-1].cleanUp();      
      eventHandlers[i-1] = null;
      handlerCount--;
    }
  }
  
  void removeHandler (ManagedEventHandler eh)  // called in PriorityScheduler.move
  {
	  for (int i = 0; i < noOfHandlers; i++)
      {        
        if (eventHandlers[i] == eh)
        {
          eventHandlers[i].cleanUp(); 
          eventHandlers[i] = null;
          PriorityScheduler.instance().pFrame.queue.remove(scjProcesses[i]);
          scjProcesses[i] = null;
          handlerCount--;
        }
      }	  
  }
    
  void removeAperiodicHandlers() // remove all aperiodic handlers; 
  // called in PriorityScheduler.move()
  {
	for (int i = 0; i < noOfHandlers; i++)
    {
      if (eventHandlers[i] instanceof AperiodicEventHandler)
      {
    	  eventHandlers[i].cleanUp();
    	  PriorityScheduler.instance().pFrame.queue.remove(scjProcesses[i]);
	      handlerCount--;
      }
    }
  }
  
  int indexOf (ManagedEventHandler eh)
  {
	for (int i = 0; i < noOfHandlers; i++)
	{
		if (eventHandlers[i] == eh)
			return i;
	}
	return -1;
  } 

  public String toString()
  {
    return "Mission: " + noOfHandlers + " handlers";
  }
}