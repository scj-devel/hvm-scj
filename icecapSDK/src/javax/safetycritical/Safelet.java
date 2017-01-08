/**************************************************************************
 * File name  : Safelet.java
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
 *             
 * @version 1.3 2016-10-18  
 *************************************************************************/
package javax.safetycritical;

import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.Phase;
import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.annotate.SCJPhase;
import javax.safetycritical.annotate.SCJMayAllocate;
import javax.safetycritical.annotate.AllocationContext;
import javax.safetycritical.annotate.SCJMaySelfSuspend;

/** 
 * A safety-critical application consists of one or more missions, executed 
 * concurrently or in sequence. Every safety-critical application must implement 
 * <code>Safelet</code> which identifies  the outer-most <code>MissionSequencer</code>.
 * This outer-most <code>MissionSequencer</code> runs the sequence of missions 
 * that comprise this safety-critical application.
 * 
 * The mechanism used to identify the <code>Safelet</code> to a particular SCJ environment 
 * is implementation defined.
 */
@SuppressWarnings("unused")
@SCJAllowed
public interface Safelet {
	/**
	 * The infrastructure invokes <code>getSequencer</code> to obtain the 
	 * <code>MissionSequencer</code> object that oversees execution of missions 
	 * for this application. The returned sequencer must reside in immortal memory.
	 * 
	 * @return The <code>MissionSequencer</code> responsible for selecting
	 *   the sequence of <code>Mission</code>s that represent this application.
	 */
	@SCJAllowed(Level.SUPPORT)
	@SCJPhase({Phase.STARTUP})
	@SCJMayAllocate({AllocationContext.CURRENT})
	@SCJMaySelfSuspend(false)
	public MissionSequencer getSequencer();
	
	
	//public long globalBackingStoreSize();  // new: since SCJ 152
	
	/**
	 *  @return the amount of additional immortal memory that must be available for allocations to be 
	 *  performed by this application. If the amount of memory remaining in immortal memory is less 
	 *  than this requested size, the infrastructure halts execution of the application upon return from 
	 *  this method.
	 */
	@SCJAllowed(Level.SUPPORT)
	@SCJPhase({Phase.STARTUP})
	@SCJMayAllocate({})
	@SCJMaySelfSuspend(false)
	public long immortalMemorySize();

	/**
	 *  The infrastructure shall invoke <code>initializeApplication</code> in the allocation context of
	 *  immortal memory. The application can use this method to allocate data structures in immortal memory. 
	 *  This method shall be called exactly once by the infrastructure.
	 */
	@SCJAllowed(Level.SUPPORT)
	@SCJPhase({Phase.STARTUP})
	@SCJMayAllocate({AllocationContext.CURRENT})
	@SCJMaySelfSuspend(true)
	public void initializeApplication();

}
