/**************************************************************************
 * File name  : Chronograph.java
 * 
 * This file is part a SCJ implementation, 
 * based on SCJ 145 Draft, Version 0.105 14 December 2015.
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
 * Copyright 2016 
 * @authors  Anders P. Ravn, Aalborg University, DK
 *           Stephan E. Korsholm and Hans S&oslash;ndergaard, 
 *             VIA University College, DK
 *************************************************************************/

package javax.realtime;

import javax.safetycritical.annotate.SCJAllowed;

@SCJAllowed
public interface Chronograph {
	
	public RelativeTime getEpochOffset();
	public RelativeTime getQueryPrecision(RelativeTime dest);
	public RelativeTime getQueryPrecision();
	public AbsoluteTime getTime(AbsoluteTime dest);
	public AbsoluteTime getTime();
}



