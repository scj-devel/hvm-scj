/**************************************************************************
 * File name: AperiodicEventHandler.java
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
import javax.safetycritical.annotate.Phase;
import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.annotate.SCJRestricted;

/**
 * This class permits the automatic periodic execution of code that is 
 * bound to an aperiodic event.
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment
 *  - SCJ issue: Two constructors only.
 */
@SCJAllowed(Level.LEVEL_1)
public abstract class AperiodicEventHandler extends ManagedEventHandler
{
  private AperiodicEvent[] events;
  
  /**
   * The fire count manipulated by scheduler
   */
  int fireCount;

  @SCJAllowed(Level.LEVEL_1)
  @SCJRestricted(Phase.INITIALIZE)
  public AperiodicEventHandler (PriorityParameters priority,
                                AperiodicParameters release,
                                StorageParameters storage,
                                AperiodicEvent event) 
  {
    super (priority, release, storage);
    
    if (event == null) throw new IllegalArgumentException ("event is null");
    
    events = new AperiodicEvent[1];
    events[0] = event;
    events[0].addHandler(this);
    events[0].setActive();
  }
 
  @SCJAllowed(Level.LEVEL_1)
  @SCJRestricted(javax.safetycritical.annotate.Phase.INITIALIZE)
  public AperiodicEventHandler (PriorityParameters priority,
                                AperiodicParameters release,
                                StorageParameters storage,
                                AperiodicEvent[] events)
  {
    super (priority, release, storage);
    
    if (events == null) throw new IllegalArgumentException ("events is null");
    
    events = new AperiodicEvent[events.length];
    for (int i = 0; i < events.length; i++)
    {
      this.events[i] = events[i];
      this.events[i].addHandler(this);
      this.events[i].setActive();
    } 
  }
  
  public void cleanup() 
  { 
	//devices.Console.println ("AperiodicEventHandler.cleanup");
    for (int i = 0; i < events.length; i++)
      events[i].removeHandler(this);
    fireCount = 0;
    
    super.cleanup();
  }
  
  @SCJAllowed(Level.INFRASTRUCTURE)
  @SCJRestricted(Phase.INITIALIZE)
  public final void register()
  {
    super.register();
  }
  

  /**
   * Method used by <code>AperiodicEvent</code> to activate the handler
   */
  void fire ()
  {
    //devices.Console.println("AperiodicEventHandler.fire");
    fireCount++;
    sch.release(this);  
  }
}

