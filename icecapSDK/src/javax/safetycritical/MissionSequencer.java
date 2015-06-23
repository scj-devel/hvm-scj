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
 * @scjComment 
 */
@SCJAllowed
public abstract class MissionSequencer<MissionType extends Mission> extends ManagedEventHandler {

	MissionMemory missionMemory;
	MissionType currMission;

	interface State {
		public final static int START = 1;
		public final static int INITIALIZE = 2;
		public final static int EXECUTE = 3;
		public final static int CLEANUP = 4;
		public final static int TERMINATE = 5;
		public final static int END = 6;
	}

	int currState;
	boolean terminateSeq = false;
	static volatile boolean isOuterMostSeq = true;

	static ScjProcess missSeqProcess = null;

	static int howManyMissions = -1; // for testing only

	// Level2 only: a reference to the nearest outer sequencer
	MissionSequencer<?> outerSeq = null;
	static MissionSequencer<?> outerMostSeq = null; // for multiprocessor only
	
	Monitor lock;

	/**
	 * Constructs a <code>MissionSequencer</code> to run at the priority and
	 * with the memory resources specified by its parameters.
	 */
	@SCJAllowed
	@SCJRestricted(Phase.INITIALIZE)
	public MissionSequencer(PriorityParameters priority, StorageParameters storage, String name)
			throws IllegalStateException {
		super(priority, new AperiodicParameters(), storage);
		this.name = name;

		//		devices.Console.println("MissSeq.constr: " + name 
		//			+ "; maxMissionMemory " + storage.maxMissionMemory 
		//			+ "; backingstore: " + this.privateMemory + "; isOuterMost: " + isOuterMostSeq);
		missionMemory = new MissionMemory((int) storage.maxMissionMemory, // mission memory
				privateMemory, //backingstore of sequencer
				name);

		currState = State.START;
		
		if(Launcher.level != 0)
			Services.setCeiling(this, this.priority.getPriority());
		
		ManagedEventHandler.handlerBehavior.initMissionSequencer(this);
	}

	@SCJAllowed
	@SCJRestricted(Phase.INITIALIZE)
	public MissionSequencer(PriorityParameters priority, final StorageParameters storage) throws IllegalStateException {
		this(priority, storage, "MisMem");
	}

	synchronized void seqWait() {
		if (!Launcher.useOS) {
			while (!currMission.terminationPending() && currMission.msSetForMission.msCount > 0) {
				//devices.Console.println("MS.seqWait msCount:" + currMission.msSetForMission.msCount);
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			while (currMission.msSetForMission.activeCount > 0) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
		}
	}

	synchronized void seqNotify() {
		if (!Launcher.useOS) {
			devices.Console.println("MS.seqNotify msCount:" + currMission.msSetForMission.msCount);
			if (currMission.msSetForMission.msCount == 0) {
				notify();
			}
		} else {
			currMission.msSetForMission.activeCount--;
			if (currMission.msSetForMission.activeCount == 0) {
				notify();
			}
		}
	}

	/**
	 * This method is declared final because the implementation is provided by
	 * the infrastructure of the SCJ implementation and shall not be overridden. <br>
	 * This method performs all of the activities that correspond to sequencing
	 * of <code>Mission</code>s by this <code>MissionSequencer</code>.
	 */
	@SCJAllowed(Level.INFRASTRUCTURE)
	public final void handleAsyncEvent() {
		do {
			// the main actions of the sequencer governed by currState
			switch (currState) {
			case State.START:
				//devices.Console.println("MS.S: " + name);
				currMission = getNextMission();

				if (currMission != null) {
					howManyMissions++;
				} else
					howManyMissions--;

				if (currMission == null || terminateSeq) {
					terminateSeq = true;
					currState = State.TERMINATE;
				} else {
					currMission.missionTerminate = false;
					currState = State.INITIALIZE;
				}
				break;

			case State.INITIALIZE:
				//devices.Console.println("MS.I: " + name);
				currMission.setMissionSeq(this);
				missionMemory.enterToInitialize(currMission);
				currState = State.EXECUTE;
				break;

			case State.EXECUTE:
				//devices.Console.println("MS.E");
				ManagedEventHandler.handlerBehavior.missionSequencerExecutePhase(this);
				currState = State.CLEANUP;
				break;

			case State.CLEANUP:
				//devices.Console.println("MS.C: " + name);

				missionMemory.enterToCleanup(currMission);
				missionMemory.resizeArea(storage.maxMissionMemory);

				// handleAsyncEvent continues
				currState = State.START;
				break;

			case State.TERMINATE:
				ManagedEventHandler.handlerBehavior.cleanOuterMissionSequencer(this);
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
	protected abstract MissionType getNextMission();

	public final void register() {
		super.register();
	}

	@Override
	public void signalTermination() {
		ManagedEventHandler.handlerBehavior.missionSequencerSingleTermination(this);
	}

	public void cleanUp() {
		super.cleanUp();
		missionMemory.removeArea();
	}

	MissionSequencer<?> getOuterSeq() {
		return outerSeq;
	}

	// used for JML annotation only (not public)
	MissionMemory getMissionMemory() {
		return missionMemory;
	}

	// used for JML annotation only (not public)
	int getLevel() {
		return Launcher.level;
	}

	// used for JML annotation only (not public)
	boolean isOuterMostSeq() {
		return isOuterMostSeq;
	}
	
	Monitor getLock() {
		return lock;
	}
}
