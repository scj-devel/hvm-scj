/**************************************************************************
 * File name  : ScjProcess.java
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
 * Description: 
 * 
 * Revision history:
 *   date   init  comment
 *
 *************************************************************************/

package javax.safetycritical;

import icecaptools.IcecapCompileMe;
import util.StringUtil;

import javax.realtime.AbsoluteTime;
import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.TestPortalRT;
import javax.realtime.memory.ScopeParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.Memory;
import vm.ProcessLogic;
import vm.RealtimeClock;

/**
 * Defines the VM process context for an executing Java program.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 * 
 * @scjComment - implementation issue: infrastructure class; not part of the SCJ
 *             specification.
 */
class ScjProcess extends Process implements Comparable<ScjProcess> {
	vm.Process process;

	Clock rtClock;
	AbsoluteTime next; // next activation time

	RelativeTime start;
	RelativeTime period;

	Object monitorLock = null;
	AbsoluteTime next_temp = null;
	boolean isNotified = false;
	
	boolean waitForObjectLock = false;  // HSO

	private ExceptionReporter exceptionReporter;

	interface State {
		public final static byte NEW = 0;
		public final static byte READY = 1;
		public final static byte EXECUTING = 2;
		public final static byte BLOCKED = 3;
		public final static byte SLEEPING = 4;
		public final static byte HANDLED = 5;
		public final static byte TERMINATED = 6;

		public final static byte WAITING = 7;
		public final static byte REQUIRELOCK = 8;
	}

	/**
	 * The constructor initializes a new VM process object
	 * 
	 * @param target
	 * 	is the handler containing the handleAsyncEvent method 
	 * 	to be executed
	 * @param stack
	 *   is the run time stack
	 */
	ScjProcess(ManagedSchedulable ms, int[] stack) {
		this.rtClock = Clock.getRealtimeClock();
		this.next = new AbsoluteTime(this.rtClock);
		this.msObject = ms;
		this.state = State.NEW;
		this.exceptionReporter = new ExceptionReporter();
		
		this.process = new vm.Process(new ProcessLogic() {
			public void run() {
				try {
					runLogic(msObject);
				} catch (Throwable e) {
					Const.reporter.processExecutionError(e);
					
					if (msObject instanceof MissionSequencer) 
						((MissionSequencer)msObject).currState = MissionSequencer.State.END;
					
				} finally {
					//System.out.println("ScjProcess.run.finally");
					if (msObject instanceof PeriodicEventHandler) {
						next.add(period, next); // next = next + period
					}	
										
					state = State.HANDLED;
				}
			}

			public void catchError(final Throwable t) {
				exceptionReporter.t = t;
				try {
					ImmortalMemory immortal = ImmortalMemory.instance();
					if (immortal != null) {
						immortal.executeInArea(exceptionReporter);
					} else {
						Memory.executeInHeap(exceptionReporter);
					}
				} catch (OutOfMemoryError o) {
					Const.reporter.processOutOfMemoryError(o);
				}
			}
		}, stack);

		this.process.initialize();
		
		rtClock.getTime(this.next);

		setRelease(msObject);

		setProcess(msObject);
	}

	private void runLogic(ManagedSchedulable ms) {	
		//System.out.println("*** ScjProcess.runLogic for: " + ms);
		if (ms instanceof ManagedEventHandler)
			((ManagedEventHandler) ms).privateMemory.enter(ms); // execute logic;  
		else if (ms instanceof ManagedThread)
			((ManagedThread) ms).privateMemory.enter(ms);
		else {
			// (ms is instanceof ManagedLongEventHandler)
			((ManagedLongEventHandler) ms).privateMemory.enter(ms);
		}
	}

	private void setRelease(ManagedSchedulable ms) {
		//System.out.println("*** ScjProcess.setRelease: " + ms);
		if (ms instanceof PeriodicEventHandler) {
			//this.start = ((PeriodicParameters) ((PeriodicEventHandler) ms).release).getStart();
			this.start = TestPortalRT.start((PeriodicParameters) ((PeriodicEventHandler) ms).release);
			//this.period = ((PeriodicParameters) ((PeriodicEventHandler) ms).release).getPeriod();
			this.period = TestPortalRT.period((PeriodicParameters) ((PeriodicEventHandler) ms).release);
			next.add(start, next); // next = next + start
		} else if (ms instanceof OneShotEventHandler) {
			if (((OneShotEventHandler) ms).releaseTime instanceof RelativeTime) {
				RelativeTime releaseTime = (RelativeTime) ((OneShotEventHandler) ms).releaseTime;
				next.add(releaseTime, next); // next = next + releaseTime
			} else {
				AbsoluteTime releaseTime = (AbsoluteTime) ((OneShotEventHandler) ms).releaseTime;
				int compare = releaseTime.compareTo(Clock.getRealtimeClock().getTime(new AbsoluteTime(rtClock)));
				if (compare < 0)
					next.add(new RelativeTime(), next);
				else
					next.set(releaseTime);
			}
		} else if (ms instanceof AperiodicEventHandler || ms instanceof AperiodicLongEventHandler) {
			
			rtClock.getTime(this.next);  // has been done in constructor
		}
		else if (ms instanceof MissionSequencer) {
			
			;  // do nothing
		}
		else
			devices.Console.println("UPS: ScjProcess.setRelease: more cases? ManagedThreads?");
	}

