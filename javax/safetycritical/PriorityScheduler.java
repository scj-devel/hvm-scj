/**************************************************************************
 * File name  : PriorityScheduler.java
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

import icecaptools.IcecapCompileMe;

import javax.realtime.Clock;
import javax.realtime.RelativeTime;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

/**
 * This class represents the priority-based scheduler for Level 1 and 2. <br>
 * The only access to the priority scheduler is for obtaining the software
 * priorities.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 */
@SCJAllowed(Level.LEVEL_1)
public class PriorityScheduler extends javax.realtime.PriorityScheduler 
{
	PriorityFrame pFrame;

	ScjProcess current;
	PrioritySchedulerImpl prioritySchedulerImpl;

	Clock rtClock;	
	RelativeTime timeGrain;

	private static PriorityScheduler scheduler; // = new PriorityScheduler(); 
	/**
	 * 
	 * @return The priority scheduler.
	 */
	@SCJAllowed(Level.LEVEL_1)
	public static PriorityScheduler instance() {
		if (scheduler == null)
			scheduler = new PriorityScheduler(); 
		return scheduler;
	}

	private PriorityScheduler() {
		int[] schedulerStack = new int[Const.PRIORITY_SCHEDULER_STACK_SIZE];

		this.pFrame = new PriorityFrame(Const.DEFAULT_PRIORITY_QUEUE_SIZE);

		this.prioritySchedulerImpl = new PrioritySchedulerImpl();

		vm.ClockInterruptHandler.initialize(this.prioritySchedulerImpl, schedulerStack);

		this.rtClock = Clock.getRealtimeClock();		
		this.timeGrain = new RelativeTime (0, 0, this.rtClock); 
		rtClock.getResolution(this.timeGrain);
	}

	void add(ManagedEventHandler handler, int[] stack) {
		ScjProcess process = new ScjProcess(handler, stack);
		if (handler instanceof MissionSequencer<?>) {
			process.index = -2;
			MissionSequencer.missSeqProcess = process;
		} else {
			process.index = Mission.currHandlerSet.indexOf(handler);
		}
		pFrame.addProcess(process);
	}
	
	//----27 Dec 2013 -------------------------------------
	
	private vm.Process mainProcess;
	
	private void processStart() {
		vm.ClockInterruptHandler clockHandler = vm.ClockInterruptHandler.instance;
		mainProcess = new vm.Process(null, null);
		
		clockHandler.register();
		clockHandler.enable();
		clockHandler.startClockHandler(mainProcess);
		clockHandler.yield();
	}
	
	@IcecapCompileMe
	void stop(vm.Process current)
	{
		current.transferTo(mainProcess);
	}
	
	//----27 Dec 2013 end -----------------------------------

	void start() {
		// put idle process in readyQueue
		pFrame.queue.insert(ScjProcess.createIdleProcess());
		current = pFrame.queue.extractMin();		
		processStart();
	}

	void release(AperiodicEventHandler handler) {
		// see AperiodicEventHandler, where release is called
		
		vm.ClockInterruptHandler.instance.disable();
		if (handler.process.state == ScjProcess.State.EXECUTING) {
			; // do nothing, - is already running
		}

		else if (handler.process.state == ScjProcess.State.BLOCKED) {
			handler.process.state = ScjProcess.State.READY;
			pFrame.queue.insert(handler.process);
		} else {
			; // it is already ready
		}
		vm.ClockInterruptHandler.instance.enable();
	}

	@IcecapCompileMe
	ScjProcess move() {
		if (current == ScjProcess.idleProcess) {
			// add a time grain to the idle process, so it is pushed back in the queue
			rtClock.getTime(current.next);
			current.next.add(timeGrain,current.next);
			
			pFrame.queue.insert(current);
		}

		// periodic handlers
		else if (current.target instanceof PeriodicEventHandler) {
			if (current.state == ScjProcess.State.HANDLED) {
				// finished executing handleAsyncEvent

				if (Mission.getCurrentMission().terminationPending()) {
					Mission.currHandlerSet.removeHandler(current.target);

					current.state = ScjProcess.State.TERMINATED;
				} else {
					current.state = ScjProcess.State.SLEEPING;
					current.start();
					
					pFrame.queue.insert(current);
				}
			} else {
				// handler was preempted
				current.state = ScjProcess.State.READY;
				
				pFrame.queue.insert(current);
			}
		}

		else if (current.target instanceof MissionSequencer<?>) {
			if (current.state == ScjProcess.State.HANDLED) {
				// missionSequencer terminates

				current.target.cleanUp();
				current.state = ScjProcess.State.TERMINATED;
			} else {
				// handler was preempted
				current.state = ScjProcess.State.READY;
				
				// add a time grain to the mission sequencer process, so it is pushed back in the queue
				rtClock.getTime(current.next);
				current.next.add(timeGrain, current.next);
				
				pFrame.queue.insert(current);
			}
		}

		else if (current.target instanceof AperiodicEventHandler) {
			if (current.state == ScjProcess.State.HANDLED) {
				// AperiodicEventHandler finished handling

				if (Mission.getCurrentMission().terminationPending()) {
					Mission.currHandlerSet.removeHandler(current.target);


					// remove the rest of aperiodic handlers
					Mission.currHandlerSet.removeAperiodicHandlers();

					current.state = ScjProcess.State.TERMINATED;
				} 					
				else 
					current.state = ScjProcess.State.BLOCKED;
			} 			
			else {
				// handler was preempted or in state NEW (first release)
				current.state = ScjProcess.State.READY;
				
				pFrame.queue.insert(current);
			}
		}
		
		else if (current.target instanceof OneShotEventHandler) 
		{
			if (current.state == ScjProcess.State.HANDLED) {
				// oneShotHandler finished
				Mission.currHandlerSet.removeHandler(current.target);
				current.state = ScjProcess.State.TERMINATED;
			} else {
				// handler was preempted
				current.state = ScjProcess.State.READY;
				
				pFrame.queue.insert(current);
			}
		} 
		
		else {
			throw new IllegalArgumentException("PriorityScheduler.move: UPS: another handler??");
		}
		
		// get next process from queue		
		ScjProcess nextProcess = pFrame.queue.extractMin();
		
		nextProcess.state = ScjProcess.State.EXECUTING;
		current = nextProcess;

		if ((current == ScjProcess.idleProcess) && (pFrame.queue.heapSize == 0)) {
			current.target.cleanUp();
			return null;
		} else {
			
			return nextProcess;
		}
	}	

	/**
	 * 
	 * @return The maximum hardware real-time priority supported by this
	 *         scheduler.
	 */
	@SCJAllowed(Level.LEVEL_1)
	public int getMaxHardwarePriority() {
		return Priorities.MAX_HARDWARE_PRIORITY;
	}

	/**
	 * 
	 * @return The minimum hardware real-time priority supported by this
	 *         scheduler.
	 */
	@SCJAllowed(Level.LEVEL_1)
	public int getMinHardwarePriority() {
		return Priorities.MIN_HARDWARE_PRIORITY;
	}
}
