/**************************************************************************
 * File name  : Launcher.java
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

import javax.realtime.MemoryArea;
import javax.scj.util.Const;

import vm.Machine;
import vm.Memory;

/**
 * This class is used by an application class to launch a Level 0 or a Level 1
 * application.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 * 
 * @scjComment - The class is not part of the SCJ specification.
 */
abstract class Launcher implements Runnable {
	Safelet<?> app;
	static int level;
	static boolean useOS = false;
	static Mission.MissionBehavior delegate = null;

	Launcher(Safelet<?> app, int level) {
		this(app, level, false);
	}

	Launcher(Safelet<?> app, int level, boolean useOS) {
		this.app = app;
		Launcher.level = level;
		Launcher.useOS = useOS;

		ManagedMemory.allocateBackingStore(Const.OVERALL_BACKING_STORE);

		if (Memory.memoryAreaTrackingEnabled) {
			new PrivateMemory(Const.MEMORY_TRACKER_AREA_SIZE, Const.MEMORY_TRACKER_AREA_SIZE,
					MemoryArea.overAllBackingStore, "MemTrk");
		}

		ManagedMemory immortalMem = new ManagedMemory.ImmortalMemory(2 * Const.IMMORTAL_MEM);
		immortalMem.executeInArea(this);
		//immortalMem.removeArea();
	}

	public void run() {
		app.initializeApplication();
		start();
	}

	protected abstract void start(); 

	protected void startLevel0() {
		delegate = new Mission.MissionSinglecoreBehavior();
		MissionSequencer<?> seq = app.getSequencer();
		CyclicScheduler.instance().start(seq);
	}

	protected void startLevel1_2() {
		// insert idle process before the mission sequencer.
		delegate = new Mission.MissionSinglecoreBehavior();
		PriorityScheduler sch = PriorityScheduler.instance();
		sch.insertReadyQueue(ScjProcess.createIdleProcess());
		app.getSequencer();
		PriorityScheduler.instance().start();
	}

	protected void startwithOS() {
		delegate = new Mission.MissionMulticoreBehavior();
		Machine.setCurrentScheduler(new MultiprocessorHelpingScheduler());
		OSProcess.initSpecificID();
		MissionSequencer<?> outerMostMS = app.getSequencer();
		outerMostMS.privateMemory.enter(outerMostMS);
		outerMostMS.cleanUp();
	}
}
