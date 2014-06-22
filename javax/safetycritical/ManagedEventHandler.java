/**************************************************************************
 * File name  : ManagedEventHandler.java
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

import javax.realtime.AbsoluteTime;
import javax.realtime.PriorityParameters;
import javax.realtime.ReleaseParameters;
import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.annotate.SCJRestricted;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.Phase;
import javax.scj.util.Const;

/**
 * <code>ManagedEventHandler</code> is the base class for all SCJ handlers.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 * 
 * @scjComment - SCJ issue: In constructor, null arguments for priority and
 *             release parameters are left for resolution by infrastructure
 *             initialization <br>
 *             - SCJ issue: In constructor, if storage parameter is null, 
 *             a default value is given. <br>
            
 */
@SCJAllowed
public abstract class ManagedEventHandler implements ManagedSchedulable 
{
	// APR added; HSO: not used
	
//	static class CurrentRunning implements Runnable {
//	   /**
//	    * the current running ManagedSchedulable object
//	    * 
//	    * It is set indirectly by <code>CyclicExecutive<code> and the <PriorityScheduler</code>,
//	    * and it is initialized by the <code>Launcher</code>.
//	    * 
//	    * For a multiprocessor application, there would be an array of ManagedSchedulables indexed
//	    * by a processor number.
//	    */
//	    public static ManagedEventHandler handler;
//	      
//	    public void run(){
//	        handler.handleAsyncEvent();
//	    }      
//	};  
//	
//	private final static CurrentRunning runSO = new CurrentRunning();
//	
//	final static ManagedEventHandler getCurrentSO() {
//		return CurrentRunning.handler;
//	}
//	
//	final static void setCurrentSO(ManagedEventHandler handler) {
//		CurrentRunning.handler= handler;
//	}
//	
//	final static void setAndRunSO(ManagedEventHandler handler){
//		CurrentRunning.handler= handler;
//		handler.privateMemory.enter(runSO);
//	}

	// APR end addition
	
	PriorityParameters priority;
	ReleaseParameters release;

	StorageParameters storage;

	/**
	 * The memory area in which the handler will execute
	 */
	ManagedMemory privateMemory;

	/**
	 * Process for use by scheduler
	 */
	ScjProcess process;

	static StorageParameters defaultStorage = 
			  new StorageParameters(Const.BACKING_STORE_SIZE_DEFAULT,
				new long[] { Const.HANDLER_STACK_SIZE_DEFAULT },
				Const.PRIVATE_MEM_SIZE_DEFAULT, 
				Const.IMMORTAL_MEM_SIZE_DEFAULT, 
				Const.MISSION_MEM_SIZE_DEFAULT);

  /**
   * Constructs an event handler.
   * 
   * @param priority specifies the priority parameters.
   * @param release  specifies the release parameters.
   * @param storage  specifies the non-null maximum storage demands for this event handler.
   * 
   * @throws <code>IllegalArgumentException</code> if priority or release parameters are null.
   */
  /*@ 
    public normal_behavior
      requires priority != null;
      requires 
        javax.safetycritical.PriorityScheduler.instance().getMinPriority() <= priority.getPriority() && 
        priority.getPriority() <= javax.safetycritical.PriorityScheduler.instance().getMaxPriority();

      requires release != null;  
  
      ensures this.getPriorityParam().getPriority() == priority.getPriority();  
      ensures this.getReleaseParam().getDeadline() == release.getDeadline();
      ensures this.getReleaseParam().getMissHandler() == release.getMissHandler();
  
    also
    public exceptional_behavior
      requires priority == null;
      signals (IllegalArgumentException) true;
    also
    public exceptional_behavior
      requires release == null;
      signals (IllegalArgumentException) true;        
    @*/
	public ManagedEventHandler(PriorityParameters priority, 
			                   ReleaseParameters release, 
			                   StorageParameters storage) 
	{
		if (priority == null) 
		  throw new IllegalArgumentException ("priority is null");	
		if (release == null) 
		  throw new IllegalArgumentException ("release is null");
		
		this.priority = priority;
		this.release = release;
		this.storage = (storage != null ? storage : defaultStorage);
	
		this.privateMemory = new PrivateMemory(
		  (storage != null ? (int) storage.maxMemoryArea : Const.PRIVATE_MEM_SIZE_DEFAULT));
	}
	
	public abstract void handleAsyncEvent();

	@SCJAllowed(Level.SUPPORT)
	@SCJRestricted(Phase.CLEANUP)
	public void cleanUp() {
		privateMemory.removeArea();
	}

	/**
	 * Registers this event handler with the current mission.
	 */
	@SCJAllowed(Level.INFRASTRUCTURE)
	@SCJRestricted(Phase.INITIALIZE)
	public void register() {
		HandlerSet hs = Mission.currHandlerSet;
		hs.addHandler(this);
	}
	
	@SCJAllowed(Level.LEVEL_1)
	public AbsoluteTime getLastReleaseTime()
	{
		// ToDo: implementation
		return null;
	}
	
	private /*@ spec_public @*/ PriorityParameters getPriorityParam()
	{
		return priority;
	}
	
	private /*@ spec_public @*/ ReleaseParameters getReleaseParam()
	{
		return release;
	}
}
