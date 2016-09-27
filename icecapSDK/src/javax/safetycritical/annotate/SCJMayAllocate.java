/**************************************************************************
 * File name  : SCJMayAllocate.java
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

package javax.safetycritical.annotate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation distinguishes methods that may be restricted from allocating 
 * memory in certain memory areas.
 */
@SCJAllowed
@Retention(RetentionPolicy.CLASS)
@Target({
java.lang.annotation.ElementType.TYPE,
java.lang.annotation.ElementType.METHOD,
java.lang.annotation.ElementType.CONSTRUCTOR})

public @interface SCJMayAllocate {

@SCJAllowed
public AllocatePermission[] value ()
	default {
		AllocatePermission.CurrentContext, 
		AllocatePermission.OuterContext, 
		AllocatePermission.InnerContext};
}

