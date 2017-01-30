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
 *             
 *  @version 1.3 2016-10-18           
 *************************************************************************/
package javax.safetycritical;

import javax.realtime.AperiodicParameters;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.annotate.AllocationContext;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.Phase;
import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.annotate.SCJMayAllocate;
import javax.safetycritical.annotate.SCJMaySelfSuspend;
import javax.safetycritical.annotate.SCJPhase;

/**
 * A <code>MissionSequencer</code> oversees a sequence of Mission executions. 
 * The sequence may include interleaved execution of independent missions 
 * and repeated executions of missions.<p>
 * As a subclass of <code>ManagedEventHandler</code>, <code>MissionSequencer</code>'s 
 * execution priority and memory budget are specified by constructor parameters. <p>
 * This <code>MissionSequencer</code> executes vendor-supplied infrastructure code which
 * invokes user-defined implementations of <code>getNextMission</code>,
 * <code>Mission.initialize</code> and <code>Mission.cleanUp</code>.
 * During execution of a mission, the <code>MissionSequencer</code> remains blocked waiting for
 * the mission to terminate. An invocation of <code>signalTermination</code> will unblock it
 * to invoke the running mission's <code>requestTermination</code> method,
 */
@SCJAllowed
public abstract class MissionSequencer extends ManagedEventHandler {

	MissionMemory missionMemory;
	Mission currMission;

	interface State {
		public final static int START = 1;
		public final static int INITIALIZE = 2;
		public final static int EXECUTE = 3;
		public final static int CLEANUP = 4;
		public final static int TERMINATE = 5;
		public final static int END = 6;
	}

	int currState;
	
	Phase phase;  // used for JML test
	boolean currentSeqIsOuterMost = false;  // used for JML test
	
	boolean terminateSeq = false;
	static volatile boolean isOuterMostSeq = true; 

	static ScjProcess missSeqProcess = null;

	static int howManyMissions = -1; // for testing only

	// Level2 only: a reference to the nearest outer sequencer
	MissionSequencer outerSeq = null;
	static MissionSequencer outerMostSeq = null; // for multiprocessor only; also used in single-processor
	
	Monitor lock;

	/**
	 * Construct a <code>MissionSequencer</code> object to oversee a sequence of mission executions.
	 * 
	 * @param priority - The priority at which the <code>MissionSequencer</code> executes.
	 * @param storage - specifies the <code>ScopeParameters</code> for this handler.
	 * @param config - specifies the <code>ConfigurationParameters</code> for this handler.
	 * @param name - The name by which this <code>MissionSequencer</code> will be identified.
	 * 
	 * @throws <code>IllegalStateException</code> if invoked in an inappropriate phase.
	 */
	@SCJAllowed
	@SCJPhase({Phase.STARTUP, Phase.INITIALIZATION})
	@SCJMaySelfSuspend(false)
	@SCJMayAllocate({
		AllocationContext.CURRENT,
		AllocationContext.INNER,
		AllocationContext.OUTER})
	public MissionSequencer(PriorityParameters priority, StorageParameters storage, 
			ConfigurationParameters config, String name)
			throws IllegalStateException {
		super(priority, new AperiodicParameters(), storage, config, name);

//		System.out.println("MissSeq.constr: " + name 
//			+ "; maxMissionMemory " + storage.maxMissionMemory 
//			+ "; backingstore: " + this.privateMemory + "; isOuterMost: " + isOuterMostSeq);
		
		missionMemory = new MissionMemory((int) storage.maxMissionMemory, // mission memory
				privateMemory, //backingstore of sequencer
				name);
		
		currState = State.START;
		phase = Phase.INITIALIZATION;
		
		if(Launcher.level != 0)
			Services.setCeiling(this, this.priority.getPriority());
		ManagedEventHandler.handlerBehavior.initMissionSequencer(this);
		
		if  ( TestPortalSC.getLevel() < 2 && ! currentSeqIsOuterMost )
			throw new IllegalStateException ("MissionSequencer not in appropriate phase)") ;
	}

	/** 
	 * This constructor behaves the same as calling <code>MissionSequencer(priority, storage, null)</code>.
	 */
	@SCJAllowed
	@SCJPhase({Phase.STARTUP, Phase.INITIALIZATION})
	@SCJMaySelfSuspend(false)
	@SCJMayAllocate({
		AllocationContext.CURRENT,
		AllocationContext.INNER,
		AllocationContext.OUTER})
	public MissionSequencer(PriorityParameters priority, StorageParameters storage,
			ConfigurationParameters config) throws IllegalStateException {
		this(priority, storage, config, null); //default name: "MissSeq" ; used internal by icecap utility tool ??
	}

