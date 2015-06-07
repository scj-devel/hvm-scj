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
import javax.realtime.BoundAsyncEventHandler;
import javax.realtime.HighResolutionTime;
import javax.realtime.MemoryArea;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.ReleaseParameters;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.Phase;
import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.annotate.SCJRestricted;

import vm.Memory;

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
public abstract class ManagedEventHandler extends BoundAsyncEventHandler implements ManagedSchedulable {

	PriorityParameters priority;
	StorageParameters storage;
	Process process = null;
	Mission mission = null;
	
	ManagedMemory privateMemory;
	ManagedMemory currentMemory;	// for multicore only
	
	ReleaseParameters release;

	String name;
	
	AffinitySet set = null;
	
	// used in JML spec. methods
	boolean isRegistered;
	boolean isInMissionScope;

	static HandlerBehavior handlerBehavior = null;

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
	    requires release != null;  
	
	//	    ensures this.getPriorityParam().getPriority() == priority.getPriority();  
	//	    ensures this.getReleaseParam().getDeadline() == release.getDeadline();
	//	    ensures this.getReleaseParam().getMissHandler() == release.getMissHandler();
	
	  also
	  public exceptional_behavior
	    requires priority == null;
	    signals (IllegalArgumentException) true;
	  also
	  public exceptional_behavior
	    requires release == null;
	    signals (IllegalArgumentException) true;        
	  @*/
	public ManagedEventHandler(PriorityParameters priority, ReleaseParameters release, StorageParameters storage) {
		this(priority, release, storage, null);
	}

	ManagedEventHandler(PriorityParameters priority, ReleaseParameters release, StorageParameters storage,
			String name) {
		if (priority == null)
			throw new IllegalArgumentException("priority is null");
		if (release == null)
			throw new IllegalArgumentException("release is null");
		if (storage == null)
			throw new IllegalArgumentException("storage is null");
		this.priority = priority;
		this.release = release;
		this.storage = storage;
		this.name = name;
		this.mission = Mission.getMission();

		int backingStoreOfThisMemory;

		if (mission == null && this instanceof MissionSequencer) {
			backingStoreOfThisMemory = MemoryArea.getRemainingMemorySize();
			currentMemory = ManagedMemory.ImmortalMemory.instance();
		} else {
			backingStoreOfThisMemory = (int) this.storage.totalBackingStore;
			if(mission !=null){
				this.currentMemory = mission.currMissSeq.missionMemory;
				this.set = mission.currMissSeq.set;
			}
				
		}

		MemoryArea backingStoreProvider = (mission == null) ? 
				MemoryArea.overAllBackingStore : mission.currMissSeq.missionMemory;

		String privateMemoryName = Memory.getNextMemoryName("PvtMem");		
		
		privateMemory = new PrivateMemory((int) this.storage.getMaximalMemoryArea(),
		           backingStoreOfThisMemory,
	               backingStoreProvider, 
	               privateMemoryName);	
		
		this.isRegistered = false;
		this.isInMissionScope = false;
	}

	/*@ 
	  public behavior
	    requires true;
	 
//	    ensures javax.realtime.Clock.getRealtimeClock().getTime().compareTo(
//	              getLastReleaseTime().add(release.getDeadline())) <= 0;
	  @*/
	public abstract void handleAsyncEvent();

	//	//@ also
	//  //@   requires true;
	//  //@   ensures ??;	// something to add?
	@SCJAllowed(Level.SUPPORT)
	@SCJRestricted(Phase.CLEANUP)
	public void cleanUp() {
		privateMemory.removeArea();
	}

	/**
	 * Registers this event handler with the current mission.
	 */
	//	//@ also
	//  //@   requires true;
	//  //@   ensures ??;	// something to add?
	@SCJAllowed(Level.INFRASTRUCTURE)
	@SCJRestricted(Phase.INITIALIZE)
	public void register() {
		ManagedSchedulableSet hs = mission.msSetForMission;
		hs.addMS(this);
		
		isRegistered = true;
		isInMissionScope = true;
	}

	@SCJAllowed(Level.SUPPORT)
	public void signalTermination() {
	}

	@SCJAllowed(Level.LEVEL_1)
	public AbsoluteTime getLastReleaseTime() {
		// ToDo: implementation
		return null;
	}

	Mission getMission() {
		return mission;
	}

	void setName(String name) {
		this.name = name;
	}

	String getName() {
		return name;
	}

	// Used in JML annotations
	/*@ spec_public @*/PriorityParameters getPriorityParam() {
		return priority;
	}

	// Used in JML annotations
	/*@ spec_public @*/ReleaseParameters getReleaseParam() {
		return release;
	}
	
	void setCurrentMemory(ManagedMemory current) {
		this.currentMemory = current;
	}

	ManagedMemory getCurrentMemory() {
		return currentMemory;
	}
	
	AffinitySet getAffinitySet() {
		return set;
	}
	

	static abstract class HandlerBehavior {

		abstract void aperiodicHandlerRelease(AperiodicEventHandler handler);

		abstract boolean oneshotHandlerDeschedule(OneShotEventHandler handler);

		abstract void oneshotHandlerScheduleNextReleaseTime(OneShotEventHandler handler, HighResolutionTime time);

		abstract void initMissionSequencer(MissionSequencer<?> handler);

		abstract void cleanOuterMissionSequencer(MissionSequencer<?> handler);

		abstract void missionSequencerSingleTermination(MissionSequencer<?> handler);

		abstract void missionSequencerExecutePhase(MissionSequencer<?> handler);

	}

