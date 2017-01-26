/**************************************************************************
 * File name  : SCJAllowed.java
 * 
 * This file is part a SCJ Level 0 and Level 1 implementation, 
 * based on SCJ Draft, Version 0.108 6. January 2017.
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
 * Copyright 2017 
 * @authors  Anders P. Ravn, Aalborg University, DK
 *           Stephan E. Korsholm and Hans S&oslash;ndergaard, 
 *             VIA University College, DK
 *             
 *************************************************************************/

package javax.safetycritical.annotate;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** This annotation distinguishes methods, classes, and fields that may be accessed
* from within safety-critical Java programs. In some implementations
* of the safety-critical Java specification, elements which are not declared with
* the <code>@SCJAllowed</code> annotation (and are therefore not allowed in safety-critical
* application software) are present within the declared class hierarchy. These
* are necessary for full compatibility with standard edition Java, the Real-Time
* Specification for Java, and/or for use by the implementation of infrastructure
* software.
* 
* The value field equals <code>LEVEL_0</code> for elements that may be used within safety-critical
* Java applications targeting Level 0, Level 1, or Level 2.
* 
* The value field equals <code>LEVEL_1</code> for elements that may be used within safety-critical
* Java applications targeting Level 1 or Level 2.
* 
* The value field equals <code>LEVEL_2</code> for elements that may be used within safety-critical
* Java applications targeting Level 2.
* 
* Absence of this annotation on a given Class, Field, Method, or Constructor
* declaration indicates that the corresponding element may not be accessed from
* within a compliant safety-critical Java application.
*/
@SCJAllowed
@Retention(java.lang.annotation.RetentionPolicy.CLASS)
@Target({
java.lang.annotation.ElementType.TYPE,
java.lang.annotation.ElementType.FIELD,
java.lang.annotation.ElementType.METHOD,
java.lang.annotation.ElementType.CONSTRUCTOR})

public @interface SCJAllowed {
	
	@SCJAllowed
	public boolean members() default false;

	@SCJAllowed
	public Level value() default Level.LEVEL_0;

}


