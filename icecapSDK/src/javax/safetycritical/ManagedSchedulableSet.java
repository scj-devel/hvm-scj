/**************************************************************************
 * File name  : ManagedSchedulableSet.java
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

import java.util.Iterator;

import javax.scj.util.Const;

import util.StringUtil;

/**
 * This collection class of handlers is created in mission memory and used 
 * by the mission.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 * 
 * @scjComment 
 *  - implementation issue: infrastructure class; not part of the SCJ specification.
 */
class ManagedSchedulableSet {

	private ManagedSchedulable[] schedulables;

	/**
	 * Count of ManagedSchedulable objects for the mission. 
	 * Only one mission at a time; no sub-mission
	 * 
	 * The msCount is incremented by addMS,
	 * and is decremented by PriorityScheduler when MS object is terminated.
	 * 
	 * Mission.runCleanup is waiting until msCount == 0
	 */
	private int msCount;
	private int noOfRegistered;
	private SchedulableIterator sitr;

	private class SchedulableIterator implements Iterator<ManagedSchedulable> {
		private int index;

		SchedulableIterator() {
			reset();
		}

		@Override
		public boolean hasNext() {
			return index < noOfRegistered;
		}

		@Override
		public ManagedSchedulable next() {
			return schedulables[index++];
		}

		public void reset() {
			index = 0;
		}
	}

	ManagedSchedulableSet() {
		schedulables = new ManagedSchedulable[Const.DEFAULT_HANDLER_NUMBER];
		sitr = new SchedulableIterator();
		msCount = noOfRegistered = 0;
	}

	/*@ 
	  behavior
	    requires ms != null;
	    ensures this.contains(ms);
	  @*/
	void addMSObject(ManagedSchedulable ms) {
		if (!containMSObject(ms)) {
			schedulables[noOfRegistered] = ms;
			noOfRegistered++;
			msCount++;
		}
	}

	boolean containMSObject(ManagedSchedulable ms) {
		for (int i = 0; i < noOfRegistered; i++) {
			if (schedulables[i] == ms)
				return true;
		}
		return false;
	}

	void terminateMSObjects() // stop all managed schedule objects; called in CyclicExecutive.runCleanup
	{
		for (int i = noOfRegistered; i > 0; i--) {
			schedulables[i - 1].cleanUp();
			deleteSchedulable(i - 1);
		}
	}

	void removeMSObject(ManagedSchedulable ms, Mission m) // called in Scj...Process.gotoNextState
	{
		if (msCount > 0) {
			for (int i = 0; i < noOfRegistered; i++) {
				if (schedulables[i] == ms) {
					schedulables[i].cleanUp();
					PriorityScheduler.instance().pFrame.removeFromQueue((ScjProcess) Process.getProcess(ms));
					//devices.Console.println("MSSet.removeMSObject " + scjProcesses[i].index);
					deleteSchedulable(i);
				}
			}
			//devices.Console.println("MSSet.removeMSObject: msCount " + msCount);
			if (msCount == 0) {
				m.getSequencer().seqNotify();
			}
		}
	}

	void removeAperiodicHandlers(Mission m) {
		if (msCount > 0) {
			for (int i = 0; i < noOfRegistered; i++) {
				if (schedulables[i] instanceof AperiodicEventHandler) {
					ManagedSchedulable ms = schedulables[i];

					schedulables[i].cleanUp();

					deleteSchedulable(i);

					PriorityScheduler.instance().pFrame.removeFromQueue((ScjProcess) Process.getProcess(ms));
				}
			}
			if (msCount == 0)
				m.getSequencer().seqNotify();
		}
	}

	public String toString() {
		StringBuffer buf = StringUtil.constructStringBuffer("Mission: ", noOfRegistered);
		buf.append(" handlers");
		return buf.toString();
	}

	private void deleteSchedulable(int i) {
		schedulables[i] = null;
		msCount--;
	}

	int getNumberOfManagedSchedulables() {
		return msCount;
	}

	public Iterator<ManagedSchedulable> iterator() {
		sitr.reset();
		return sitr;
	}

	public boolean hasSchedulables() {
		return msCount > 0;
	}

	public void deleteSchedulables() {
		for (int i = 0; i < noOfRegistered; i++) {
			deleteSchedulable(i);
		}
	}
}