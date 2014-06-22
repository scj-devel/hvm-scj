/**************************************************************************
 * File name  : Mission.java
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

import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;

import vm.RealtimeClock;

/**
 * An SCJ application is comprised of one or more <code>Mission</code> objects.
 * Each <code>Mission</code> object is implemented as a subclass of this
 * abstract <code>Mission</code> class.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 * 
 * 
 * @scjComment - SCJ issue: <br>
 *             <ul>
 *             <code>
 *    <li>public static Mission getCurrentMission();
 *   </code>
 *             </ul>
 * <br>
 *             This static function has a problematic semantics.Consider a Level
 *             2 application with nested missions:
 *             <ul>
 *             <li>What is <code>getCurrentMission</code>'s value inside a
 *             mission constructor, and what is it during mission
 *             <li> <code>initialize</code>: it can be replaced by specific
 *             parameters for handlers that need it.
 *             </ul>
 * <br>
 *             The <code>getCurrentMission</code> function can be replaced by
 *             specific parameters for handlers that need it.
 * 
 */
@SCJAllowed
public abstract class Mission 
{
  static MissionSequencer<?> currMissSeq; 
  
  boolean missionTerminate = false;
  
  static HandlerSet currHandlerSet;
  
  @SCJAllowed
  public Mission ()
  {
  }
  
  /** 
   * Method to clean up after a mission terminates. <br> The infrastructure calls 
   * <code>cleanup</code> after all <code>ManagedSchedulable</code>s 
   * associated with this <code>Mission</code> have terminated, but before
   * control leaves the dedicated <code>MissionMemory</code> area. 
   * The default implementation of <code>cleanup</code> does nothing.
   */
  @SCJAllowed(Level.SUPPORT)
  protected void cleanUp ()
  {
  }

  @SCJAllowed
  public static Mission getCurrentMission()
  {
    return currMissSeq.currMission;
  }
  
  @SCJAllowed
  public MissionSequencer<?> getSequencer ()
  {
    return currMissSeq;
  }
  
  /**
   * Performs initialization of this <code>Mission</code>. The infrastructure 
   * calls <code>initialize</code> after the <code>Mission</code> has been 
   * instantiated and the <code>MissionMemory</code> has been resized to 
   * match the size returned from this <code>Mission</code>'s <code>
   * missionMemorySize</code> method. Upon entry into the <code>initialize</code>
   * method, the current allocation context is the <code>MissionMemory</code> 
   * area dedicated to this particular <code>Mission</code>. <p>
   * 
   * An overridden implementation of <code>initialize</code> should instantiate 
   * and register all <code>ManagedSchedulable</code>s that constitute this 
   * <code>Mission</code>. The infrastructure enforces that <code>
   * ManagedSchedulable</code>s can only be instantiated and registered if 
   * the infrastructure is currently executing a <code>Mission.initialize</code> method. 
   * An exception to this rule allows a <code>MissionSequencer</code> to be 
   * instantiated if the infrastructure is currently executing
   * <code>Safelet.getSequencer</code>. <p>
   *  The infrastructure arranges to begin executing the <code>ManagedSchedulable</code>s 
   *  registered by the <code>initialize</code> method upon return from 
   *  the <code>initialize</code> method.
   */
  @SCJAllowed(Level.SUPPORT)
  protected abstract void initialize ();
  
  // used in MissionSequencer.handleAsyncEvent: case State.INITIALIZE
  void setMissionSeq (MissionSequencer<?> missSeq)
  {
    currMissSeq = missSeq;
  }

  /**
   * The application developer is required to implement this method.
   * @return The desired size of this mission's <code>MissionMemory</code>, measured in bytes.
   */
  @SCJAllowed(Level.SUPPORT)
  public abstract long missionMemorySize ();

//  /**
//   * Asks for termination of the current <code>Mission</code> and its 
//   * <code>MissionSequencer</code>. <br> The effect of this method is to invoke 
//   * <code>requestSequenceTermination</code> on the <code>MissionSequencer</code> 
//   * that is responsible for execution of this <code>Mission</code>.
//   */
//  public final void requestSequenceTermination ()
//  {
//    currMissSeq.requestSequenceTermination();
//  }

