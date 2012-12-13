/**************************************************************************
 * File name  : ScopedAllocationContext.java
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
 * A scoped allocation context is one whose associated objects have their
 * memory reclaimed when no schedulable object is active within it.
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment
 *  - SCJ issue: <code>ScopedAllocationContext</code> does not inherit 
 *      from <code>AllocationContext</code> because of a simplified
 *      structure of the <code>MemoryArea</code> classes. <br>
 *  - SCJ issue: simplification of the <code>MemoryArea</code> class 
 *      hierarchy, cf. SCJ Draft, Figure 7.2: Overview of MemoryArea-Related Classes. 
 *      Omitted the following two RTSJ classes:
 *      <ul> <code>
 *      <li> javax.realtime.ScopedMemory
 *      <li> javax.realtime.LTMemory
 *      </code> </ul>
 *  - semantic issue: Why is this interface introduced as a public entity? 
 *      No applications declare or access portal objects. 
 */
@SCJAllowed
/*public*/ interface ScopedAllocationContext
{
  /**
   * Returns a reference to the portal object of this memory area.
   * @return A reference to the portal object or null if there is no portal object.
   */
  Object getPortal(); // throws ...
  
  /**
   * Sets the portal object of this memory area.
   * @param value The object which will become the portal for this.
   */
  void setPortal(Object value); // throws ...
}
