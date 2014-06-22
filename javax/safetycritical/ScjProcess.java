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

import javax.realtime.AbsoluteTime;
import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

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
final class ScjProcess implements Comparable<ScjProcess> 
{
	vm.Process process;

	ManagedEventHandler target;

	int state;

	Clock rtClock;
	AbsoluteTime next; // next activation time

	RelativeTime start;
	RelativeTime period;
	
	int index = -9999; // The index of the ScjProcesses; used by PriorityScheduler; -9999 is 'no index set'

	interface State {
		public final static byte NEW = 0;
		public final static byte READY = 1;
		public final static byte EXECUTING = 2;
		public final static byte BLOCKED = 3;
		public final static byte SLEEPING = 4;
		public final static byte HANDLED = 5;
		public final static byte TERMINATED = 6;
	}
	
	private class HandleAsyncEventRunnable implements Runnable
	{
		public void run() {
			target.handleAsyncEvent();
		}		
	}
	
	private HandleAsyncEventRunnable handlerLogic;
	
	/**
	 * The constructor initializes a new VM process object
	 * 
	 * @param target
	 *            is the handler containing the handleAsyncEvent method to be
	 *            executed
	 * @param stack
	 *            is the run time stack
	 */
	ScjProcess(ManagedEventHandler handler, int[] stack) {
		this.rtClock = Clock.getRealtimeClock();
		this.next = new AbsoluteTime(this.rtClock);
		this.target = handler;
		this.state = State.NEW;
		this.handlerLogic = new HandleAsyncEventRunnable();
		
		this.process = new vm.Process(new ProcessLogic() {
			public void run() {
				try {
					target.privateMemory.enter(handlerLogic);
				} catch (Exception e) {
					devices.Console.println("ScjProcess: exception -> " + e);
				} finally {
					if (target instanceof PeriodicEventHandler) {
						next.add(period, next); // next = next + period
					}
					state = State.HANDLED;
				}
			}

			public void catchError(Throwable t) {
				devices.Console.println("ScjProcess: exception -> " + t);
			}

		}, stack);

		this.process.initialize();

		rtClock.getTime(this.next);

		if (target instanceof PeriodicEventHandler) {
			this.start = ((PeriodicParameters) target.release).getStart();
			this.period = ((PeriodicParameters) target.release).getPeriod();
			next.add(start, next); // next = next + start
		}
		
		else if (target instanceof OneShotEventHandler) {
			
			// HSO: Dec 2013: RelativeTime in this version; later perhaps AbsoluteTime too
			RelativeTime releaseTime = (RelativeTime)((OneShotEventHandler) target).releaseTime;
			next.add(releaseTime, next); // next = next + releaseTime
		}

		this.target.process = this;
	}

	public String toString() {
		return ("ScjProcess:" /*+ target*/ + " index " + index);
	}

	/**
	 * Compares this process with the parameter process. The ordering of the
	 * processes are done after next release and priority.
	 */
	public int compareTo(ScjProcess process) {
		int result= this.next.compareTo(process.next);
		if (result == 0) 
		{
	      result = (process.target.priority.getPriority() - this.target.priority.getPriority());
		}
		return result;
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
	public static ScjProcess createIdleProcess() 
	{
	  if (idleProcess == null) 
	  {
		idleProcess = new ScjProcess(
		  new PeriodicEventHandler(
		    new PriorityParameters(Priorities.MIN_PRIORITY), 
			new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()),// start (0,0)
		  	                                        Const.INFINITE_TIME),     // period
          new StorageParameters(Const.BACKING_STORE_SIZE_DEFAULT,
        		new long[] { Const.IDLE_PROCESS_STACK_SIZE },
        		Const.PRIVATE_MEM_SIZE_DEFAULT, 
        		Const.IMMORTAL_MEM_SIZE_DEFAULT, 
        		Const.MISSION_MEM_SIZE_DEFAULT)
		  ) 
		  {
		    public void handleAsyncEvent() {
		      devices.Console.println("Idle");
			  yield();
			}

			private void yield() {
			  while (true) { 
				RealtimeClock.awaitNextTick();
			  }
		    }
		  }, 
		  new int[Const.IDLE_PROCESS_STACK_SIZE]);
		}
	  
		idleProcess.rtClock.getTime(idleProcess.next);
		idleProcess.index = -1;
		
		return idleProcess;
	}

	public void start() {
		process.initialize();
	}
}
