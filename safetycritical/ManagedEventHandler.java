/**************************************************************************
 * File name  : ManagedEventHandler.java
 * 
 * This file is part of our SCJ Level 0 and Level 1 implementation, 
 * based on SCJ Draft, Version 0.79. 16 May 2011.
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
 * Description: 
 * 
 * Revision history:
 *   date   init  comment
 *
 *************************************************************************/
package javax.safetycritical;

import javax.safetycritical.annotate.SCJAllowed;
import javax.scj.util.Const;

/**
 * <code>ManagedEventHandler</code> is the base class for all SCJ handlers.
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment - SCJ issue: In constructor, null arguments for priority and
 *             release parameters are left for resolution by infrastructure
 *             initialization <br>
 *             - SCJ issue: In constructor, if storage parameter is null, a
 *             default value is given.
 *             <p>
 *             - SCJ implementation: <code>getCurrentHandler</code> omitted ? <br>
 *             <ul>
 *             <li>
 *             <code>  
 *        public static ManagedEventHandler getCurrentHandler()
 *      </code>
 *             </ul>
 */
@SCJAllowed
public abstract class ManagedEventHandler extends AsyncEventHandler implements ManagedSchedulable {
	PriorityParameters priority;
	ReleaseParameters release;

	/**
	 * The memory area in which the handler will execute
	 */
	ManagedMemory privateMemory;

	/**
	 * The scheduler that implements release of a handler.
	 */
	PriorityScheduler sch; // used in AperiodicEventHandler.fire

	/**
	 * Process for use by scheduler
	 */
	ScjProcess process;

	int default_stack_size;

	protected ManagedEventHandler(PriorityParameters priority, ReleaseParameters release, StorageParameters storage) {
		this.priority = priority;
		this.release = release;
		this.privateMemory = new ManagedMemory((storage != null ? (int) storage.getBackingStoreSize() : Const.BACKING_STORE_SIZE));
		// devices.Console.println("ManagedEventHandler.constructor: private mem created");
		default_stack_size = Const.DEFAULT_STACK_SIZE;
		if (storage != null) {
			if (storage.configurationSizes != null) {
				if (storage.configurationSizes.length > 0) {
					default_stack_size = (int) storage.configurationSizes[0];
				}
			}
		}
		// if scheduler is not a PriorityScheduler (but CyclicScheduler), then
		// sch should be null
		if (Launcher.level == 1)
			this.sch = PriorityScheduler.instance();
	}

	ManagedMemory getMemoryArea() {
		return (ManagedMemory) privateMemory;
	}

	public abstract void handleAsyncEvent();

	public void cleanup() {
		// ToDo: nothing?
		// devices.Console.println ("ManagedEventHandler.cleanup");
	}

	/**
	 * Registers this event handler with the current mission.
	 */
	public void register() {
		HandlerSet hs = HandlerSet.getHandlerSet();
		hs.addHandler(this);
	}

	/**
	 * @scjComment Omitted, - not implemented
	 */
	@SCJAllowed
	public static ManagedEventHandler getCurrentHandler() {
		return null;
	}
}
