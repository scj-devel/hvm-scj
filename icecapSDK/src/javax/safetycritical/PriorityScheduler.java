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
import vm.MachineFactory;

import javax.realtime.AbsoluteTime;
import javax.realtime.Clock;
import javax.realtime.HighResolutionTime;
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
public class PriorityScheduler extends javax.realtime.PriorityScheduler {

	PriorityFrame pFrame;

	ScjProcess current;
	PrioritySchedulerImpl prioritySchedulerImpl;

	Clock rtClock;
	RelativeTime timeGrain;
	AbsoluteTime now;

	private static PriorityScheduler scheduler; 

	ScjProcess outerMostSeqProcess = null;

	/**
	 * 
	 * @return The priority scheduler.
	 */
	@SCJAllowed(Level.LEVEL_1)
	public static PriorityScheduler instance() {
		/* Do not remove lines below to change initialization of the 
		 * PriorityScheduler. This will break a lot of tests.
		 */
		if (scheduler == null) {
			scheduler = new PriorityScheduler();
		}
		return scheduler;
	}

	private PriorityScheduler() {
		int[] schedulerStack = new int[Const.PRIORITY_SCHEDULER_STACK_SIZE];

		this.pFrame = new PriorityFrame(Const.DEFAULT_PRIORITY_QUEUE_SIZE);

		this.prioritySchedulerImpl = new PrioritySchedulerImpl();

		vm.ClockInterruptHandler.initialize(this.prioritySchedulerImpl, schedulerStack);

		this.rtClock = Clock.getRealtimeClock();
		this.now = new AbsoluteTime(this.rtClock);
		rtClock.getTime(this.now);

		this.timeGrain = new RelativeTime(0, 0, this.rtClock);
		rtClock.getResolution(this.timeGrain);
		scheduler = this;
	}

	void addOuterMostSeq(MissionSequencer seq) {
		ScjProcess process = ManagedSchedMethods.createScjProcess(seq);
		process.index = -2;
		MissionSequencer.missSeqProcess = process;
		outerMostSeqProcess = (ScjProcess) seq.process;
		pFrame.addProcess(process);
	}

    void stop(vm.Process current) {
		terminateScheduler(current);
	}

	void start(MachineFactory mFactory) {
		current = pFrame.readyQueue.extractMax();
		System.out.println("\nPrScheduler.start. current: " + current);
		startScheduler(mFactory);
		System.out.println("\nPrScheduler.start finished");
	}

	void release(AperiodicEventHandler handler) {
		// see AperiodicEventHandler, where release is called
		ScjProcess process = (ScjProcess) handler.process;
		vm.ClockInterruptHandler.instance.disable();
		if (process.state == ScjProcess.State.EXECUTING) {
			; // do nothing, - is already running
		}

		else if (process.state == ScjProcess.State.BLOCKED) {
			process.state = ScjProcess.State.READY;
			process.start();
			pFrame.readyQueue.insert(process);
		} else {
			; // it is already ready
		}
		vm.ClockInterruptHandler.instance.enable();
		vm.ClockInterruptHandler.instance.yield();
	}

	@IcecapCompileMe
	ScjProcess move() {
		if (current == ScjProcess.idleProcess) {
			pFrame.readyQueue.insert(current);
		} else {
			current.gotoNextState(pFrame);
		}

		// Move processes from sleepingQueue to readyQueue
		ScjProcess process = pFrame.sleepingQueue.minimum();
		rtClock.getTime(now);

		while (process != null && process.next.compareTo(now) <= 0) {
			
			ScjProcess p = pFrame.sleepingQueue.extractMin();
			
			//devices.Console.println("PrSch.move: sleepingQueue.extractMin; process: " + p + "\n");
			
			if (p.waitForObjectLock) {
				
				p.waitForObjectLock = false;
								
				if (! p.isNotified) {
					
					p.isNotified = true;					

					devices.Console.println("PrSch.move: t.isNotified: " + p.isNotified);
					
					p.next_temp = null;
					p.state = ScjProcess.State.REQUIRELOCK;
					PriorityScheduler.instance().pFrame.waitQueue.removeProcess(p);
					
					Monitor monitor = Monitor.getMonitor(p);  
					devices.Console.println("PrSch.move: p.monitorLock: " + monitor);
					
					monitor.lockWithOutEnable();
				}
			}
			else {
				p.state = ScjProcess.State.READY;				
				
				//devices.Console.println("PrSch.move: sleep --> ready: ; process: " + p);
	
				pFrame.readyQueue.insert(p);
			}
			
			// look at "next" process in sleeping queue with smallest activationTime
			process = pFrame.sleepingQueue.minimum();
			
		}

		// get next process from readyQueue
		ScjProcess nextProcess = pFrame.readyQueue.extractMax();
		nextProcess.state = ScjProcess.State.EXECUTING;
		current = nextProcess;

		if (current == ScjProcess.idleProcess && pFrame.sleepingQueue.heapSize == 0 && pFrame.waitQueue.heapSize == 0
				&& pFrame.lockQueue.heapSize == 0) {
			//devices.Console.println("PrioritySch.move: " + current.getTarget());
			//current.getTarget().cleanUp();  // HSO: is this call of cleanUp() necessary ? Then the TCK-test does not work
			return null;
		} else {

			return nextProcess;
		}
	}

	ScjProcess getCurrentProcess() {
		return current;
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

	void insertReadyQueue(ScjProcess process) {
		pFrame.readyQueue.insert(process);
	}

	void addProcessToLockQueue(Object target, ScjProcess process) {
		pFrame.lockQueue.addProcess(target, process);
	}

	ScjProcess getProcessFromLockQueue(Object monitor) {
		return pFrame.lockQueue.getNextProcess(monitor);
	}

	void wait(Object target) {
		prioritySchedulerImpl.wait(target);
	}

	void notify(Object target) {
		prioritySchedulerImpl.notify(target);
	}

	void notifyAll(Object target) {
		prioritySchedulerImpl.notifyAll(target);
	}
	
	boolean waitForObject(Object target, HighResolutionTime<?> time) {
		return prioritySchedulerImpl.waitForObject(target, time);
	}

	void moveToNext() {
		ScjProcess nextProcess = pFrame.readyQueue.extractMax();
		nextProcess.state = ScjProcess.State.EXECUTING;
		current = nextProcess;
		//devices.Console.println("<<< From readyQueue to current: " + nextProcess.index);
	}

	/**
	 * Print out the contents of the queues.
	 * For testing only.
	 */
	public void printQueues() {
		vm.ClockInterruptHandler.instance.disable();
		devices.Console.println("");
		//		devices.Console.println("PriorityScheduler: current process: "
		//				+ current.getTarget() + "; index: " + current.index);
		//devices.Console.println("----------- ready queue ----------");
		pFrame.readyQueue.print();
		//devices.Console.println("----------- sleeping queue ----------");
		pFrame.sleepingQueue.print();
		//devices.Console.println("----------- lock queue ----------");
		pFrame.lockQueue.print();
		//devices.Console.println("----------- wait queue ----------");
		pFrame.waitQueue.print();
		devices.Console.println("");
		vm.ClockInterruptHandler.instance.enable();
	}
}