	private static class ExceptionReporter implements Runnable {
		Throwable t;

		public void run() {
			Const.reporter.processExecutionError(t);
		}
	}

	public String toString() {
		return StringUtil.constructString("ScjProcess:" + msObject + " index: ", this.index);
	}

	/**
	 * Compares this process with the parameter process. HSO: The ordering of
	 * the processes are done after growing priorities.
	 * 
	 * APR: The ordering of the processes are done after next release and
	 * priority:
	 * 
	 * The "smallest" of the two processes is the process with the smallest (the
	 * first) next release time; if the processes have the same release time,
	 * the process with the highest priority comes first (is the "smallest").
	 */
	public int compareTo(ScjProcess process) {
		// HSO: two queues in PriorityFrame: PriorityQueue and SleepingQueue (works)
		//		return (this.target.priority - process.target.priority)

		if (msObject instanceof ManagedEventHandler && process.msObject instanceof ManagedEventHandler)
			return (((ManagedEventHandler) msObject).priority.getPriority() - ((ManagedEventHandler) process.msObject).priority
					.getPriority());

		if (msObject instanceof ManagedThread && process.msObject instanceof ManagedThread)
			return (((ManagedThread) msObject).priority.getPriority() - ((ManagedThread) process.msObject).priority
					.getPriority());

		if (msObject instanceof ManagedLongEventHandler && process.msObject instanceof ManagedLongEventHandler)
			return (((ManagedLongEventHandler) msObject).priority.getPriority() - ((ManagedLongEventHandler) process.msObject).priority
					.getPriority());

		return -999; // HSO: not finished

	}

	ManagedSchedulable getTarget() {
		return msObject;
	}

	void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Idle process is created and put in readyQueue, so that readyQueue will
	 * never be empty. Idle process has lowest priority. <br>
	 * 
	 * Idle process is a periodic handler with "infinite" period.
	 */

	static ScjProcess idleProcess;

	/**
	 * Creates and returns the singleton idle process. If idle process is
	 * already created, no new process is created.
	 * 
	 * @return Returns the singleton idle process.
	 */
	static ScjProcess createIdleProcess() {
		if (idleProcess == null) {
			RelativeTime INFINITE_TIME = new RelativeTime(365 * 24 * 60 * 1000, 0, Clock.getRealtimeClock());
			
			PeriodicEventHandler peh = new PeriodicEventHandler(new PriorityParameters(Priorities.MIN_PRIORITY),
					new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), // start (0,0)
							INFINITE_TIME), // period
//					new StorageParameters(2 * Const.IDLE_BACKING_STORE, new long[] { Const.IDLE_PROCESS_STACK_SIZE },
//							2 * Const.IDLE_BACKING_STORE, 0, 0)
					new ScopeParameters(2 * Const.IDLE_BACKING_STORE, 0, 0, 0),
					new ConfigurationParameters (-1, -1, new long[] { Const.IDLE_PROCESS_STACK_SIZE })
					) 
				{
					public void handleAsyncEvent() {
						yield();
				}

				@IcecapCompileMe
				private void yield() {
					while (true) {
						RealtimeClock.waitForNextTick();
					}
				}
			};

			ScjProcess process = new ScjProcess(peh, new int[Const.IDLE_PROCESS_STACK_SIZE]);

			process.rtClock.getTime(process.next);
			process.index = -1;
			idleProcess = process;
		}
		return idleProcess;
	}

	public void start() {
		process.initialize();
	}

	String print() {
		return StringUtil.constructString("name: " + this.msObject + " 	index: ", index);
	}

	protected boolean nextState(PriorityFrame pFrame) {
		return false;
	}

	protected void gotoNextState(PriorityFrame pFrame) {
	}

	void switchToPrivateMemArea() {
		;
	}
}
