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
import javax.scj.util.Configuration;
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

	Launcher(Safelet<?> app, int level) {
		this(app, level, false);
	}

	Launcher(Safelet<?> app, int level, boolean useOS) {
		if (level < 0 || level > 2 || app == null) {
			throw new IllegalArgumentException();
		}
		
		this.app = app;
		Launcher.level = level;
		Launcher.useOS = useOS;

		
	}

	public void run() {
		app.initializeApplication();
		start();
	}

	void createImmortalMemory(){
		ManagedMemory.allocateBackingStore(Const.OVERALL_BACKING_STORE);

		if (Memory.memoryAreaTrackingEnabled) {
			new PrivateMemory(Const.MEMORY_TRACKER_AREA_SIZE, Const.MEMORY_TRACKER_AREA_SIZE,
					MemoryArea.overAllBackingStore, "MemTrk");
		}
		
		ManagedMemory immortalMem = new ManagedMemory.ImmortalMemory(2 * Const.IMMORTAL_MEM);
		immortalMem.executeInArea(this);
		//immortalMem.removeArea();
	}
	
	abstract void start(); 
	
	void startLevel0() {
		MissionSequencer<?> seq = app.getSequencer();
		CyclicScheduler.instance().start(seq);
	}
	
	void startLevel1_2() {
		// insert idle process before the mission sequencer.
		PriorityScheduler sch = PriorityScheduler.instance();
		sch.insertReadyQueue(ScjProcess.createIdleProcess());
		app.getSequencer();
		PriorityScheduler.instance().start();
	}
	
	void startwithOS() {
		initAffinfitySetsMulticore();
		Machine.setCurrentScheduler(new MultiprocessorHelpingScheduler());
		OSProcess.initSpecificID();
		MissionSequencer<?> outerMostMS = app.getSequencer();
		outerMostMS.privateMemory.enter(outerMostMS);
		outerMostMS.cleanUp();
	}
	
	private void initAffinfitySetsMulticore() {
		if (Configuration.processors != null) {
			AffinitySet.checkAndInitAffinityByCustomized(Configuration.processors);
		} else {
			switch (Launcher.level) {
			case 0:
				AffinitySet.initAffinitySet_Level0();
				break;
			case 1:
				AffinitySet.initAffinitySet_Level1();
				break;
			case 2:
				AffinitySet.initAffinitySet_Level2();
				break;
			}
		}
	}
}
