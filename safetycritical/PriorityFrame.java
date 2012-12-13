/**************************************************************************
 * File name  : PriorityFrame.java
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
 * This frame class holds a ready queue and a sleeping queue 
 * for the priority scheduler. <br>
 * The class is package protected because it is not part of the SCJ 
 * specification.
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
class PriorityFrame
{
  PriorityQueue readyQueue;

  SleepingQueue sleepingQueue; // a priority queue ordered after
                               // ScjProcess.nextActivationTime;
  PriorityFrame (int queueSize)
  {
    // create the queues ...
    readyQueue    = new PriorityQueue(queueSize);      
    sleepingQueue = new SleepingQueue(queueSize);
  }

  void addProcess (ScjProcess process)  
  {
    if (process.target instanceof PeriodicEventHandler)
    {
      //devices.Console.println("PrFrame.addProcess, periodic "); // + process);
      
      PeriodicEventHandler pevh = (PeriodicEventHandler) process.target;
      RelativeTime start = ((PeriodicParameters) pevh.release).start;
      
      if (start.millis == 0 && start.nanos == 0)
      {
        readyQueue.insert(process);
        process.state = ScjProcess.State.READY;
      }
      else
      {
        sleepingQueue.insert(process);
        process.state = ScjProcess.State.SLEEPING;
      }
    }

    else if (process.target instanceof MissionSequencer)
    {
      //devices.Console.println("PrFrame.addProcess, missSeq "); // + process);
      readyQueue.insert(process);
      process.state = ScjProcess.State.READY;
    }
    
    else if (process.target instanceof AperiodicEventHandler)
    {
      //devices.Console.println("PrFrame.addProcess, aperiodic "); // + process);
      process.state = ScjProcess.State.BLOCKED;
    }
    
    else
    {
      throw new IllegalArgumentException("PriorityFrame.addHandler: UPS: another handler??");
    }   
  }  
}
