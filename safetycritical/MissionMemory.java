/**************************************************************************
 * File name  : MissionMemory.java
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
 * Mission memory is a linear-time scoped memory area that remains active
 * through the lifetime of a mission.
 * <p>
 * This class is final. It is instantiated by the infrastructure and 
 * entered by the infrastructure.
 * Hence, non of its constructors are visible.
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment 
 *  - SCJ issue: This class should not be public. <p>
 *  - SCJ issue: The portal methods specified in <code>interface 
 *      ScopedAllocationContext</code> must be implemented in <code>class 
 *      ManagedMemory ... implements ScopedAllocationContext</code>. <br>
 *      In SCJ Draft, Section 7.3.9, it seems as if the methods are 
 *      implemented in <code>MissionMemory</code>.
 *   
 */
@SCJAllowed(Level.INFRASTRUCTURE)
public final class MissionMemory extends ManagedMemory 
{  
  MissionMemory(int size) 
  {    
    super(size);
  }
  
  void setHandlerPortal (HandlerSet handlerSet)
  {
    setPortal (handlerSet);
  }
  
  HandlerSet getHandlerPortal ()
  {
    return (HandlerSet) getPortal();
  }
   
  void enterToInitialize (final Mission mission)
  {
    executeInArea (new Runnable() {  
      public void run() {
        mission.runInitialize(MissionMemory.this);
      }
    });
  }
  
  void enterToExecute (final Mission mission)
  {
    executeInArea (new Runnable() {  
      public void run() {
        mission.runExecute(MissionMemory.this);
      }
    });
  }
  
  void enterToCleanup (final Mission mission)
  {
	executeInArea (new Runnable() {  
      public void run() {
        mission.runCleanup(MissionMemory.this);
      }
    });
	
	resetMemoryArea();
	//devices.Console.println ("MissionMemory.enterToCleanup: mission mem reset");   
  }
}
