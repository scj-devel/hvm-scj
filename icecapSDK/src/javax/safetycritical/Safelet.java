
/**************************************************************************
 * File name  : Safelet.java
 * 
 * This file is part an SCJ implementation, based on SCJ Specification, Version 0.111, 7 April 2017, Draft
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
 * along with this SCJ implementation.  
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2017 
 * @authors  Anders P. Ravn, Aalborg University, DK
 *           Stephan E. Korsholm and Hans S&oslash;ndergaard, 
 *             VIA University College, DK
 *             
 * @version 1.5 2017-10-16 
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
 * <br>
 * The mechanism used to identify the <code>Safelet</code> to a particular SCJ environment 
 * is implementation defined.
 */
@SCJAllowed
public interface Safelet {

	@SCJAllowed
	public static final int INSUFFICIENT_BACKING_STORE = 17;
	@SCJAllowed
	public static final int INSUFFICIENT_IMMORTAL_MEMORY = 19;
	
	/**
	 * The infrastructure invokes <code>getSequencer</code> to obtain the 
	 * <code>MissionSequencer</code> object that oversees execution of missions 
	 * for this application. The returned sequencer resides in immortal memory.
	 * 
	 * @return the <code>MissionSequencer</code> that oversees execution of missions for 
	 * this application.
	 */
	@SCJAllowed(Level.SUPPORT)
	@SCJPhase({Phase.STARTUP})
	@SCJMayAllocate({AllocationContext.CURRENT})
	@SCJMaySelfSuspend(false)
	public MissionSequencer getSequencer();

	/**
	 * @return the amount of additional backing store memory that must be available
	 * 	for managed memory areas. If the amount of remaining memory is less than this
	 * 	requested size, the infrastructure shall call the <code>handleStartupError()</code> method to determine
	 * 	whether the application should be immediately halted.
	*/
	@SCJPhase({Phase.STARTUP})
	@SCJMayAllocate({})
	@SCJMaySelfSuspend(false)
	public long managedMemoryBackingStoreSize( );

	/**
	 *  @return the amount of additional immortal memory that must be available for allocations to be 
	 *  performed by this application. If the amount of remaining memory is less 
	 *  than this requested size, the infrastructure  shall call the <code>handleStartupError()</code> method 
	 *  to determine whether the application should be immediately halted.
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
	 *  
	 *  @param args — The list of parameters passed to the safety-critical Java program on its invocation.
	 */
	@SCJAllowed(Level.SUPPORT)
	@SCJPhase({Phase.STARTUP})
	@SCJMayAllocate({AllocationContext.CURRENT})
	@SCJMaySelfSuspend(true)
	public void initializeApplication(String[] args);

	/**
	 * Called during startup by the infrastructure if it detects the presence
	 * of a fatal startup error allocating memory or for any other implementation
	 * defined reason. This method returns a boolean indication whether it intends for the infrastructure 
	 * to immediately halt execution, or whether it intends for the infrastructure to retry
	 * the failed allocation request. This method makes it possible for an application to attempt to execute 
	 * in a degraded mode in the event of certain types of failure, such as a partial memory failure.
	 * procedure, providing the application the ability to reconfigure itself, 
	 * 
	 * @param cause - Identifies the condition that caused the infrastructure to call this method.
	 * <br>
	 * 	If <code>cause</code> = <code>INSUFFICIENT_IMMORTAL_MEMORY</code>, the amount of available memory
	 *  is insufficient for the immortal memory requested by the previous call to <code>immortalMemorySize()</code>.
	 *  If <code>cause</code> = <code>INSUFFICIENT_BACKING_STORE</code>, the amount of
	 *  available memory is insufficient for the backing store memory requested by the previous
	 *  call to <code>managedMemoryBackingStoreSize()</code>. If cause has any other value, its meaning is
	 *  implementation defined.
	 * 
	 *  @param val - If <code>cause</code> = <code>INSUFFICIENT IMMORTAL MEMORY</code>, <code>val</code> contains the short-fall 
	 *  in available memory for the immortal memory requested by the previous call to <code>immortalMemorySize()</code>., 
	 *  If <code>cause</code> = <code>INSUFFICIENT_BACKING_STORE</code>, <code>val</code> contains the short-fall in available memory 
	 *  for the backing store memory requested by the previous call to <code>managedMemoryBackingStoreSize()</code>. 
	 *  If <code>cause</code> has any other value, the meaning of <code>val</code> is implementation defined.
	 *   
	 *  @return <code>true</code> if the infrastructure should immediately halt as a result of detecting the fatal startup error.
	 *  If <code>false</code> is returned, the infrastructure should repeat its calls to
	 *  <code>immortalMemorySize()</code> and <code>managedMemoryBackingStoreSize()</code>, providing the application the ability 
	 *  to reconfigure itself, if possible, to work around the fatal startup error.
	 */
	@SCJAllowed(Level.SUPPORT)
	@SCJPhase({Phase.STARTUP})
	@SCJMayAllocate({AllocationContext.CURRENT})
	@SCJMaySelfSuspend(false)
	public boolean handleStartupError(int cause, long val);

	/**
	 * Called by the infrastructure after termination of the <code>MissionSequencer</code> for this
	 * <code>Safelet</code>.
	 */
	@SCJAllowed(Level.SUPPORT)
	@SCJPhase({Phase.CLEANUP})
	@SCJMayAllocate({AllocationContext.CURRENT})
	@SCJMaySelfSuspend(false)
	public void cleanUp();
}