	synchronized void seqWait() {
		if (!Launcher.useOS) {
			while (!currMission.terminationPending() && currMission.hasSchedulables()) {
				//devices.Console.println("MS.seqWait msCount:" + currMission.msSetForMission.msCount);
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			while (currMission.activeCount > 0) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
		}
	}

	synchronized void seqNotify() {
		if (!Launcher.useOS) {
			if (!currMission.hasSchedulables()) {
				notify();
			}
		} else {
			currMission.activeCount--;
			if (currMission.activeCount == 0) {
				notify();
			}
		}
	}

	/**
	 * This method is used in the implementation of SCJ infrastructure. 
	 * The method is not to be invoked by application code 
	 * and it is not to be overridden by application code.
	 */
	@SCJAllowed(javax.safetycritical.annotate.Level.SUPPORT)
	@Override
	@SCJMaySelfSuspend(true)
	@SCJMayAllocate({
	    AllocationContext.CURRENT, AllocationContext.INNER, AllocationContext.OUTER})
	@SCJPhase({ Phase.STARTUP, Phase.INITIALIZATION, Phase.RUN, Phase.CLEANUP })
	public final void handleAsyncEvent() {
		do {
			// the main actions of the sequencer governed by currState
			switch (currState) {
			case State.START:
				//devices.Console.println("MS.S: " + this.getName() );
				phase = Phase.STARTUP;
				
				currMission = getNextMission();

				if (currMission != null) {
					//devices.Console.println("MS.S: " + currMission + "; memArea is: " + MemoryArea.getMemoryArea(currMission));
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
				phase = Phase.INITIALIZATION;
				currMission.setMissionSeq(this);
				missionMemory.enterToInitialize(currMission);
				currState = State.EXECUTE;
				break;

			case State.EXECUTE:
				//devices.Console.println("MS.E");
				phase = Phase.RUN;
				ManagedEventHandler.handlerBehavior.missionSequencerExecutePhase(this);
				currState = State.CLEANUP;
				break;

			case State.CLEANUP:
				//devices.Console.println("MS.C: " + name);

				phase = Phase.CLEANUP;
				
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
		
		//CyclicScheduler.instance().stop(missSeqProcess.process);
	}

	/**
	 * This method is called by the infrastructure to select the initial mission to execute,
	 * and subsequently, each time one mission terminates, to determine the next mission to execute.
	 * <br>
	 * Prior to each invocation of <code>getNextMission</code>,
	 * infrastructure initializes and enters the mission memory allocation area.
	 * The <code>getNextMission</code> method may allocate the returned mission within this newly 
	 * instantiated mission memory allocation area, or it may return a reference to a <code>Mission</code> object 
	 * that was allocated in some outer-nested mission memory area or in the <code>ImmortalMemory</code> area.
	 * 
	 * @return the next mission to run, or null if no further missions are to run under the control of this
	 *         <code>MissionSequencer</code>.
	 */
	@SCJAllowed(Level.SUPPORT)
	@SCJPhase({Phase.STARTUP, Phase.INITIALIZATION, Phase.CLEANUP})
	@SCJMaySelfSuspend(false)
	@SCJMayAllocate({
		AllocationContext.CURRENT,
		AllocationContext.INNER,
		AllocationContext.OUTER})
	protected abstract Mission getNextMission();

	/** 
	 * Called by the infrastructure to indicate that the enclosing mission has been 
	 * instructed to terminate.<br> 
	 * The sole responsibility of this method is to call <code>requestTermination</code> on the currently 
	 * running mission.<br> 
	 * <code>signalTermination</code> will never be called by a Level 0 or Level 1 infrastructure.
	 */
	@Override
	@SCJAllowed(Level.SUPPORT)
	@SCJMayAllocate({})
	@SCJMaySelfSuspend(false)
	@SCJPhase({Phase.STARTUP, Phase.INITIALIZATION, Phase.RUN, Phase.CLEANUP })
	public final void signalTermination() {
		super.signalTermination();
		if (currMission != null) currMission.requestTermination();
		
		ManagedEventHandler.handlerBehavior.missionSequencerSingleTermination(this);
	}

	public void cleanUp() {
		super.cleanUp();
		missionMemory.removeArea();
	}

	MissionSequencer getOuterSeq() {
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
		return currentSeqIsOuterMost;
	}
	
	// used for JML annotation only (not public)
	Phase getPhase() {
		return phase;
	}
	
	Monitor getLock() {
		return lock;
	}
}


