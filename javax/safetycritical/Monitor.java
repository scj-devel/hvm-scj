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

import vm.InterruptHandler;
import icecaptools.IcecapCompileMe;

final class Monitor extends vm.Monitor {
	private int ceiling;
	private int synchCounter;
	private int priority;
	private ManagedEventHandler owner;
	private InterruptHandler clock;
	
	public Monitor(int ceiling) {
		this.ceiling = ceiling;
		clock = vm.ClockInterruptHandler.instance;
	}

	@IcecapCompileMe
	@Override
	public void lock() {
		clock.disable();
		ManagedEventHandler handler = PriorityScheduler.instance().current.target;
		if (owner == null) {
			owner = handler;
		}

		if (owner == handler) {
			synchCounter++;
			if (synchCounter == 1) {
				priority = handler.priority.getPriority();
				handler.priority.setPriority(max(priority, ceiling) + 1);
			}
		} else {
			clock.enable();
			throw new IllegalMonitorStateException("Not owner on entry");
		}
		clock.enable();
	}

	@IcecapCompileMe
	@Override
	public void unlock() {
		clock.disable();
		ManagedEventHandler handler = PriorityScheduler.instance().current.target;
		if (owner == handler) {
			synchCounter--;
			if (synchCounter == 0) {
				owner = null;
				handler.priority.setPriority(priority);
				clock.enable();
				clock.handle();
			}
		}
		else
		{
			clock.enable();
			throw new IllegalMonitorStateException("Not owner on exit");
		}
	}

	private static int max(int x, int y) {
		if (x > y)
			return x;
		else
			return y;
	}
}