  /**
   * This method provides a standard interface for requesting termination of a mission. <p>
   * The infrastructure shall: 
   * <ol>
   * <li> disable all periodic event handlers associated with this <code>Mission</code>,
   * <li> remove handlers from <code>AperiodicEvent</code>s, 
   * <li> clear the fire count for each of this <code>Mission</code>'s event handlers 
   * <li> wait for all dispatched schedulables to terminate execution, 
   * <li> invoke the <code>cleanup</code> method for each of the 
   *      <code>ManagedSchedulable</code>s associated with this mission, and 
   * <li> invoke the <code>cleanup</code> method associated with this mission.
   * </ol>
   * <p>
   * 
   * An application-specific subclass of <code>Mission</code> may override 
   * this method in order to insert application-specific code to communicate 
   * the intent to shutdown to specific <code>ManagedSchedulable</code>s. <p>
   * Control returns from <code>requestTermination</code> after it has 
   * arranged for the required activities to be performed. 
   * Note that those activities may not have completed.
   */
  @SCJAllowed
  public final void requestTermination ()
  {
    missionTerminate = true;
  }

//  /**
//   * Checks if the current <code>MissionSequencer</code> is trying to terminate.
//   * 
//   * @return True if and only if the <code>requestSequenceTermination</code> 
//   *   method for the <code>MissionSequencer</code> that controls execution
//   *   of this <code>Mission</code> has been invoked.
//   */
//  public final boolean sequenceTerminationPending ()
//  {
//    return currMissSeq.sequenceTerminationPending();
//  }

  /**
   * Checks if the current mission is trying to terminate.
   * 
   * @return True if and only if this <code>Mission</code> object's <code>
   *         Mission.requestTermination</code> method has been invoked.
   */
  public final boolean terminationPending ()
  {
    return missionTerminate;
  }

  void runInitialize()
  {
	currHandlerSet = new HandlerSet();
    initialize();
  }
 
  void runExecute()  
  //  Called in mission memory. 
  //  This implementation is for priority schedule execution.
  //  For cyclic schedule execution, this method is overwritten in 
  //  the subclass CyclicExecutive. 
  {
    //devices.Console.println ("Mission.runExecute");
    
    HandlerSet hs = currHandlerSet;     
    PriorityFrame frame = PriorityScheduler.instance().pFrame;    
    
    vm.ClockInterruptHandler.instance.disable();    
   
    for (int i = 0; i < hs.noOfHandlers; i++)
    {       
      hs.scjProcesses[i] = 
        new ScjProcess (hs.eventHandlers[i],
        		        new int[(int)hs.eventHandlers[i].storage.configurationSizes[0]]);
      
      hs.scjProcesses[i].index = i;
      
      frame.addProcess	(hs.scjProcesses[i]);      
    }
   
    vm.ClockInterruptHandler.instance.enable();
        
    // wait until (missionTerminate == true)   
    while (!missionTerminate)
    {  	
    	RealtimeClock.awaitNextTick();
    }
  }
  
  void runCleanup(MissionMemory missMem)  
  //  Called in mission memory. 
  //  This implementation is for priority schedule execution.
  //  For cyclic schedule execution, this method is overwritten in 
  //  the subclass CyclicExecutive. 
  {
	// wait until (all handlers in mission have terminated) 
	  
    while (currHandlerSet.handlerCount > 0)    	
    {
    	;
    }     
    missMem.resetArea();
  }
   
  /*@ 
    behaviour
      requires pevh != null;
      assignable \nothing;
      ensures \result == true ;  // not finished
    @*/ 
  /*@ spec_public @*/ boolean isRegistered(PeriodicEventHandler pevh)   // problems with tool, if private
  {
    return true;
  }
  
  /*@ 
    behaviour
      requires pevh != null;
      assignable \nothing;
      ensures \result == true ; // not finished
    @*/ 
  /*@ spec_public @*/ boolean inMissionScope(PeriodicEventHandler pevh)   // problems with tool, if private
  {
    return true;
  }
  
  /*@ 
    behaviour
      requires cs != null;
      assignable \nothing;
      ensures \result == true ; // not finished
    @*/ 
  /*@ spec_public @*/ boolean inMissionScope(CyclicSchedule cs)   // problems with tool, if private
  {
    return true;
  }

}
