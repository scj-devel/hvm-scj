/**************************************************************************
 * File name  : HandlerSet.java
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

import java.util.Iterator;
import java.util.Vector;

import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;

/**
 * This collection class of handlers is created in mission memory and used 
 * by the mission.
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment 
 *  - implementation issue: infrastructure class; not part of the SCJ specification.
 */
@SCJAllowed(Level.INFRASTRUCTURE)  
class HandlerSet
{
  Vector<ManagedEventHandler> eventHandlers = 
    new Vector<ManagedEventHandler>();  // the handlers in this mission  
  
  static Mission mission;  // only one mission at a time; no sub-mission
  
 
  /**
   * Handler count for the mission.
   * 
   * The handlerCount is incremented by addHandler,
   * and is decremented by PriorityScheduler when handler is terminated.
   * 
   * Mission.runCleanup is waiting until handlerCount == 0
   */
  int handlerCount;  // only one mission at a time; no sub-mission
  
  HandlerSet (Mission mission)
  {
    HandlerSet.mission = mission;
  }
  
  static HandlerSet getHandlerSet()
  { 
    // get current mission memory    
    MissionMemory missionMem = Mission.currMissSeq.missionMemory;
    return missionMem.getHandlerPortal();
  }
  
  Mission getMission() 
  {
    return mission;
  }
  
  void addHandler (ManagedEventHandler eh)
  {
    eventHandlers.add(eh); 
    handlerCount++;
  }
  
  void terminateHandlers() // stop all handlers; called in CyclicExecutive.runCleanup
  {
    for (Iterator<ManagedEventHandler> itr = eventHandlers.iterator(); 
         itr.hasNext(); )
    {
      ManagedEventHandler handler = (ManagedEventHandler) itr.next();
      handler.cleanup();
      itr.remove();
      handlerCount--;
    }
  }
  
  void removeHandler (ManagedEventHandler eh)
  {
	  for (Iterator<ManagedEventHandler> itr = eventHandlers.iterator(); 
			itr.hasNext(); )
      {
        ManagedEventHandler handler = (ManagedEventHandler) itr.next();
        if (handler == eh)
        {
          handler.cleanup();
          itr.remove();
          handlerCount--;
        }
      }
	  
  }
  
  void removeAperiodicHandlers() // remove all aperiodic handlers; 
  // called in PriorityScheduler.move()
  {
    for (Iterator<ManagedEventHandler> itr = eventHandlers.iterator(); 
         itr.hasNext(); )
    {
      ManagedEventHandler handler = (ManagedEventHandler) itr.next();
      if (handler instanceof AperiodicEventHandler)
      {
	      handler.cleanup();
	      itr.remove();
	      handlerCount--;
      }
    }
  }
  

  public String toString()
  {
    return "Mission: " + eventHandlers.size() + " handlers";
  }
}