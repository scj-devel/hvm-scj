/**************************************************************************
 * File name  : AllocationContext.java
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
 *************************************************************************/

package javax.realtime;

import javax.safetycritical.annotate.SCJAllowed;

/**
 * All memory allocation takes places from within an allocation context. 
 * This interface defines the operations available on all allocation contexts. 
 * Allocation contexts are implemented by memory areas.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 * 
 * @scjComment 
 *  - semantic issue: Why is this interface introduced as a public entity? 
 *      No applications declare or access memory areas? 
 */

@SCJAllowed
public interface AllocationContext
{
  /**
   * @return the amount of memory in bytes consumed so far in this memory area.
   */
  /*@ 
    public behavior
      requires true;
      ensures \result >= 0; 
    @*/
  @SCJAllowed
  public long memoryConsumed(); 
  
  /**
   * @return the amount of memory in bytes remaining in this memory area.
   */
  /*@ 
    public behavior
      requires true;
      ensures \result >= 0; 
    @*/
  @SCJAllowed
  public long memoryRemaining();
  
  
  /**
   * @return the current size of this memory area in bytes.
   */
  /*@ 
    public behavior
      requires true;
      ensures \result >= 0;
      ensures \result == memoryRemaining()+ memoryConsumed();
  @*/
  @SCJAllowed
  public long size();
}
