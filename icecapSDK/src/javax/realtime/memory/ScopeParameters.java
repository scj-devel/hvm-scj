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

import javax.safetycritical.annotate.SCJAllowed;

/**
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 * 
 * @scjComment
 *  - The suggested arguments <code>messageLength</code> and <code>stackTraceLength</code>
 *    are vendor specific, thus might be included in <code>sizes</code>. 
 */
@SCJAllowed
public final class ScopeParameters extends javax.realtime.MemoryParameters {

	private static final long serialVersionUID = 123456789987654101L;
	
//	long totalBackingStore;
//	long maxMissionMemory;
	
	long maxContainingArea; // HSO
	long maxInitialBackingStore; // HSO

	/**
	 * Create a ScopeParameters instance with the given values.
	 * 
	 * @param maxInitialArea a limit on the amount of memory the schedulable may allocate
	 * 		in its initial scoped memory area <p>
	 */
	@SCJAllowed
//	public ScopeParameters(long maxInitialArea, long maxImmortal, long maxMemoryArea, long maxMissionMemory) {  // HSO: old
//	
//		super(maxMemoryArea, maxImmortal);
//
//		this.totalBackingStore = maxInitialArea;
//		this.maxMissionMemory = maxMissionMemory;
//	}
	
	public ScopeParameters(long maxInitialArea, long maxImmortal, long maxContainingArea, long maxInitialBackingStore) // HSO
			throws java.lang.IllegalArgumentException { 

		super(maxInitialArea, maxImmortal);
		this.maxContainingArea = maxContainingArea;
		this.maxInitialBackingStore = maxInitialBackingStore;
	}
	
	public ScopeParameters(long maxInitialArea, long maxImmortal, long maxInitialBackingStore) // HSO
			throws java.lang.IllegalArgumentException { 

		this(maxInitialArea, maxImmortal, 0, maxInitialBackingStore);
	}

	public  long getMaxInitialArea() {
		return maxInitialArea;
	}
	
	public long getMaxContainingArea() {
		return maxContainingArea;
	}

	public long getMaxBackingStore() {
		return maxInitialBackingStore;
	}


//	//used in JML annotation only (not public)
//	long getBackingStoreSize() {
//		return totalBackingStore;
//	}
//	
//	//used in JML annotation only (not public)
//	long getMaxMissionMemory() {
//		return maxMissionMemory;
//	}

}



