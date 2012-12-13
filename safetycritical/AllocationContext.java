/**************************************************************************
 * File name  : AllocationContext.java
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
 * This is the base interface for all memory areas. <br>
 * It is a generalization of the Java Heap to allow for alternate forms of 
 * memory management.<br> 
 * All memory areas implement this interface.
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment 
 *  - semantic issue: Why is this interface introduced as a public entity? 
 *      No applications declare or access memory areas? <br>
 *  - semantic issue: <code>IllegalThreadStateException</code> 
 *      (cf. SCJ Draft D.1.1 INTERFACE <code>AllocationContext</code>) is not included, 
 *      since only infrastructure can invoke <code>enter</code> methods <br>
 *  - semantic issue: <code>ThrowBoundaryError</code> is not included. 
 *      It must be the responsibility of the exception handlers for
 *      <code>enter</code> methods to make a local copy of the exception 
 *      before propagating it. <br> 
 *   - semantic issue: should <code>newArray</code> and 
 *       <code>newInstance</code> be SCJAllowed. Should they be there at all?
 */

@SCJAllowed
public interface AllocationContext
{
/**
  * Executes some logic with this memory area as the default allocation context.
  * The effect on the scope stack is specified in the implementing classes.
  * 
  * @throws IllegalArgumentException Thrown if <code>logic</code> is null.
  * 
  * @param logic is the runnable object whose <code>run()</code> method should be executed in this memory area.
  */
  @SCJAllowed(Level.INFRASTRUCTURE)
  public void executeInArea (Runnable logic)
    throws java.lang.IllegalArgumentException;
  
  /**
   * Gets the amount of allocated memory in this memory area.
   * 
   * @return The amount of memory consumed in bytes.
   */
  @SCJAllowed
  public long memoryConsumed(); 
  
  /**
   * Gets the amount of memory available for allocation in this memory area.
   * 
   * @return The amount of memory remaining in bytes.
   */
  @SCJAllowed
  public long memoryRemaining();
  
  /**
   * Creates a new array of the given type in this memory area. 
   * This method may be concurrently used by multiple threads.
   * 
   * @param type is the class of object this array should hold
   * @param number is the number of elements the array should have.
   * @return The new array of type <code>type</code> and size <code>number</code>.
   * 
   * @throws java.lang.IllegalArgumentException Thrown 
   *   when <code>number</code> is negative or <code>type</code> is null
   * @throws java.lang.OutOfMemoryError Thrown when there is not enough memory left.
   */
  @SCJAllowed(Level.INFRASTRUCTURE)
  public Object newArray(@SuppressWarnings("rawtypes") Class type, int number)
    throws java.lang.IllegalArgumentException, java.lang.OutOfMemoryError;
  
  /**
   * Creates a new instance of a class in this memory area using its default constructor.
   * 
   * @param type is the class of object to be created 
   * @return An object of class type.
   * 
   * @throws java.lang.IllegalAccessException
   * @throws java.lang.InstantiationException
   * @throws java.lang.OutOfMemoryError Thrown when there is not enough memory left.
   */
  @SCJAllowed(Level.INFRASTRUCTURE)
  public Object newInstance(@SuppressWarnings("rawtypes") Class type) 
    throws java.lang.IllegalAccessException, 
           java.lang.InstantiationException,
           java.lang.OutOfMemoryError;
  
  /**
   * Gets the size of this memory area.
   * 
   * @return The current size of this memory area.
   */
  @SCJAllowed
  public long size();
}