	static final class MulticoreBehavior extends HandlerBehavior {

		@Override
		void aperiodicHandlerRelease(AperiodicEventHandler handler) {
			handler.fireNextRelease();
			handler.isReleased = true;
		}

		@Override
		boolean oneshotHandlerDeschedule(OneShotEventHandler handler) {
			if (handler.process.executable.startTimer_c > 0 || handler.state == 0) {
				handler.deschedulePending = true;
				OSProcess.setTimerfd(handler.process.executable.startTimer_c, 0);
				return false;
			} else {
				return true;
			}
		}

		@Override
		void oneshotHandlerScheduleNextReleaseTime(OneShotEventHandler handler, HighResolutionTime time) {
			if (time == null)
				handler.deschedule();
			else {
				if (time instanceof AbsoluteTime) {
					handler.releaseTime = new RelativeTime(0, 0);
				} else if (time instanceof RelativeTime) {
					if (handler.releaseTime.getMilliseconds() < 0
							|| (handler.releaseTime.getMilliseconds() == 0 && handler.releaseTime.getNanoseconds() < 0))
						throw new IllegalArgumentException("release time < 0");
					handler.releaseTime = time;
				} else {
					throw new IllegalArgumentException("wrong time form");
				}

				if (handler.state == 0) {
					OSProcess.setTimerfd(handler.process.executable.startTimer_c, handler.getStart());
				}
				if (handler.state == 2) {
					OSProcess.setTimerfd(handler.process.executable.startTimer_c, handler.getStart());
					handler.fireNextRelease();
				}
			}

		}

		@Override
		void initMissionSequencer(MissionSequencer<?> handler) {
			if (MissionSequencer.isOuterMostSeq) {

				MissionSequencer.outerMostSeq = handler;
				MissionSequencer.isOuterMostSeq = false;

				OSProcess.setOuterMostMissionSequencer(handler.priority.getPriority());
				handler.set = Launcher.level == 1 ? findAffinitySetForLevel1()
						: AffinitySet.AFFINITY_SET[0];
			}

		}
		
		private AffinitySet findAffinitySetForLevel1() {
			int processor = OSProcess.getCurrentCPUID();
			for (int i = 0; i < AffinitySet.AFFINITY_SET.length; i++) {
				if (AffinitySet.AFFINITY_SET[i].processorSet[0] == processor) {
					return AffinitySet.AFFINITY_SET[i];
				}
			}
			throw new NullPointerException();
		}

		@Override
		void cleanOuterMissionSequencer(MissionSequencer<?> handler) {

		}

		@Override
		void missionSequencerSingleTermination(MissionSequencer<?> handler) {
			handler.terminateSeq = true;
			handler.currMission.requestTermination();
		}

		@Override
		void missionSequencerExecutePhase(MissionSequencer<?> handler) {
			handler.missionMemory.enterToExecute(handler.currMission);
		}
	}

	static final class SinglecoreBehavior extends HandlerBehavior {

		@Override
		void aperiodicHandlerRelease(AperiodicEventHandler handler) {
			PriorityScheduler.instance().release(handler);
		}

		@Override
		boolean oneshotHandlerDeschedule(OneShotEventHandler handler) {
			ManagedSchedulableSet hs = Mission.getMission().msSetForMission;

			if (hs.contains(handler)) {
				hs.removeMSObject(handler);
				return true;
			} else
				return false;

		}

		@Override
		void oneshotHandlerScheduleNextReleaseTime(OneShotEventHandler handler, HighResolutionTime time) {
			// to be implement
		}

		@Override
		void initMissionSequencer(MissionSequencer<?> handler) {
			if (MissionSequencer.isOuterMostSeq) {
				MissionSequencer.outerMostSeq = handler;
				if (Launcher.level != 0) {
					PriorityScheduler.instance().addOuterMostSeq(handler);
				}
			} else {
				if (Launcher.level < 2)
					throw new IllegalStateException("MissSeq not outer-most");
				else
					handler.outerSeq = Mission.getMission().currMissSeq;

			}
			MissionSequencer.isOuterMostSeq = false;
			handler.lock = Monitor.getMonitor(handler);
		}

		@Override
		void cleanOuterMissionSequencer(MissionSequencer<?> handler) {
			if (Launcher.level == 2) {
				devices.Console.println("MS.T: " + handler.name + "; #Missions: " + MissionSequencer.howManyMissions
						+ "; outerSeq: " + handler.outerSeq);

				vm.ClockInterruptHandler.instance.disable();
				if (handler.outerSeq != null)
					handler.outerSeq.currMission.msSetForMission.removeMSObject(handler);
				vm.ClockInterruptHandler.instance.enable();
			}
		}

		@Override
		void missionSequencerSingleTermination(MissionSequencer<?> handler) {
			vm.ClockInterruptHandler.instance.disable();
			devices.Console.println("------ MS.signalTermination: " + handler.name);
			handler.terminateSeq = true;
			handler.currMission.requestTermination();
			vm.ClockInterruptHandler.instance.enable();
		}

		@Override
		void missionSequencerExecutePhase(MissionSequencer<?> handler) {
			handler.missionMemory.enterToExecute(handler.currMission);

			// the ms will wait here until it is notified
			if (Launcher.level > 0) {
				handler.seqWait();
			} else {
				while (!handler.currMission.terminationPending() && handler.currMission.msSetForMission.msCount > 0) {
					vm.RealtimeClock.awaitNextTick();
				}
			}

			
		}
	}
}
