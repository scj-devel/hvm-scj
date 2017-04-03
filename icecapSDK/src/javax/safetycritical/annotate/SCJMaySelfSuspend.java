/**************************************************************************
 * File name  : SCJMaySelfSuspend.java
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
 *************************************************************************/

package javax.safetycritical.annotate;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation distinguishes methods that may be restricted from blocking
 * during execution.
 */
@SCJAllowed
@Retention(java.lang.annotation.RetentionPolicy.CLASS)
@Target({
java.lang.annotation.ElementType.TYPE,
java.lang.annotation.ElementType.METHOD,
java.lang.annotation.ElementType.CONSTRUCTOR})

public @interface SCJMaySelfSuspend {
	@SCJAllowed
	public boolean value () default false;
}


