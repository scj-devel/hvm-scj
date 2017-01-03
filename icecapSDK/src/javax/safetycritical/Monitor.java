/**************************************************************************
 * File name  : Monitor.java
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

import javax.safetycritical.ScjProcess.State;

import vm.InterruptHandler;
import icecaptools.IcecapCompileMe;

final class Monitor extends vm.Monitor {
	int ceiling;
	private int synchCounter;
	private int priority;
	private ManagedSchedulable owner;
	private InterruptHandler clock;

	Monitor(int ceiling) {
		this.ceiling = ceiling;
		clock = vm.ClockInterruptHandler.instance;
	}

	public void lock() {
		clock.disable();
		ScjProcess currentProcess = PriorityScheduler.instance().getCurrentProcess();

		if (currentProcess != null) {
			ManagedSchedulable msObj = currentProcess.getTarget();
			
//			if (PriorityScheduler.instance().getCurrentProcess().index != -1)  // do not print idle
//				devices.Console.println("\n" + PriorityScheduler.instance().getCurrentProcess().index + " locks ");
			
			if (owner == null) {
				setOwner(msObj);
			}

			if (owner == msObj) {
				synchCounter++;
				if (synchCounter == 1) {
					priority = ManagedSchedMethods.getPriorityParameter(msObj).getPriority();
					ManagedSchedMethods.getPriorityParameter(msObj).setPriority(max(priority, ceiling) + 1);
				}
				clock.enable();
			} else {
				// insert the process to in lock queue
				// process in REQUIRELOCK state
				//ScjProcess process = ManagedSchedMethods.getScjProcess(msObj);
				currentProcess.state = ScjProcess.State.REQUIRELOCK;
				PriorityScheduler.instance().addProcessToLockQueue(this, currentProcess);
				devices.Console.println("addProcessToLockQueue: "  + ManagedSchedMethods.getScjProcess(msObj).index);

				// transfer to the process
				clock.enable();
				vm.ClockInterruptHandler.instance.yield();
			}
		} else {
			clock.enable();
		}
	}

	@IcecapCompileMe
	@Override
	public void unlock() {
		clock.disable();
		ScjProcess currentProcess = PriorityScheduler.instance().getCurrentProcess();

		if (currentProcess != null) {
			ManagedSchedulable msObj = currentProcess.getTarget();
			
//			if (PriorityScheduler.instance().getCurrentProcess().index != -1)  // do not print idle
//				devices.Console.println(PriorityScheduler.instance().getCurrentProcess().index + " unlocks \n");
			
			if (owner == msObj) {
				synchCounter--;

				if (synchCounter == 0) {
					ManagedSchedMethods.getPriorityParameter(msObj).setPriority(priority);
					// get the next process that needs the lock in lock queue 
					// and assign the lock to this process.
					ScjProcess process = PriorityScheduler.instance().getProcessFromLockQueue(this);
					
					if (process != null) devices.Console.println("getProcessFromLockQueue: " + process.index);

					if (process != null) {
						setOwner(process.getTarget());

						synchCounter++;
						priority = ManagedSchedMethods.getPriorityParameter(msObj).getPriority();
						ManagedSchedMethods.getPriorityParameter(msObj).setPriority(max(priority, ceiling) + 1);
						// process READY
						process.state = State.READY;
						PriorityScheduler.instance().insertReadyQueue(process);
						devices.Console.println("insertReadyQueue: " + process.index);
					} else {
						setOwner(null);
					}
				}
			} else {
				devices.Console.println("    Monitor.unlock: UPS, - not owner");
				clock.enable();
				throw new IllegalMonitorStateException("Not owner on exit");
			}
			clock.enable();
		} else {
			clock.enable();
		}

	}
	
	protected void lockWithOutEnable() {
		vm.ClockInterruptHandler.instance.disable();
		ManagedSchedulable ms = PriorityScheduler.instance().getCurrentProcess().getTarget();
		if (owner == null) {
			owner = ms;
		}

		if (owner == ms) {
			synchCounter++;
			if (synchCounter == 1) {
				priority = ManagedSchedMethods.getPriorityParameter(owner).getPriority();
				ManagedSchedMethods.getPriorityParameter(owner).setPriority(max(priority, ceiling) + 1);
			}
		} else {
			// insert the process to the lock set.
			PriorityScheduler.instance().getCurrentProcess().state = ScjProcess.State.REQUIRELOCK;
			PriorityScheduler.instance().addProcessToLockQueue(this, PriorityScheduler.instance().getCurrentProcess());

			// get the next process and set the state.
			PriorityScheduler.instance().moveToNext();

			// transfer to the process
			vm.ClockInterruptHandler.instance.enable();
			vm.ClockInterruptHandler.instance.handle();
		}
	}
	
	protected void unlockWithOutEnable() {
		ManagedSchedulable ms = PriorityScheduler.instance().getCurrentProcess().getTarget();

		if (owner == ms) {
			synchCounter--;
			if (synchCounter == 0) {
				ManagedSchedMethods.getPriorityParameter(ms).setPriority(priority);
				
				// get the next process that needs the lock in lock set and
				// assign the lock to this process.
				ScjProcess nextProcess = PriorityScheduler.instance().getProcessFromLockQueue(this);
				if (nextProcess != null) {
					owner = nextProcess.getTarget();
					synchCounter++;
					
					priority = ManagedSchedMethods.getPriorityParameter(owner).getPriority();
					ManagedSchedMethods.getPriorityParameter(owner).setPriority(max(priority, ceiling) + 1);					
					
					// process READY
					nextProcess.state = State.READY;
					PriorityScheduler.instance().insertReadyQueue(nextProcess);
					devices.Console.println("insertReadyQueue: " + nextProcess.index);
				} else {
					owner = null;
				}
			}
		} else {
			clock.enable();
			throw new IllegalMonitorStateException();
		}
	}
		

	private void setOwner(ManagedSchedulable msObj) {
		owner = msObj;
	}

	private static int max(int x, int y) {
		if (x > y)
			return x;
		else
			return y;
	}

	static Monitor getMonitor(Object target) {
		Object obj = getAttachedMonitor(target);
		if (obj == null || !(obj instanceof Monitor)) {
			throw new IllegalMonitorStateException("the target is not a lock:" + obj.toString());
		}
		return (Monitor) obj;
	}
}
