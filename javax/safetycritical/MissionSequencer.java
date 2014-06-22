/**************************************************************************
 * File name  : MissionSequencer.java
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
import javax.realtime.PriorityParameters;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.Phase;
import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.annotate.SCJRestricted;
import javax.scj.util.Const;

/**
 * A <code>MissionSequencer</code> oversees a sequence of Mission executions. 
 * The sequence may include interleaved execution of independent missions 
 * and repeated executions of missions.<p>
 * As a subclass of <code>ManagedEventHandler</code>, <code>MissionSequencer</code> 
 * has an execution priority and memory budget as specified by constructor parameters. <p>
 * The <code>MissionSequencer</code> executes vendor-supplied infrastructure code which
 * invokes user-defined implementations of the method <code>getNextMission</code>, and for each mission
 * the user-defined <code>initialize</code> and <code>cleanUp</code> methods. <p>
 * During execution of an inner-nested mission, the <code>MissionSequencer</code> remains blocked waiting for
 * the mission to terminate. An invocation of <code>requestSequenceTermination</code> will unblock it
 * so that it can perform an invocation of the running mission's <code>requestTermination</code> method,
 * if the mission is still running and its termination has not already been requested. <p>
 * Note that if a <code>MissionSequencer</code> object is preallocated by the application, it
 * must be allocated in the same scope as its corresponding <code>Mission</code>s.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 * 
 * 
 * @scjComment 
 *   - SCJ issue: Why Level 2 only for the following two methods?
 *     <ul>
 *       <code>
 *       <li>public final void requestSequenceTermination() <br>
 *       <li>public final boolean sequenceTerminationPending() 
 *       </code>
 *      </ul>
 */
@SCJAllowed
public abstract class MissionSequencer<SpecificMission extends Mission> extends ManagedEventHandler 
{
	MissionMemory missionMemory;
	SpecificMission currMission;

	interface State {
		public final static int START      = 1;
		public final static int INITIALIZE = 2;
		public final static int EXECUTE    = 3;
		public final static int CLEANUP    = 4;
		public final static int TERMINATE  = 5;
		public final static int END        = 6;
	}

	int currState;
	boolean terminateSeq = false;

	static ScjProcess missSeqProcess = null;

	/**
	 * Constructs a <code>MissionSequencer</code> to run at the priority and
	 * with the memory resources specified by its parameters.
	 */
	@SCJAllowed
	@SCJRestricted(Phase.INITIALIZE)
	public MissionSequencer(PriorityParameters priority, StorageParameters storage) {
		super(priority, 
		  new AperiodicParameters(), 
		  storage);
		missionMemory = new MissionMemory((int) storage.maxMissionMemory);

		currState = State.START;
		
		if (Launcher.level == 1) {
			PriorityScheduler.instance().
			  add(this, new int[Const.HANDLER_STACK_SIZE]);
		}
	}

	/**
	 * This method is declared final because the implementation is provided by
	 * the infrastructure of the SCJ implementation and shall not be overridden. <br>
	 * This method performs all of the activities that correspond to sequencing
	 * of <code>Mission</code>s by this <code>MissionSequencer</code>.
	 */
	@SCJAllowed(Level.INFRASTRUCTURE)
	public final void handleAsyncEvent()
	{
		do {
			// the main actions of the sequencer governed by currState
			switch (currState) {
			case State.START:
				currMission = getNextMission();

				if (currMission == null || terminateSeq) {
					terminateSeq = true;
					currState = State.TERMINATE;
				} else {
					currMission.missionTerminate = false;
					currState = State.INITIALIZE;
				}
				break;

			case State.INITIALIZE:
				//devices.Console.println("MS.I");
			    
			    currMission.setMissionSeq(this);
				missionMemory.enterToInitialize(currMission);

				currState = State.EXECUTE;
				break;

			case State.EXECUTE:
				//devices.Console.println("MS.E");
								
				missionMemory.enterToExecute(currMission);

				currState = State.CLEANUP;
				break;

			case State.CLEANUP:
				//devices.Console.println("MS.C");
				missionMemory.enterToCleanup(currMission);
				
				// handleAsyncEvent continues
				currState = State.START;
				break;

			case State.TERMINATE:
				// devices.Console.println("MS.T");
				currState = State.END;
			default:
			}
		} while (currState < State.END);
	}

	/**
	 * This method is called by the infrastructure to select the next
	 * <code>Mission</code> to execute. <br>
	 * Prior to each invocation of <code>getNextMission</code> by the
	 * infrastructure, the infrastructure instantiates and enters a <code>
	 * MissionMemory</code>, initially sized to represent all available backing
	 * store.
	 * 
	 * @return The next <code>Mission</code> to run, or null if no further
	 *         <code>Mission</code>s are to run under the control of this
	 *         <code>MissionSequencer</code>.
	 */
	@SCJAllowed(Level.SUPPORT)
	protected abstract SpecificMission getNextMission();

	public final void register() {
		super.register();
	}

	/**
	 * Invokes the currently running <code>Mission</code>'s <code>
	 * requestTermination</code> method. <br>
	 * Upon completion of the currently running <code>Mission</code>, this
	 * <code>MissionSequencer</code> returns without starting any additional
	 * missions.
	 * <p>
	 * Note that <code>requestSequenceTermination</code> does not force the
	 * sequence to terminate because the currently running <code>Mission</code>
	 * must voluntarily relinquish its resources.
	 */
	@SCJAllowed
	public final void requestSequenceTermination() {
		terminateSeq = true;
		currMission.requestTermination();
	}

	/**
	 * Checks if the current <code>Mission</code> is trying to terminate.
	 * 
	 * @return True if and only if this <code>MissionSequencer</code>'s
	 *         <code>requestSequenceTermination</code> method has been invoked.
	 */
	@SCJAllowed
	public final boolean sequenceTerminationPending() {
		return terminateSeq;
	}

	@Override
	public final void cleanUp() {
		super.cleanUp();
		missionMemory.removeArea();
	}
}
