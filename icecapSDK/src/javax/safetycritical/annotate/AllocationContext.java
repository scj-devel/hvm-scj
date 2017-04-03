/**************************************************************************
 * File name  : AllocationContext.java
 * 
 * This file is part a SCJ implementation, 
 * based on SCJ Draft, Version 0.108 6. January 2017.
 *
 * It is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as  
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This SCJ implementation is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the  
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this SCJ implementation.  
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2017
 * @authors  Anders P. Ravn, Aalborg University, DK
 *           Stephan E. Korsholm and Hans S&oslash;ndergaard, 
 *             VIA University College, DK
 *
 **************************************************************************/
package javax.safetycritical.annotate;

@SCJAllowed
public enum AllocationContext
{
  /** Allocation is allowed in the current memory area. */
  @SCJAllowed
  CURRENT,

  /** Allocation is allowed in any outer (less deeply nested) memory area. */
  @SCJAllowed
  OUTER,

  /** Allocation is allowed in any inner (more deeply nested) memory area. */
  @SCJAllowed
  INNER,

  /**
   * Allocation is allowed in the memory area where the current
   * object (<code>this</code>) was allocated.
   */
  @SCJAllowed
  THIS
}


