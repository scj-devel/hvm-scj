/**************************************************************************
 * File name  : ScopeParameters.java
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
package javax.realtime.memory;

import javax.realtime.MemoryParameters;

public class ScopeParameters extends MemoryParameters {
	
	public ScopeParameters(long maxInitialArea, long maxImmortal,
						   long maxInitialBackingStore, long maxContainingArea)
		throws java.lang.IllegalArgumentException {
		
		super(maxInitialArea, maxImmortal); // ??
		
		//ToDo: implement
		
	}
	
	public ScopeParameters(long maxInitialArea, long maxImmortal,
						   long maxInitialBackingStore)
		throws java.lang.IllegalArgumentException  {
		
		//Same as ScopeParameters(maxInitialArea, maxImmortal, MemoryParameters.UNREFERENCED,
		//	maxParentBackingStore, MemoryParameters.UNLIMITED)
		
		this(maxInitialArea, maxImmortal, 0, 0); // ??
		
		//ToDo: implement
		
	}
	
	public long getMaxBackingStore() {
		
		//ToDo: implement
		return -1;
	}
	
	public long getMaxContainingArea() {
		
		//ToDo: implement
		return -1;
	}

}



