/**************************************************************************
 * File name  : ManagedSchedulable.java
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
package javax.safetycritical;

import javax.realtime.Affinity;
import javax.realtime.Schedulable;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.Phase;
import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.annotate.SCJPhase;

/**
 * All schedulable objects are managed by a mission.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 */
@SCJAllowed
public interface ManagedSchedulable extends Schedulable {
	
	/**
	 * Runs end-of-mission clean up associated with this schedulable object.
	 */
	@SCJAllowed(Level.SUPPORT)
	public void cleanUp();
	
	/**
	 * Registers this schedulable object with the current mission.
	 */
	@SCJPhase(Phase.INITIALIZATION)
	public void register(Affinity affinity) throws IllegalStateException;
	
	/**
	 * Registers this schedulable object with the current mission.
	 */
	@SCJPhase(Phase.INITIALIZATION)
	public void register();

	/**
	 * Indicates that the enclosing mission has been instructed to terminate.
	 */
	@SCJAllowed(Level.SUPPORT)
	public void signalTermination();
}

