/**************************************************************************
 * File name: AperiodicEventHandler.java
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

import javax.realtime.AperiodicParameters;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.Phase;
import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.annotate.SCJPhase;

/**
 * This class permits the explicit release of application code.
 * Concrete subclasses must implement the <code>handleAsyncEvent</code> method 
 * and may override the default <code>cleanUp</code> method. <br>
 * 
 * Note that the values in parameters passed to the constructors are those that will be used by the 
 * infrastructure. Changing these values after construction will have no impact on the created event handler.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 */
@SCJAllowed(Level.LEVEL_1)
public abstract class AperiodicEventHandler extends ManagedEventHandler {
	boolean isReleased = false;

	/**
	 * Constructs an aperiodic event handler that can be explicitly released.
	 * 
	 * @param priority is the priority parameters for this aperiodic event handler; it must not be null.
	 * @param release is the release parameters for this aperiodic event handler; it must not be null.
	 * @param storage is the <code>StorageParameters</code> for this aperiodic event handler.
	 * 
	 * @throws <code>IllegalArgumentException</code> if <code>priority</code> or <code> release</code> is null.
	 */
	public AperiodicEventHandler(PriorityParameters priority, AperiodicParameters release,
			ScopeParameters storage, ConfigurationParameters config) {
		this(priority, release, storage, config, null);
	}
	
	@SCJAllowed(Level.LEVEL_1)
	@SCJPhase(Phase.INITIALIZATION)
	protected AperiodicEventHandler(PriorityParameters priority, AperiodicParameters release,
			ScopeParameters storage, ConfigurationParameters config, String name) {
		super(priority, release, storage, config, name);
		if (priority == null || release == null)
			throw new IllegalArgumentException("null argument");
		
		if(Launcher.useOS)
			Services.setCeiling(this, this.priority.getPriority());
	}
	
	/**
	 * Release this aperiodic event handler
	 */
	@SCJAllowed
	public final void release() {
		ManagedEventHandler.handlerBehavior.aperiodicHandlerRelease(this);
	}
	
	boolean isReleased() {
		return isReleased;
	}
	
	synchronized void waitForNextRelease() {
		//		if (mission.terminationPending()) {
		//			mission.currMissSeq.decrementActiveCount();
		//			OSProcess.requestTermination_c(osProcess.executable);
		//			OSProcess.testCancel_c();
		//		}

		try {
			if (!mission.terminationPending())
				wait();
		} catch (InterruptedException e) {
		}

		if (mission.terminationPending()) {
			mission.currMissSeq.seqNotify();
			OSProcess.requestTermination_c(process.executable);
			//			OSProcess.testCancel_c();
		}
	}

	synchronized void fireNextRelease() {
		notify();
	}

}
